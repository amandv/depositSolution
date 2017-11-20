package tests;

import config.ConfigReader;
import org.assertj.core.api.SoftAssertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.AllUsers;
import pages.NewUser;
import utils.APIUtils;
import utils.TestUtils;

import static constants.Constants.USER_EMAIL_ID;
import static constants.Constants.USER_NAME;

/**
 * Created by amanpreet.oberoi on 11/14/2017.
 */

public class RandomUserTest extends BaseTest {
    static NewUser newUser;
    static AllUsers allUsers;
    static WebDriver driver;

    @BeforeClass
    public static void beforeClass() throws Exception {
        driver = getDriver();
        newUser = new NewUser(driver);
        allUsers = new AllUsers(driver);
    }

    @AfterClass
    public static void afterClass() {
        if (ConfigReader.getInstance().isCleanUsersData()) {
            System.out.println("Deleting All Users Data");
            APIUtils.delete(ConfigReader.getInstance().getApplicationBaseUrl() + "user/all", null);
        }
    }

    @Test
    public void requiredFieldsErrorMessageTest() {
        try {
            driver.get(ConfigReader.getInstance().getApplicationBaseUrl());
            SoftAssertions assertions = new SoftAssertions();
            System.out.println("======================================================");
            newUser.generateNewUser("", "", "", "");
            System.out.println("User Name Error Message Verification ");
            assertions.assertThat(newUser.getUserNameErrorMessage()).isEqualTo("Required");
            System.out.println("User Email Message Verification");
            assertions.assertThat(newUser.getUserEmailErrorMessage()).isEqualTo("requirred");
            System.out.println("User Password Message Verification");
            assertions.assertThat(newUser.getUserPasswordMessage()).isEqualTo("Required");
            assertions.assertAll();
            System.out.println("======================================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createValidUser() {
        try {
            System.out.println("Valid User Test");
            System.out.println("======================================================");
            SoftAssertions softAssertions = new SoftAssertions();
            String userName = TestUtils.generateRandomString("Testing", 6);
            String userEmail = TestUtils.generateUserEmail("deposit.com", 5);
            String password = TestUtils.generateRandomNumber(6);
            driver.get(ConfigReader.getInstance().getApplicationBaseUrl());
            newUser.generateNewUser(userName, userEmail, password, password);
            softAssertions.assertThat(allUsers.verifyNewUserButtonDisplayed()).isTrue();
            if (allUsers.getNewUserName().equals(userName)) {
                System.out.println("New User Name Matched");
            }
            softAssertions.assertThat(allUsers.getNewUserName()).isEqualTo(userName);

            if (allUsers.getNewUserEmailID().equals(userEmail)) {
                System.out.println("New User Email ID Matched");
            }
            softAssertions.assertThat(allUsers.getNewUserEmailID()).isEqualTo(userEmail);
            if (allUsers.getNewUserPassword().equals(password)) {
                System.out.println("New User password matched");
            }
            softAssertions.assertThat(allUsers.getNewUserPassword()).isEqualTo(password);
            softAssertions.assertThat(APIUtils.verifyUserExistence(userName, userEmail, password)).isTrue();
            softAssertions.assertAll();
            System.out.println("======================================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void differentPasswordTest() {
        try {
            System.out.println("Different Confirm Password Test ");
            System.out.println("======================================================");
            SoftAssertions softAssertions = new SoftAssertions();
            driver.get(ConfigReader.getInstance().getApplicationBaseUrl());
            String userName = TestUtils.generateRandomString("Testing", 6);
            String userEmail = TestUtils.generateUserEmail("deposit.com", 5);
            String password = TestUtils.generateRandomNumber(6);
            newUser.generateNewUser(userName, userEmail, password, "111111");
            softAssertions.assertThat(newUser.getUserConfirmPasswordMessage()).isEqualTo("passwords are not the same");
            softAssertions.assertThat(APIUtils.verifyUserExistence(userName, userEmail, password)).isFalse();
            softAssertions.assertAll();
            System.out.println("======================================================");
        } catch (Exception e) {
            System.out.println("Error while running test different confirm password " + e.getClass().getName());
            e.printStackTrace();
        }
    }

    @Test
    public void existingUserTest() {
        try {
            System.out.println("Existing User Test ");
            System.out.println("======================================================");
            SoftAssertions softAssertions = new SoftAssertions();
            driver.get(ConfigReader.getInstance().getApplicationBaseUrl());
            if (ConfigReader.getInstance().isCleanUsersData() && APIUtils.verifyUserExistence(USER_NAME, USER_EMAIL_ID, "111111")) {
                newUser.generateNewUser(USER_NAME, USER_EMAIL_ID, "111111", "111111");
                allUsers.clickNewUserButton();
            } else {
                newUser.generateNewUser(USER_NAME, USER_EMAIL_ID, "111111", "111111");
                allUsers.clickNewUserButton();
                newUser.generateNewUser(USER_NAME, USER_EMAIL_ID, "111111", "111111");
            }
            softAssertions.assertThat(newUser.getUserNameErrorMessage()).isEqualTo("Must be unique");
            softAssertions.assertThat(newUser.getUserEmailErrorMessage()).isEqualTo("Must be unique");
            softAssertions.assertAll();
            System.out.println("======================================================");
        } catch (Exception e) {
            System.out.println("Error while running test for existing user : " + e.getClass().getName());
            e.printStackTrace();
        }
    }

    @Test
    public void newUserFormFieldsTextTest() {
        try {
            System.out.println("New User Form Fields Displayed Text Assertion Test ");
            System.out.println("======================================================");
            SoftAssertions softAssertions = new SoftAssertions();
            driver.get(ConfigReader.getInstance().getApplicationBaseUrl());
            softAssertions.assertThat(newUser.getUserNameInputFieldDisplayedText()).isEqualTo("name");
            softAssertions.assertThat(newUser.getUserEMailIDInputFieldDisplayedText()).isEqualTo("your@email.com");
            softAssertions.assertThat(newUser.getUserPasswordInputFieldDisplayedText()).isEqualTo("min 6 characters");
            softAssertions.assertThat(newUser.getUserConfirmPasswordInputFieldText()).isEqualTo("min 6 characters");
            softAssertions.assertAll();
            System.out.println("======================================================");
        } catch (Exception e) {
            System.out.println("Error while fetching New user input fields text : " + e.getClass().getName());
            e.printStackTrace();
        }
    }
}
