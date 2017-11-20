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

public class AllUsers {
    WebDriver driver;

    @FindBy(id = "users")
    WebElement usersTable;

    @FindBy(xpath = "//a[text()='New User']")
    WebElement newUserButton;

    @FindBy(css = "table#users tbody tr:last-child td:nth-child(1)")
    WebElement newlyAddedUserName;

    @FindBy(css = "table#users tbody tr:last-child td:nth-child(2)")
    WebElement newlyAddedUserEmailID;

    @FindBy(css = "table#users tbody tr:last-child td:nth-child(3)")
    WebElement newlyAddedUserPassword;

    public AllUsers(WebDriver driver) {
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, ConfigReader.getInstance().getElementWaitTime());
        PageFactory.initElements(factory, this);
    }

    public String getNewUserName() {
        String newUserName = "";
        try {
            newUserName = newlyAddedUserName.getText();
            System.out.println("Fetching new user name from all users table : " + newUserName);
            return newUserName;
        } catch (Exception e) {
            System.out.println("Error in fetching user name from all users table : " + e.getClass().getName());
            e.printStackTrace();
            return newUserName;
        }
    }

    public String getNewUserEmailID() {
        String newUserEmailID = "";
        try {
            newUserEmailID = newlyAddedUserEmailID.getText();
            System.out.println("Fetching new user email id from all users table : " + newUserEmailID);
            return newUserEmailID;
        } catch (Exception e) {
            System.out.println("Error in fetching user email id from all users table : " + e.getClass().getName());
            e.printStackTrace();
            return newUserEmailID;
        }
    }

    public String getNewUserPassword() {
        String newUserPassword = "";
        try {
            newUserPassword = newlyAddedUserPassword.getText();
            System.out.println("Fetching new user password from all users table : " + newUserPassword);
            return newUserPassword;
        } catch (Exception e) {
            System.out.println("Error in fetching user password from all users table : " + e.getClass().getName());
            e.printStackTrace();
            return newUserPassword;
        }
    }

    public boolean verifyNewUserButtonDisplayed() {
        boolean newUserButtonDisplayed = false;
        try {
            newUserButtonDisplayed = newUserButton.isDisplayed();
            System.out.println("Verifying new user button displayed on All Users Page : " + newUserButtonDisplayed);
            return newUserButtonDisplayed;
        } catch (Exception e) {
            System.out.println("Error while checking new user button on all users page ");
            //e.printStackTrace();
            return newUserButtonDisplayed;
        }
    }

    public void clickNewUserButton() {
        try {
            newUserButton.click();
            System.out.println("Clicking New User Button");
        } catch (Exception e) {
            System.out.println("Error while clicking new user button : " + e.getClass().getName());
            e.printStackTrace();
        }
    }
}
