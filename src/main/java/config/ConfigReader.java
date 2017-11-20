package config;

import utils.TestUtils;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by amanpreet.oberoi on 11/15/2017.
 */

public class ConfigReader {
    private static ConfigReader instance = null;
    Properties prop = new Properties();
    private String browserName;
    private String applicationBaseUrl;
    private boolean cleanUsersData;
    private Integer elementWaitTime;
    private String excelTestDataFileName;
    private String excelTestDataSheetName;
    private boolean screenShotOnSuccess;
    private boolean screenShotOnFailure;

    private ConfigReader() {

    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public void initProperties() {
        try {
            prop.load(new FileInputStream(System.getProperty("user.dir") + "\\resources\\config.properties"));
            browserName = prop.getProperty("BROWSER_NAME").trim();
            applicationBaseUrl = prop.getProperty("APPLICATION_BASE_URL").trim();
            cleanUsersData = TestUtils.strToBoolean(prop.getProperty("CLEAN_USERS_DATA"));
            elementWaitTime = Integer.parseInt(prop.getProperty("ELEMENT_WAIT_TIME"));
            excelTestDataFileName = prop.getProperty("EXCEL_TEST_DATA_INPUT_FILE_NAME").trim();
            excelTestDataSheetName = prop.getProperty("EXCEL_TEST_DATA_SHEET_NAME").trim();
            screenShotOnFailure = TestUtils.strToBoolean(prop.getProperty("SCREEN_SHOT_TEST_FAILURE"));
            screenShotOnSuccess = TestUtils.strToBoolean(prop.getProperty("SCREEN_SHOT_TEST_SUCCESS"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getApplicationBaseUrl() {
        return applicationBaseUrl;
    }

    public void setApplicationBaseUrl(String applicationBaseUrl) {
        this.applicationBaseUrl = applicationBaseUrl;
    }

    public boolean isCleanUsersData() {
        return cleanUsersData;
    }

    public void setCleanUsersData(boolean cleanUsersData) {
        this.cleanUsersData = cleanUsersData;
    }

    public Integer getElementWaitTime() {
        return elementWaitTime;
    }

    public void setElementWaitTime(Integer elementWaitTime) {
        this.elementWaitTime = elementWaitTime;
    }

    public String getExcelTestDataFileName() {
        return excelTestDataFileName;
    }

    public void setExcelTestDataFileName(String excelTestDataFileName) {
        this.excelTestDataFileName = excelTestDataFileName;
    }

    public String getExcelTestDataSheetName() {
        return excelTestDataSheetName;
    }

    public void setExcelTestDataSheetName(String excelTestDataSheetName) {
        this.excelTestDataSheetName = excelTestDataSheetName;
    }

    public boolean isScreenShotOnSuccess() {
        return screenShotOnSuccess;
    }

    public void setScreenShotOnSuccess(boolean screenShotOnSuccess) {
        this.screenShotOnSuccess = screenShotOnSuccess;
    }

    public boolean isScreenShotOnFailure() {
        return screenShotOnFailure;
    }

    public void setScreenShotOnFailure(boolean screenShotOnFailure) {
        this.screenShotOnFailure = screenShotOnFailure;
    }
}
