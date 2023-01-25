package tests;

import managers.DriverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import org.testng.annotations.*;
import utilities.TestProperties;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Arrays;

@Listeners(listeners.TestListeners.class)
public class BaseTest {
    // 1- Read from parameter
    // 2- if parameters is not given then read  browser property from test.properties
    protected WebDriver driver;
    private final String[] browserTypes = {"chrome","firefox","opera","remote-chrome","remote-firefox","remote-edge"}; //first one is default

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void BaseSetup(@Optional("browser")String browser,Method method, ITestContext context) throws MalformedURLException {
        if (!isBrowserExist(browser)){
            TestProperties properties = new TestProperties();
            String browserProp = properties.readProperty("browser");
            if (isBrowserExist(browserProp)){
                browser = browserProp;

            }else{
                browser = browserTypes[0];
            }
        }
        driver = DriverFactory.getDriverInstance(browser);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // to find correct drivers on listeners otherwise there can be problem in parallel test execution
        String getDriverContext = method.getName()+"_driver";
        context.setAttribute(getDriverContext,driver);
        context.setAttribute("browser_name",browser);
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(){
        driver.quit();
    }

    private boolean isBrowserExist(String browser){
        return Arrays.stream(browserTypes).anyMatch(browser.toLowerCase()::equals);
    }
    public boolean isBrowserRemote(String browserName){
        return browserName.startsWith("remote-") ? true : false;
    }
}