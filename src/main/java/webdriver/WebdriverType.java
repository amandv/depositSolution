package webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by amanpreet.oberoi on 11/15/2017.
 */

public enum WebdriverType implements WebdriverSetup {

    FIREFOX {
        public DesiredCapabilities getDesiredCapabilities() {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\resources\\geckodriver.exe");
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
            desiredCapabilities.setCapability("marionette", true);
            return desiredCapabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new FirefoxDriver(capabilities);
        }
    },
    CHROME {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\resources\\Chromedriver.exe");
            Map<String, Object> prefs = new HashMap<String, Object>();
            ChromeOptions options = new ChromeOptions();
            options.addArguments(Arrays.asList("allow-running-insecure-content", "ignore-certificate-errors",
                    "test-type", "allow-http-screen-capture", "enable-logging",
                    "--disable-flash-3d", "--disable-flash-stage3d", "--start-maximized", "--dns-prefetch-disable", "--disable-bundled-ppapi-flash"));
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", prefs);
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new ChromeDriver(capabilities);
        }
    },
    IE {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
            capabilities.setCapability("requireWindowFocus", true);
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new InternetExplorerDriver(capabilities);
        }
    },
    EDGE {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.edge();
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new EdgeDriver(capabilities);
        }
    },
    SAFARI {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.safari();
            capabilities.setCapability("safari.cleanSession", true);
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new SafariDriver(capabilities);
        }
    },
    OPERA {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.operaBlink();
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new OperaDriver(capabilities);
        }
    },
    PHANTOMJS {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
            capabilities.setJavascriptEnabled(true);
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
                    new String[]{"--webdriver-logfile=" + System.getProperty("user.dir") + "\\webdriver.log"});
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, System.getProperty("user.dir") + "\\resources\\phantomjs.exe");
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new PhantomJSDriver(capabilities);
        }
    },
    CHROME_HEADLESS {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\resources\\Chromedriver.exe");
            Map<String, Object> prefs = new HashMap<String, Object>();
            ChromeOptions options = new ChromeOptions();
            options.addArguments(Arrays.asList("allow-running-insecure-content", "ignore-certificate-errors",
                    "test-type", "allow-http-screen-capture", "enable-logging",
                    "--disable-flash-3d", "--disable-flash-stage3d", "--start-maximized", "--dns-prefetch-disable", "--disable-bundled-ppapi-flash", "--headless"));
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", prefs);
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new ChromeDriver(capabilities);
        }
    };
}