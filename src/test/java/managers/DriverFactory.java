package managers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


public class DriverFactory {
    // driver version option can be added
    public static WebDriver getDriverInstance(String driverName) throws MalformedURLException {
        WebDriver driver;
        String remoteDriverURL = System.getenv("REMOTE_HUB_URL");
        switch (driverName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "opera":
                WebDriverManager.operadriver().setup();
                OperaOptions operaOptions = new OperaOptions();
                operaOptions.addArguments("--start-maximized");
                driver = new OperaDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "remote-chrome":
                ChromeOptions remoteChromeoptions = new ChromeOptions();
                driver = new RemoteWebDriver(new URL(remoteDriverURL),remoteChromeoptions);
                break;
            case "remote-firefox":
                FirefoxOptions remoteFirefoxOptions = new FirefoxOptions();
                driver = new RemoteWebDriver(new URL(remoteDriverURL),remoteFirefoxOptions);
                break;
            case "remote-edge":
                EdgeOptions remoteEdgeOptions = new EdgeOptions();
                driver = new RemoteWebDriver(new URL(remoteDriverURL),remoteEdgeOptions);
                break;
            default:
                throw new Error("Driver can't be initialized");
        }
        return driver;
    }
}
