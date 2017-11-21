package tests;

import config.ConfigReader;
import org.assertj.core.api.SoftAssertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pages.AllUsers;
import pages.NewUser;
import utils.APIUtils;
import utils.APIUtils.APIResponse;
import utils.ExcelUtils;
import utils.TestUtils;

import java.io.IOException;
import java.util.Collection;

import static org.junit.runners.Parameterized.Parameters;

/**
 * Created by amanpreet.oberoi on 11/17/2017.
 */

@RunWith(Parameterized.class)
public class ExcelDataDrivenUserTest extends BaseTest {
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
    private static boolean userExistenceCheck;

    private ExcelDataDrivenUserTest excelDataDrivenUserTest;

    public ExcelDataDrivenUserTest(String userName, String userEmailID, String userPassword, String userConFirmPassword, String userNameErrorMessage,
                                   String userNameFieldDisplayedText, String userEmailIDErrorMessage, String userEmailIDFieldDisplayedText,
                                   String userPasswordErrorMessage, String userPasswordFieldDisplayedText, String userConFirmPasswordErrorMessage,
                                   String userConFirmPasswordFieldDisplayedText, String userExistenceCheck) {
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
        this.userExistenceCheck = TestUtils.strToBoolean(userExistenceCheck);
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

    @AfterClass
    public static void afterClass() throws IOException {
        APIResponse response = APIUtils.get(ConfigReader.getInstance().getApplicationBaseUrl() + "user/all/json", null);
        System.out.println("All Users Created In Suite Response JSON : " + response.message);
        if (ConfigReader.getInstance().isCleanUsersData()) {
            System.out.println("Deleting All Users Data");
            APIUtils.delete(ConfigReader.getInstance().getApplicationBaseUrl() + "user/all", null);
        }
    }

    @Parameters
    public static Collection spreadsheetData() throws IOException {
        ConfigReader.getInstance().initProperties();
        return new ExcelUtils().getInstance().loadFromSpreadsheet
                (ConfigReader.getInstance().getExcelTestDataFileName(),
                        ConfigReader.getInstance().getExcelTestDataSheetName());
    }

    @Test
    public void createNewUsersExcelParameterized() throws Exception {
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
        assertions.assertThat(APIUtils.verifyUserExistence(userName, userEmailID, userPassword)).isTrue();
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
        if (userExistenceCheck) {
            assertions.assertThat(APIUtils.verifyUserExistence(userName, userEmailID, userPassword)).isFalse();
        }
        assertions.assertThat(newUser.verifyAllUsersButtonDisplayed()).isTrue();
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
