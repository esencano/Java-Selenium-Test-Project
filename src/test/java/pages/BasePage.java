package pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.html5.RemoteWebStorage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    private final String envVariableBaseURL = System.getenv("APPLICATION_FE_URL");
    private final String defaultBaseURL = "http://localhost:3000";

    public final String BASE_URL = !(envVariableBaseURL == null || envVariableBaseURL.equals("")) ?envVariableBaseURL : defaultBaseURL; // can be done private and change assign approach
    protected final String PATH_SIGNIN = "/signin";
    protected final String PATH_SIGNUP = "/signup";
    protected final String PATH_NEW_TRANSACTION = "/transaction/new";
    protected final String PATH_BANK_ACCOUNTS = "/bankaccounts";
    protected final String PATH_USER_SETTINGS = "/user/settings";
    protected final String PATH_NOTIFICATIONS= "/notifications";
    protected final String PATH_CONTACTS = "/contacts";
    protected final String PATH_PERSONAL = "/personal";
    protected final String PATH_TRANSACTION_DETAILS = "/transaction/"; // id should be added after path


    public final String URL_SIGNIN = BASE_URL + PATH_SIGNIN;
    public final String URL_SIGNUP = BASE_URL + PATH_SIGNUP;
    public final String URL_HOME_PAGE_PUBLIC_SECTION = BASE_URL;
    public final String URL_HOME_PAGE_CONTACTS_SECTION = BASE_URL + PATH_CONTACTS;
    public final String URL_HOME_PAGE_PERSONAL_SECTION = BASE_URL + PATH_PERSONAL;
    public final String URL_BANK_ACCOUNTS = BASE_URL + PATH_BANK_ACCOUNTS;
    public final String URL_NEW_TRANSACTIONS = BASE_URL + PATH_NEW_TRANSACTION;
    public final String URL_NOTIFICATIONS = BASE_URL + PATH_NOTIFICATIONS;
    public final String URL_USER_SETTING = BASE_URL + PATH_USER_SETTINGS;
    public final String URL_TRANSACTION_DETAILS = BASE_URL + PATH_TRANSACTION_DETAILS;

    public void goToBasePage(WebDriver driver){
        driver.get(BASE_URL);
    }

    @Step
    @Attachment
    public String getCurrentURL(WebDriver driver){
        return driver.getCurrentUrl();
    }

    public void clickElementByLocator(WebDriver driver, By locator){
        waitUntilClickable(driver,locator,5000);
        driver.findElement(locator).click();
    }


    public String getTextOfElementByLocator(WebDriver driver, By locator){
        waitUntilVisibilityOfElementLocated(driver,locator,5000);
        return driver.findElement(locator).getText();
    }

    public String getTextOfElement(WebElement element){
        return element.getText();
    }
    public String getValueOfElement(WebElement element){
        return getAttributeOfElement(element,"value");
    }
    public String getValueOfElementByLocator(WebDriver driver,By locator){
        waitUntilVisibilityOfElementLocated(driver,locator,5000);
        return getValueOfElement(driver.findElement(locator));
    }

    public void fillElementByLocator(WebDriver driver, By locator, String value){
        waitUntilVisibilityOfElementLocated(driver,locator,5000);
        driver.findElement(locator).sendKeys(value);
    }

    public boolean isElementEnabledByLocator(WebDriver driver, By locator){
        waitUntilPresenceOfElementLocated(driver,locator, 5000);
        return driver.findElement(locator).isEnabled();
    }

    public boolean isElementDisplayedByLocator(WebDriver driver, By locator){
        return !driver.findElements(locator).isEmpty();
    }

    public boolean isElementClassContains(WebElement element, String value) {
        return element.getAttribute("class").contains(value);
    }

    public boolean isElementCheckedByLocator(WebDriver driver, By locator){
        waitUntilPresenceOfElementLocated(driver,locator,5000);
        return driver.findElement(locator).isSelected();
    }

    // clear function doesn't work for macos that's why this function is implemented
    public void clearElement(WebDriver driver, By locator){
        waitUntilVisibilityOfElementLocated(driver,locator,2000);
        WebElement element = driver.findElement(locator);
        String inputText = element.getAttribute("value");
        if( inputText != null ) {
            for(int i=0; i<inputText.length();i++) {
                element.sendKeys(Keys.BACK_SPACE);
            }
        }
    }
    

    public String getAttributeOfElement(WebElement element,String attribute){
        return element.getAttribute(attribute);
    }

    public void waitUntilPresenceOfElementLocated(WebDriver driver, By elementLocator , int timeout){
        WebDriverWait wait = getWait(driver,timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
    }

    public void waitUntilVisibilityOfElementLocated(WebDriver driver, By locator , int timeout){
        WebDriverWait wait = getWait(driver,timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitUntilVisibilityOf(WebDriver driver, WebElement element , int timeout){
        WebDriverWait wait = getWait(driver,timeout);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitUntilClickable(WebDriver driver,By elementLocator ,int timeout){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));
        wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
    }

    // this function will return true if page is loaded in given timeout otherwise it will return false instead of throwing error
    public boolean waitUntilURLContains(WebDriver driver, String value){
        WebDriverWait wait = getWait(driver,5000);
        try{
              wait.until(ExpectedConditions.urlContains(value));
              return true;
        }catch (TimeoutException e){
            return false;
        }
    }

    public void waitUntilURLTobe(WebDriver driver, String value){
        WebDriverWait wait = getWait(driver,5000);
        wait.until(ExpectedConditions.urlToBe(value));
    }

    @Step
    public void waitUntilBasePageLoaded(WebDriver driver){
        waitUntilURLTobe(driver,BASE_URL+"/");
    }

    public void waitUntilNumbersOfElementTobe(WebDriver driver,By locator ,int size){
        WebDriverWait driverWait = new WebDriverWait(driver,Duration.ofMillis(5000));
        driverWait.until(ExpectedConditions.numberOfElementsToBe(locator, size));
    }

    @Step
    public String getLocalStorage(WebDriver driver, String key, boolean isRemoteDriver){
        return getWebStorage(driver,isRemoteDriver).getLocalStorage().getItem(key);
    }

    @Step
    public String getSessionStorage(WebDriver driver, String key, boolean isRemoteDriver){
        return getWebStorage(driver,isRemoteDriver).getSessionStorage().getItem(key);
    }

    @Step
    public void setLocalStorage(WebDriver driver, String key, String value, boolean isRemoteDriver){
        getWebStorage(driver,isRemoteDriver).getLocalStorage().setItem(key,value == null ? "" : value);
    }

    @Step
    public void setSessionStorage(WebDriver driver, String key, String value, boolean isRemoteDriver){
        getWebStorage(driver,isRemoteDriver).getSessionStorage().setItem(key,value == null ? "" : value);
    }

    private WebStorage getWebStorage(WebDriver driver, boolean isRemoteDriver){
        if(isRemoteDriver){
            RemoteExecuteMethod executeMethod = new RemoteExecuteMethod((RemoteWebDriver) driver);
            return new RemoteWebStorage(executeMethod);
        }
        return ((WebStorage)driver);
    }

    private WebDriverWait getWait(WebDriver driver, int timeout){
        return  new WebDriverWait(driver, Duration.ofMillis(timeout));
    }
}
