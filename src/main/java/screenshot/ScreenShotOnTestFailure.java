package screenshot;

/**
 * Created by amanpreet.oberoi on 11/18/2017.
 */

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import config.ConfigReader;
import org.apache.commons.io.FileUtils;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.TestUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ScreenShotOnTestFailure implements MethodRule {
    private WebDriver driver;

    public ScreenShotOnTestFailure(WebDriver driver) {
        this.driver = driver;
    }

    public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object o) {
        return new Statement() {
            String timeStamp = TestUtils.getCurrentDateTime();

            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } catch (Throwable t) {
                    ExtentReports extent = createReport("TestReport");
                    ExtentTest test = extent.startTest(frameworkMethod.getName(), "Test Failed, click here for further details");
                    test.log(LogStatus.FAIL, Arrays.toString(t.getStackTrace()));
                    test.log(LogStatus.FAIL, test.addScreenCapture(getScreenShotPath(frameworkMethod.getName() + "_Failure_", timeStamp)) + t.getMessage());
                    flushReports(extent, test);
                    if (ConfigReader.getInstance().isScreenShotOnFailure()) {
                        captureScreenShot(frameworkMethod.getName() + "_Failure_", timeStamp);
                    }
                    throw t;
                }
            }

            public void captureScreenShot(String fileName, String timeStamp) throws IOException {
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File(getScreenShotPath(fileName, timeStamp)));
            }

            private String getScreenShotPath(String fileName, String timeStamp) {
                String imageFileDir = System.getProperty("user.dir") + "\\screenshots\\";
                TestUtils.checkAndCreateDirectory(imageFileDir);
                String screenShotFileName = imageFileDir + "Screenshot_" + fileName + "_" + timeStamp + ".png";
                return screenShotFileName;
            }

            private ExtentReports createReport(String filenameOfReport) {
                String filePath = System.getProperty("user.dir") + "\\HTMLReport\\";
                TestUtils.checkAndCreateDirectory(filePath);
                String fileName = filePath + filenameOfReport + ".html";
                ExtentReports extent = new ExtentReports(fileName, false);
                return extent;
            }

            private void flushReports(ExtentReports extent, ExtentTest test) {
                extent.endTest(test);
                extent.flush();
            }
        };
    }
}