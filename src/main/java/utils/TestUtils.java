package utils;

import constants.Constants;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by amanpreet.oberoi on 11/14/2017.
 */

public class TestUtils {
    public static final String getCurrentDateTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate.replace(" ", "_").replace("-", "_").replace(":", "").replace(".", "");
    }

    public static String generateRandomStringWithDate(String prefix, int length) {
        String date = getCurrentDateTime().replace("-", "_").replace(":", "").replace(".", "");
        if (prefix == null) {
            return RandomStringUtils.randomAlphabetic(length) + "_" + date;
        } else {
            return prefix + RandomStringUtils.randomAlphabetic(length) + "_" + date;
        }
    }

    public static String generateRandomNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static String generateRandomAlphaNumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String generateUserEmail(String domain, int length) {
        String date = getCurrentDateTime().replace("-", "_").replace(":", "").replace(".", "");
        String randomString = generateRandomString("Test", length).toLowerCase();
        String email = null;
        if (length == 0) {
            email = date + "_" + randomString + "@" + domain;
        } else {
            email = date + "_" + randomString + "@" + domain;
        }
        return email;
    }

    public static void pause(final int timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static String generateRandomString(String prefix, int length) {
        if (prefix == null) {
            return RandomStringUtils.randomAlphabetic(length);
        } else {
            return prefix + RandomStringUtils.randomAlphabetic(length);
        }
    }

    public static boolean strToBoolean(String str) {
        return str.trim().equalsIgnoreCase("true");
    }

    public static String generateRandomValue(String keywordInput, String inputData) {
        String generatedObject = null;
        try {
            List<String> strings = Arrays.asList(inputData.split(","));
            String domain = null;
            String prefix = null;
            int length = 0;
            domain = strings.get(0);
            length = Integer.parseInt(strings.get(1));
            if (strings.size() == 3) {
                prefix = strings.get(2);
            }
            if (keywordInput.equalsIgnoreCase(Constants.RANDOM_KEYWORD_STRING)) {
                generatedObject = TestUtils.generateRandomString(prefix, length);
            }
            if (keywordInput.equalsIgnoreCase(Constants.RANDOM_KEYWORD_NUMBER)) {
                generatedObject = TestUtils.generateRandomNumber(length);
            }
            if (keywordInput.equals(Constants.RANDOM_KEYWORD_EMAIL)) {
                generatedObject = TestUtils.generateUserEmail(domain, length);
            }
            if (keywordInput.equalsIgnoreCase(Constants.RANDOM_KEYWORD_ALPHA)) {
                generatedObject = TestUtils.generateRandomAlphaNumeric(length);
            }
            if (keywordInput.equalsIgnoreCase(Constants.RANDOM_DATE_STRING)) {
                generatedObject = TestUtils.generateRandomStringWithDate(prefix, length);
            }
        } catch (Exception e) {

        }
        return generatedObject;
    }

    public static void checkAndCreateDirectory(String path) {
        try {
            boolean status = false;
            if (path != null && !path.isEmpty()) {
                File file = new File(path);
                status = file.exists() || file.mkdirs();
            }
            if (!status) {
                System.out.println("Couldn't create directory - " + path);
            }
        } catch (Exception e) {
            System.out.println("Couldn't create directory as requested for " + path + e.getClass().getName());
            e.printStackTrace();
        }
    }
}
