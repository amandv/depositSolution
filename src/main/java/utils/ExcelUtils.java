package utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by amanpreet.oberoi on 11/17/2017.
 */

public class ExcelUtils {
    private static ExcelUtils instance = null;

    public ExcelUtils() {
    }

    public static ExcelUtils getInstance() {
        if (instance == null) {
            instance = new ExcelUtils();
        }
        return instance;
    }

    public Collection loadFromSpreadsheet(String fileName, String sheetName)
            throws IOException {
        File excelFile = new File(System.getProperty("user.dir") + "\\resources\\testData\\" + fileName);
        FileInputStream fileInputStream = new FileInputStream(excelFile);
        Workbook workbook;
        if (fileName.contains(".xlsx")) {
            workbook = new XSSFWorkbook(fileInputStream);
        } else {
            workbook = new HSSFWorkbook(fileInputStream);
        }

        Sheet sheet = workbook.getSheet(sheetName);

        int numberOfColumns = countNonEmptyColumns(sheet);
        List rows = new ArrayList();
        List rowData = new ArrayList();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (isEmpty(row)) {
                break;
            } else {
                rowData.clear();
                for (int column = 0; column < numberOfColumns; column++) {
                    Cell cell = row.getCell(column);
                    rowData.add(objectFrom(cell));
                }
                rows.add(rowData.toArray());
            }
        }
        return rows;
    }

    private boolean isEmpty(final Row row) {
        Cell firstCell = row.getCell(0);
        boolean rowIsEmpty = (firstCell == null)
                || (firstCell.getCellType() == Cell.CELL_TYPE_BLANK);
        return rowIsEmpty;
    }

    /**
     * Count the number of columns, using the number of non-empty cells in the
     * first row.
     */
    private int countNonEmptyColumns(final Sheet sheet) {
        Row firstRow = sheet.getRow(0);
        return firstEmptyCellPosition(firstRow);
    }

    private int firstEmptyCellPosition(final Row cells) {
        int columnCount = 0;
        for (Cell cell : cells) {
            if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                break;
            }
            columnCount++;
        }
        return columnCount;
    }

    private String objectFrom(final Cell cell) {
        String value = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                NumberFormat fmt = new DecimalFormat("##.##########");
                value = fmt.format(cell.getNumericCellValue()).toString();
                // String value = Double.toString(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = Boolean.toString(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            case Cell.CELL_TYPE_ERROR:
                value = "";
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = getFormulaCellValue(cell);
                break;
            default:
                value = cell.getStringCellValue();
                break;
        }
        return value;
    }

    private String getFormulaCellValue(Cell cell) {
        String value = null;
        switch (cell.getCachedFormulaResultType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().toString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                value = String.valueOf((int) cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = getFormulaCellValue(cell);
                break;
        }
        return value;
    }
}