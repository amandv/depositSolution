package tests;

import config.ConfigReader;
import org.assertj.core.api.SoftAssertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import pages.AllUsers;
import pages.NewUser;
import utils.APIUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by amanpreet.oberoi on 11/16/2017.
 */

@RunWith(Parameterized.class)
public class DataDrivenUserTest extends BaseTest {
    static NewUser newUser;
    static AllUsers allUsers;
    static WebDriver driver;
    private static String userName;
    private static String userEmailID;
    private static String userPassword;
    private static String userConFirmPassword;
    private static String userNameErrorMessage;
    private static String userNameFieldDisplayedText;
    private static String userEmailIDErrorMessage;
    private static String userEmailIDFieldDisplayedText;
    private static String userPasswordErrorMessage;
    private static String userPasswordFieldDisplayedText;
    private static String userConFirmPasswordErrorMessage;
    private static String userConFirmPasswordFieldDisplayedText;

    private DataDrivenUserTest dataDrivenUserTest;

    public DataDrivenUserTest(String userName, String userEmailID, String userPassword, String userConFirmPassword, String userNameErrorMessage, String userNameFieldDisplayedText, String userEmailIDErrorMessage, String userEmailIDFieldDisplayedText,
                              String userPasswordErrorMessage, String userPasswordFieldDisplayedText, String userConFirmPasswordErrorMessage, String userConFirmPasswordFieldDisplayedText) {
        this.userName = userName;
        this.userEmailID = userEmailID;
        this.userPassword = userPassword;
        this.userConFirmPassword = userConFirmPassword;
        this.userNameErrorMessage = userNameErrorMessage;
        this.userNameFieldDisplayedText = userNameFieldDisplayedText;
        this.userEmailIDErrorMessage = userEmailIDErrorMessage;
        this.userEmailIDFieldDisplayedText = userEmailIDFieldDisplayedText;
        this.userPasswordErrorMessage = userPasswordErrorMessage;
        this.userPasswordFieldDisplayedText = userPasswordFieldDisplayedText;
        this.userConFirmPasswordErrorMessage = userConFirmPasswordErrorMessage;
        this.userConFirmPasswordFieldDisplayedText = userConFirmPasswordFieldDisplayedText;
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        if (ConfigReader.getInstance().isCleanUsersData()) {
            System.out.println("Deleting All Users Data");
            APIUtils.delete(ConfigReader.getInstance().getApplicationBaseUrl() + "user/all", null);
        }
        driver = getDriver();
        newUser = new NewUser(driver);
        allUsers = new AllUsers(driver);
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                //{"User Name","User Email ID", "Password", "Confirm Password", "User Name Error Message","User Name Field Displayed Text",
                // "User Email ID Error Message", "User Password Error Message", "User Password Field Displayed Text",
                // "User Confirm Password Error Message", "User Confirm Password Displayed Text"}
                {"abc", "abc@gmail.com", "111111", "111111", "", "", "", "", "", "", "", ""},
                {"cba", "cba@gmail.com", "111111", "111111", "", "", "", "", "", "", "", ""},
                {"cba", "cba@gmail.com", "111111", "111111", "Must be unique", "", "Must be unique", "", "", "", "", ""},
                {"dba", "dba@gmail.com", "111111", "11111111", "", "", "", "", "", "", "passwords are not the same", ""},
                {"!@#%#$$&$(#&&%$^%$^", "!@#%#$$&$(#&&%$^%$^@gmail.com", "111111", "11111111", "", "", "Invalid email address", "", "", "", "passwords are not the same", ""},
                {"", "", "", "", "Required", "name", "Required", "your@email.com", "Required", "min 6 characters", "", "min 6 characters"},
        });
    }

    @AfterClass
    public static void afterClass() {
        if (ConfigReader.getInstance().isCleanUsersData()) {
            System.out.println("Deleting All Users Data");
            APIUtils.delete(ConfigReader.getInstance().getApplicationBaseUrl() + "user/all", null);
        }
    }

    @Test
    public void createNewUsersParameterized() throws Exception {
        driver.get(ConfigReader.getInstance().getApplicationBaseUrl());
        System.out.println("***********************************************");
        System.out.println("User Name: " + userName);
        System.out.println("User Email ID: " + userEmailID);
        System.out.println("User Password: " + userPassword);
        System.out.println("User Confirm Password: " + userConFirmPassword);
        assertFormFieldsDisplayedText();
        newUser.generateNewUser(userName, userEmailID, userPassword, userConFirmPassword);
        if (allUsers.verifyNewUserButtonDisplayed()) {
            assertNewUserCreationSuccess();
        } else {
            assertNewUserCreationFailure();
        }
        System.out.println("***********************************************");
    }

    private void assertNewUserCreationSuccess() throws Exception {
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(allUsers.verifyNewUserButtonDisplayed()).isTrue();
        assertions.assertThat(allUsers.getNewUserName()).isEqualTo(userName);
        assertions.assertThat(allUsers.getNewUserEmailID()).isEqualTo(userEmailID);
        assertions.assertThat(allUsers.getNewUserPassword()).isEqualTo(userPassword);
        assertions.assertAll();
    }

    private void assertNewUserCreationFailure() throws Exception {
        SoftAssertions assertions = new SoftAssertions();
        if (!userNameErrorMessage.isEmpty()) {
            assertions.assertThat(newUser.getUserNameErrorMessage()).isEqualTo(userNameErrorMessage);
        }
        if (!userEmailIDErrorMessage.isEmpty()) {
            assertions.assertThat(newUser.getUserEmailErrorMessage()).isEqualTo(userEmailIDErrorMessage);
        }
        if (!userPasswordErrorMessage.isEmpty()) {
            assertions.assertThat(newUser.getUserPasswordMessage()).isEqualTo(userPasswordErrorMessage);
        }
        if (!userConFirmPasswordErrorMessage.isEmpty()) {
            assertions.assertThat(newUser.getUserConfirmPasswordMessage()).isEqualTo(userConFirmPasswordErrorMessage);
        }
        assertions.assertAll();
    }

    private void assertFormFieldsDisplayedText() throws Exception {
        SoftAssertions assertions = new SoftAssertions();
        if (!userNameFieldDisplayedText.isEmpty()) {
            assertions.assertThat(newUser.getUserNameInputFieldDisplayedText()).isEqualTo(userNameFieldDisplayedText);
        }
        if (!userEmailIDFieldDisplayedText.isEmpty()) {
            assertions.assertThat(newUser.getUserEMailIDInputFieldDisplayedText()).isEqualTo(userEmailIDFieldDisplayedText);
        }
        if (!userPasswordFieldDisplayedText.isEmpty()) {
            assertions.assertThat(newUser.getUserPasswordInputFieldDisplayedText()).isEqualTo(userPasswordFieldDisplayedText);
        }
        if (!userConFirmPasswordFieldDisplayedText.isEmpty()) {
            assertions.assertThat(newUser.getUserConfirmPasswordInputFieldText()).isEqualTo(userConFirmPasswordFieldDisplayedText);
        }
        assertions.assertAll();
    }
}
