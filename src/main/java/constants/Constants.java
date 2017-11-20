package constants;

import utils.TestUtils;

/**
 * Created by amanpreet.oberoi on 11/14/2017.
 */
public class Constants {
    public static final String RANDOM_KEYWORD_STRING = "STRING";
    public static final String RANDOM_KEYWORD_NUMBER = "NUMBER";
    public static final String RANDOM_KEYWORD_EMAIL = "EMAIL";
    public static final String RANDOM_KEYWORD_ALPHA = "ALPHA";
    public static final String RANDOM_DATE_STRING = "DATESTRING";
    public static final String USER_NAME = TestUtils.generateRandomString("DEPOSITTEST", 6);
    public static final String USER_EMAIL_ID = TestUtils.generateUserEmail("deposit.com", 5);
}
