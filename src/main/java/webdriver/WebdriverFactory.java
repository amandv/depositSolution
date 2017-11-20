package webdriver;

/**
 * Created by amanpreet.oberoi on 11/15/2017.
 */

import config.ConfigReader;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

import static webdriver.WebdriverType.valueOf;

public class WebdriverFactory {
    private WebDriver webdriver;

    public WebDriver getDriver() throws Exception {
        if (webdriver == null) {
            WebdriverType driverType = determineEffectiveDriverType();
            instantiateWebDriver(driverType);
        }
        return webdriver;
    }

    public void quitDriver() {
        if (webdriver != null) {
            webdriver.quit();
        }
    }

    private WebdriverType determineEffectiveDriverType() {
        WebdriverType driverType = null;
        try {
            driverType = valueOf(ConfigReader.getInstance().getBrowserName().toUpperCase());
            return driverType;
        } catch (IllegalArgumentException ignored) {
            System.err.println("Unknown browser specified " + ConfigReader.getInstance().getBrowserName());
            if (driverType == null) {
                System.exit(1);
            }
            return driverType;
        }
    }

    private void instantiateWebDriver(WebdriverType driverType) throws MalformedURLException {
        System.out.println("Driver Type Selected : " + driverType.name());
        webdriver = driverType.getWebDriverObject(driverType.getDesiredCapabilities());
    }
}
