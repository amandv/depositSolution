package webdriver;

/**
 * Created by amanpreet.oberoi on 11/15/2017.
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface WebdriverSetup {

    WebDriver getWebDriverObject(DesiredCapabilities desiredCapabilities);

    DesiredCapabilities getDesiredCapabilities();
}