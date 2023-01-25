package pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import locators.NotificationPageLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NotificationsPage extends BasePage{

    private final String  paymentRecievedIcon = "M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1.41 16.09V20h-2.67v-1.93c-1.71-.36-3.16-1.46-3.27-3.4h1.96c.1 1.05.82 1.87 2.65 1.87 1.96 0 2.4-.98 2.4-1.59 0-.83-.44-1.61-2.67-2.14-2.48-.6-4.18-1.62-4.18-3.67 0-1.72 1.39-2.84 3.11-3.21V4h2.67v1.95c1.86.45 2.79 1.86 2.85 3.39H14.3c-.05-1.11-.64-1.87-2.22-1.87-1.5 0-2.4.68-2.4 1.64 0 .84.65 1.39 2.67 1.91s4.18 1.39 4.18 3.91c-.01 1.83-1.38 2.83-3.12 3.16z";
    private final String  commentedIcon = "M21.99 4c0-1.1-.89-2-1.99-2H4c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h14l4 4-.01-18zM17 14H7c-.55 0-1-.45-1-1s.45-1 1-1h10c.55 0 1 .45 1 1s-.45 1-1 1zm0-3H7c-.55 0-1-.45-1-1s.45-1 1-1h10c.55 0 1 .45 1 1s-.45 1-1 1zm0-3H7c-.55 0-1-.45-1-1s.45-1 1-1h10c.55 0 1 .45 1 1s-.45 1-1 1z";
    private final String  likedIcon = "M21 8h-6.31l.95-4.57.03-.32c0-.41-.17-.79-.44-1.06L14.17 1 7.59 7.59C7.22 7.95 7 8.45 7 9v10c0 1.1.9 2 2 2h9c.83 0 1.54-.5 1.84-1.22l3.02-7.05c.09-.23.14-.47.14-.73v-2c0-1.1-.9-2-2-2zm0 4l-3 7H9V9l4.34-4.34L12.23 10H21v2zM1 9h4v12H1z";
    private final String  requestedPaymentIcon = "M20 4H4c-1.11 0-1.99.89-1.99 2L2 18c0 1.11.89 2 2 2h16c1.11 0 2-.89 2-2V6c0-1.11-.89-2-2-2zm0 14H4v-6h16v6zm0-10H4V6h16v2z";
    public final NotificationPageLocators notificationPageLocators = new NotificationPageLocators();

    @Step
    public void gotoNotificationsPage(WebDriver driver){driver.get(URL_NOTIFICATIONS);}

    @Step
    @Attachment
    public boolean waitUntilNotificationPageLoaded(WebDriver driver){
        return waitUntilURLContains(driver,URL_NOTIFICATIONS);
    }
    @Step
    @Attachment
    public String getHeaderText(WebDriver driver){return getTextOfElementByLocator(driver,notificationPageLocators.header);}

    private List<WebElement> getNotificationList(WebDriver driver){return driver.findElements(notificationPageLocators.notificationList);}

    private WebElement getNotificationByIndex(WebDriver driver, int index){return getNotificationList(driver).get(index);}
    @Step
    @Attachment
    public int getCountOfNotifications(WebDriver driver){return getNotificationList(driver).size();}

    @Step
    public void waitUntilNumbersOfNotificationsTobe(WebDriver driver, int size){
        waitUntilNumbersOfElementTobe(driver,notificationPageLocators.notificationList,size);
    }

    @Step
    @Attachment
    public boolean isNotificationIconDisplayed(WebDriver driver, int index){return !getNotificationByIndex(driver,index).findElements(notificationPageLocators.notificationIcon).isEmpty();}
    @Step
    @Attachment
    public boolean isPaymentReceivedIconDisplayed(WebDriver driver, int index){return getNotificationByIndex(driver,index).findElement(notificationPageLocators.notificationIcon).getAttribute("d").contains(paymentRecievedIcon);}
    @Step
    @Attachment
    public boolean isRequestedPaymentIconDisplayed(WebDriver driver, int index){return getNotificationByIndex(driver,index).findElement(notificationPageLocators.notificationIcon).getAttribute("d").contains(requestedPaymentIcon);}
    @Step
    @Attachment
    public boolean isCommentedIconDisplayed(WebDriver driver, int index){return getNotificationByIndex(driver,index).findElement(notificationPageLocators.notificationIcon).getAttribute("d").contains(commentedIcon);}
    @Step
    @Attachment
    public boolean isLikedIconDisplayed(WebDriver driver, int index){return getNotificationByIndex(driver,index).findElement(notificationPageLocators.notificationIcon).getAttribute("d").contains(likedIcon);}

    @Step
    @Attachment
    public String getNotificationDescription(WebDriver driver, int index){return getTextOfElement(getNotificationByIndex(driver,index).findElement(notificationPageLocators.notificationDescription));}
    @Step
    @Attachment
    public void clickDismissButton(WebDriver driver,int index){getNotificationByIndex(driver,index).findElement(notificationPageLocators.notificationDismissButton).click();}

}
