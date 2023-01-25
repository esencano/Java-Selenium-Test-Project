package listeners;

import io.qameta.allure.Allure;
import loggers.Log4jLogger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import utilities.TestUtilities;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestListeners implements ITestListener {
    public void onTestFailure(ITestResult result) {
        try{
            ITestContext context = result.getTestContext();
            String getDriverContext = result.getName()+"_driver";

            WebDriver driver = (WebDriver) context.getAttribute(getDriverContext);

            TestUtilities testUtilities = new TestUtilities();
            File ss = testUtilities.getScreenShoot(driver);

            String timeStamp = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss").format(new Date());
            String ssName = result.getName()+"_"+timeStamp;

            String path = testUtilities.saveSS(ss,ssName);
            String relativePath = Path.of("test-output").relativize(Path.of(path)).toString(); // test-output should be found dynamicly

            Allure.addAttachment(ssName, new ByteArrayInputStream(FileUtils.readFileToByteArray(ss))); // allure report attachment
            Reporter.log("<br><a href='"+ relativePath + "'> <img src='"+ relativePath + "' height='100' width='100'/> </a><br>"); // testng report attachment
        }catch (Exception e){
           Log4jLogger.debug("Got Error While Saving Screenshot. Error: " + e.getMessage());
        }
    }
}
