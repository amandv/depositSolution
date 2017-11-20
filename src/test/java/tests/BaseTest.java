package tests;

import config.ConfigReader;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import screenshot.ScreenShotOnTestFailure;
import screenshot.ScreenShotOnTestSuccess;
import webdriver.WebdriverFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by amanpreet.oberoi on 11/15/2017.
 */

public class BaseTest {
    private static List<WebdriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<WebdriverFactory>());
    private static ThreadLocal<WebdriverFactory> webDriverFactory;
    @Rule
    public ScreenShotOnTestFailure failure;

    @Rule
    public ScreenShotOnTestSuccess success;

    {
        try {
            failure = new ScreenShotOnTestFailure(webDriverFactory.get().getDriver());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    {
        try {
            success = new ScreenShotOnTestSuccess(webDriverFactory.get().getDriver());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeClass
    public static void instantiateDriverObject() {
        ConfigReader.getInstance().initProperties();
        webDriverFactory = new ThreadLocal<WebdriverFactory>() {
            @Override
            protected WebdriverFactory initialValue() {
                WebdriverFactory webdriverFactory = new WebdriverFactory();
                webDriverThreadPool.add(webdriverFactory);
                return webdriverFactory;
            }
        };
    }

    public static WebDriver getDriver() throws Exception {
        return webDriverFactory.get().getDriver();
    }

    @AfterClass
    public static void clearCookies() throws Exception {
        getDriver().manage().deleteAllCookies();
        for (WebdriverFactory webdriverFactory : webDriverThreadPool) {
            webdriverFactory.quitDriver();
        }
    }
}
