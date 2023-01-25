package pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import locators.NavbarLocators;
import org.openqa.selenium.WebDriver;

public class Navbar extends BasePage{

    public final NavbarLocators navbarLocators = new NavbarLocators();
    @Step
    @Attachment
    public boolean isNavbarVisible(WebDriver driver){ return  driver.findElement(navbarLocators.navbar).isDisplayed(); }

    @Step
    public void clickNavbarToggleButton(WebDriver driver){
        clickElementByLocator(driver,navbarLocators.navbarToggleButton);}

    @Step
    @Attachment
    public String getFullNameText(WebDriver driver){return getTextOfElementByLocator(driver,navbarLocators.navbarFullNameText);}

    @Step
    @Attachment
    public String getUsernameText(WebDriver driver){return getTextOfElementByLocator(driver,navbarLocators.navbarUsernameText);}

    @Step
    @Attachment
    public String getAccountBalanceText(WebDriver driver){return getTextOfElementByLocator(driver,navbarLocators.navbarAccountBalanceText);}

    @Step
    public void clickHomeButton(WebDriver driver){
        clickElementByLocator(driver,navbarLocators.navbarHomeButton);}

    @Step
    public void clickMyAccountButton(WebDriver driver){
        clickElementByLocator(driver,navbarLocators.navbarMyAccountButton);}

    @Step
    public void clickBankAccountstButton(WebDriver driver){
        clickElementByLocator(driver,navbarLocators.navbarBankAccountsButton);}
    @Step
    public void clickNotificationsButton(WebDriver driver){
        clickElementByLocator(driver,navbarLocators.navbarNotificationsButton);}
    @Step
    public void clickLogoutButton(WebDriver driver){
        clickElementByLocator(driver,navbarLocators.navbarLogouttButton);}

    @Step
    public void logout(WebDriver driver){
        if(!isNavbarVisible(driver)){
            clickNavbarToggleButton(driver);
        }
        clickLogoutButton(driver);
    }

}
