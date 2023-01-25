package tests;

import loggers.Log4jLogger;
import org.apache.groovy.json.internal.Chr;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.BasePage;
import utilities.TestProperties;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

@Test(groups = {"btest"})
@Listeners(listeners.TestListeners.class)
public class bTest {

    @Test(groups = {"b2test"})
    void bTest2() throws MalformedURLException, InterruptedException {
        WebDriver driver = new SafariDriver();
        driver.get("https://www.facebook.com");
        driver.quit();
    }
    @Test(groups = {"b2test"})
    void bTest3() throws MalformedURLException, InterruptedException {
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://85.90.244.177:4444/wd/hub"),options);
        driver.get("https://www.facebook.com");
        Thread.sleep(10000);
        driver.quit();
    }
    @Test(groups = {"b2test","btestx"})
    void bTest4() {
        System.out.println("#########TEST#########");
        Log4jLogger.info("#########ERCAN#########");
        Assert.assertTrue(false);

    }

    @BeforeMethod
    private void x(ITestContext context, Method method) throws MalformedURLException {

    }
}
