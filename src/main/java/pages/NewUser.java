package pages;

import config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

/**
 * Created by amanpreet.oberoi on 11/14/2017.
 */

public class NewUser {
    WebDriver driver;

    @FindBy(id = "name")
    WebElement name;

    @FindBy(id = "user.name.error")
    WebElement userNameError;

    @FindBy(id = "email")
    WebElement emailID;

    @FindBy(id = "user.email.error")
    WebElement emailError;

    @FindBy(id = "password")
    WebElement passWord;

    @FindBy(id = "user.password.error")
    WebElement passwordError;

    @FindBy(id = "confirmationPassword")
    WebElement confirmPassword;

    @FindBy(id = "user.confirmationPassword.error")
    WebElement confirmPasswordError;

    @FindBy(xpath = "//a[contains(@class,'btn btn-primary') and text()='All User']")
    WebElement allUsersButton;

    @FindBy(xpath = "//button[text()='Submit']")
    WebElement submitButton;


    public NewUser(WebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, ConfigReader.getInstance().getElementWaitTime());
        PageFactory.initElements(factory, this);
    }

    public void setName(String username) {
        try {
            name.sendKeys(username);
            System.out.println("User Name entered in User Name field Successfully : " + username);
        } catch (Exception e) {
            System.out.println("Unable to enter user name in name field : " + e.getClass().getName());
            e.printStackTrace();
        }
    }

    public void setEmailID(String userEmailID) {
        try {
            emailID.sendKeys(userEmailID);
            System.out.println("User Email ID entered in email id field successfully : " + userEmailID);
        } catch (Exception e) {
            System.out.println("Unable to enter enter user email id : " + e.getClass().getName());
            e.printStackTrace();
        }
    }

    public void setPassword(String userPassword) {
        try {
            passWord.sendKeys(userPassword);
            System.out.println("User Password entered in password field successfully : " + userPassword);
        } catch (Exception e) {
            System.out.println("Unable to enter user password : " + e.getClass().getName());
            e.printStackTrace();
        }
    }

    public void setConfirmPassword(String userConfirmPassword) {
        try {
            confirmPassword.sendKeys(userConfirmPassword);
            System.out.println("User Confirm password entered in confirm password field successfully : " + userConfirmPassword);
        } catch (Exception e) {
            System.out.println("Unable to enter user confirm password : " + e.getClass().getName());
            e.printStackTrace();
        }
    }

    public void clickSubmitButton() {
        try {
            submitButton.click();
            System.out.println("Submit Button successfully clicked ");
        } catch (Exception e) {
            System.out.println("Unable to click on submit button : " + e.getClass().getName());
            e.printStackTrace();
        }
    }

    public void clickAllUsersButton() {
        try {
            allUsersButton.click();
            System.out.println("All Users Button successfully clicked ");
        } catch (Exception e) {
            System.out.println("Unable to click all users button : " + e.getClass().getName());
            e.printStackTrace();
        }
    }

    public boolean verifyAllUsersButtonDisplayed() {
        boolean allUsersButtonIsVisible = false;
        try {
            allUsersButtonIsVisible = allUsersButton.isDisplayed();
            System.out.println("Verifying All Users Button Display on Page " + allUsersButtonIsVisible);
            return allUsersButtonIsVisible;
        } catch (Exception e) {
            System.out.println("Error in checking the visibility of all users button on New User page : " + e.getClass().getName());
            e.printStackTrace();
            return allUsersButtonIsVisible;
        }
    }

    public String getUserNameErrorMessage() {
        String userNameErrorMessage = "";
        try {
            userNameErrorMessage = userNameError.getText();
            return userNameErrorMessage;
        } catch (Exception e) {
            System.out.println("Unable to get user name error message : " + e.getClass().getName());
            e.printStackTrace();
            return userNameErrorMessage;
        }
    }

    public String getUserEmailErrorMessage() {
        String userEmailIDErrorMessage = "";
        try {
            userEmailIDErrorMessage = emailError.getText();
            return userEmailIDErrorMessage;
        } catch (Exception e) {
            System.out.println("Unable to get user email id error message : " + e.getClass().getName());
            e.printStackTrace();
            return userEmailIDErrorMessage;
        }
    }

    public String getUserPasswordMessage() {
        String userPasswordMessage = "";
        try {
            userPasswordMessage = passwordError.getText();
            return userPasswordMessage;
        } catch (Exception e) {
            System.out.println("Unable to get user password error message : " + e.getClass().getName());
            e.printStackTrace();
            return userPasswordMessage;
        }
    }

    public String getUserConfirmPasswordMessage() {
        String userConfirmPasswordMessage = "";
        try {
            userConfirmPasswordMessage = confirmPasswordError.getText();
            return userConfirmPasswordMessage;
        } catch (Exception e) {
            System.out.println("Unable to get user confirm password error message : " + e.getClass().getName());
            e.printStackTrace();
            return userConfirmPasswordMessage;
        }
    }

    public String getUserNameInputFieldDisplayedText() {
        String userNamePlaceHolderText = "";
        try {
            userNamePlaceHolderText = name.getAttribute("placeholder");
            System.out.println("User Name Input Field Text : " + userNamePlaceHolderText);
            return userNamePlaceHolderText;
        } catch (Exception e) {
            System.out.println("Error in fetching User Name Input Field Text : " + e.getClass().getName());
            e.printStackTrace();
            return userNamePlaceHolderText;
        }
    }

    public String getUserEMailIDInputFieldDisplayedText() {
        String userEmailIDPlaceHolderText = "";
        try {
            userEmailIDPlaceHolderText = emailID.getAttribute("placeholder");
            System.out.println("User EmailID Input Field Text : " + userEmailIDPlaceHolderText);
            return userEmailIDPlaceHolderText;
        } catch (Exception e) {
            System.out.println("Error in fetching User EmailID Input Field Text : " + e.getClass().getName());
            e.printStackTrace();
            return userEmailIDPlaceHolderText;
        }
    }

    public String getUserPasswordInputFieldDisplayedText() {
        String userPasswordPlaceHolderText = "";
        try {
            userPasswordPlaceHolderText = passWord.getAttribute("placeholder");
            System.out.println("User Password Input Field Text : " + userPasswordPlaceHolderText);
            return userPasswordPlaceHolderText;
        } catch (Exception e) {
            System.out.println("Error in fetching User Password Input Field Text : " + e.getClass().getName());
            e.printStackTrace();
            return userPasswordPlaceHolderText;
        }
    }

    public String getUserConfirmPasswordInputFieldText() {
        String userConfirmPasswordPlaceHolderText = "";
        try {
            userConfirmPasswordPlaceHolderText = confirmPassword.getAttribute("placeholder");
            System.out.println("User Confirm Password Input Field Text : " + userConfirmPasswordPlaceHolderText);
            return userConfirmPasswordPlaceHolderText;
        } catch (Exception e) {
            System.out.println("Error in fetching User Confirm Password Input Field Text : " + e.getClass().getName());
            e.printStackTrace();
            return userConfirmPasswordPlaceHolderText;
        }
    }

    public void generateNewUser(String userName, String userEmail, String password, String confirmPassword) {
        try {
            setName(userName);
            setEmailID(userEmail);
            setPassword(password);
            setConfirmPassword(confirmPassword);
            clickSubmitButton();
            System.out.println("Successfully Submitted New user Details ");
        } catch (Exception e) {
            System.out.println("Exception while generating new User : " + e.getClass().getName());
            e.printStackTrace();
        }
    }
}
