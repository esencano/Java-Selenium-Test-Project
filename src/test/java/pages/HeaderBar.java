package pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import locators.HeaderBarLocators;
import org.openqa.selenium.WebDriver;

public class HeaderBar extends BasePage{

    public final HeaderBarLocators headerBarLocators = new HeaderBarLocators();

    @Step
    public void clickLogo(WebDriver driver){
        clickElementByLocator(driver,headerBarLocators.logo);}
    @Step
    public void clickNewButton(WebDriver driver){
        clickElementByLocator(driver,headerBarLocators.newButton);}
    @Step
    public void clickNotificationsIcon(WebDriver driver){
        clickElementByLocator(driver,headerBarLocators.notificationsButton);}
    @Step
    @Attachment
    public boolean isCountOfNotificationDisplayed(WebDriver driver){return !isElementClassContains(driver.findElement(headerBarLocators.notificationsCount),"MuiBadge-invisible");}
    @Step
    @Attachment
    public int getCountOfNotifications(WebDriver driver){return isCountOfNotificationDisplayed(driver) ? Integer.parseInt(getTextOfElementByLocator(driver,headerBarLocators.notificationsCount)) : 0;}
}
