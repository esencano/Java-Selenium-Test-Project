package pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import locators.SigninPageLocators;
import models.User;
import org.openqa.selenium.WebDriver;

public class SigninPage extends BasePage{

    public final SigninPageLocators signinPageLocators = new SigninPageLocators();

    @Step
    public void gotoSigninPage(WebDriver driver){ driver.get(URL_SIGNIN); }

    @Step
    @Attachment
    public boolean waitUntilSigninPageLoaded(WebDriver driver){
        return waitUntilURLContains(driver,URL_SIGNIN);
    }

    @Step
    @Attachment
    public String getSigninPageHeader(WebDriver driver){return getTextOfElementByLocator(driver,signinPageLocators.header);}

    @Step
    public void clickSigninButton(WebDriver driver){clickElementByLocator(driver,signinPageLocators.signInButton);}

    @Step
    public void clickSignupButton(WebDriver driver){clickElementByLocator(driver,signinPageLocators.signupButton);}

    @Step
    public void clickHeader(WebDriver driver){clickElementByLocator(driver,signinPageLocators.header);}
    @Step
    @Attachment
    public boolean isEnabledSigninButton(WebDriver driver){return isElementEnabledByLocator(driver,signinPageLocators.signInButton);}

    @Step
    public void fillUserNameField(WebDriver driver,String value){fillElementByLocator(driver,signinPageLocators.username,value);}
    @Step
    @Attachment
    public String getUserNameError(WebDriver driver){return getTextOfElementByLocator(driver,signinPageLocators.usernameErrorText);}

    @Step
    public void fillPasswordField(WebDriver driver,String value){fillElementByLocator(driver,signinPageLocators.password,value);}
    @Step
    @Attachment
    public String getPasswordError(WebDriver driver){return getTextOfElementByLocator(driver,signinPageLocators.passwordErrorText);}

    @Step
    public void clearPasswordField(WebDriver driver){clearElement(driver, signinPageLocators.password);}
    @Step
    @Attachment
    public boolean isPasswordErrorExist(WebDriver driver){return isElementDisplayedByLocator(driver,signinPageLocators.passwordErrorText);}
    @Step
    @Attachment
    public String getWrongUserNamePasswordError(WebDriver driver){return getTextOfElementByLocator(driver,signinPageLocators.wrongUserNAmePasswordError);}
    @Step
    @Attachment
    public String getUsernameFieldValue(WebDriver driver){return getValueOfElementByLocator(driver,signinPageLocators.username);}
    @Step
    @Attachment
    public String getPasswordFieldValue(WebDriver driver){return getValueOfElementByLocator(driver,signinPageLocators.password);}
    @Step
    @Attachment
    public boolean isRememberMeChecked(WebDriver driver){return isElementCheckedByLocator(driver,signinPageLocators.rememberMeCheckbox);}

    @Step
    public void selectRememberMe(WebDriver driver){driver.findElement(signinPageLocators.rememberMeCheckbox).click();}

    @Step("Login {1}")
    public void login(WebDriver driver, User user, boolean rememberME){
        gotoSigninPage(driver);
        fillUserNameField(driver,user.getUsername());
        fillPasswordField(driver,user.getPassword());
        if(rememberME){
            selectRememberMe(driver);
        }
        clickSigninButton(driver);
    }
}
