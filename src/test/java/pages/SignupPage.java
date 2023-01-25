package pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import locators.SignupPageLocators;
import models.User;
import org.openqa.selenium.WebDriver;

public class SignupPage extends BasePage {

    public final SignupPageLocators signupPageLocators = new SignupPageLocators();

    @Step
    public void gotoSignupPage(WebDriver driver){
        driver.get(URL_SIGNUP);
    }

    @Step
    @Attachment
    public boolean waitUntilSignupPageLoaded(WebDriver driver){
        return waitUntilURLContains(driver,URL_SIGNUP);
    }
    @Step
    @Attachment
    public String getSignupPageHeader(WebDriver driver){return getTextOfElementByLocator(driver,signupPageLocators.header);}

    @Step
    public void clickSignupButton(WebDriver driver){clickElementByLocator(driver,signupPageLocators.signupButton);}
    @Step
    @Attachment
    public boolean isEnabledSignupButton(WebDriver driver){return isElementEnabledByLocator(driver, signupPageLocators.signupButton);}

    @Step
    public void fillFirstNameField(WebDriver driver,String value){fillElementByLocator(driver,signupPageLocators.firstNameField,value);}
    @Step
    @Attachment
    public String getFirstNameError(WebDriver driver){return getTextOfElementByLocator(driver,signupPageLocators.firstNameErrorText);}

    @Step
    public void fillLastNameField(WebDriver driver,String value){fillElementByLocator(driver,signupPageLocators.lastNameField,value);}
    @Step
    @Attachment
    public String getLastNameError(WebDriver driver){return getTextOfElementByLocator(driver,signupPageLocators.lastNameErrorText);}

    @Step
    public void fillUserNameField(WebDriver driver,String value){fillElementByLocator(driver,signupPageLocators.userNameField,value);}
    @Step
    @Attachment
    public String getUserNameError(WebDriver driver){return getTextOfElementByLocator(driver,signupPageLocators.userNameErrorText);}

    @Step
    public void fillPasswordField(WebDriver driver,String value){fillElementByLocator(driver,signupPageLocators.passwordField,value);}
    @Step
    @Attachment
    public String getPasswordError(WebDriver driver){return getTextOfElementByLocator(driver,signupPageLocators.passwordErrorText);}

    @Step
    public void fillConfirmPasswordField(WebDriver driver,String value){fillElementByLocator(driver,signupPageLocators.confirmPasswordField,value);}
    @Step
    @Attachment
    public String getConfirmPasswordError(WebDriver driver){return getTextOfElementByLocator(driver,signupPageLocators.confirmPasswordErrorText);}

    @Step
    public void clearPasswordField(WebDriver driver){clearElement(driver, signupPageLocators.passwordField);}
    @Step
    public void clearConfirmPasswordField(WebDriver driver){clearElement(driver, signupPageLocators.confirmPasswordField); }

    @Step
    public void clickSigninButton(WebDriver driver){clickElementByLocator(driver,signupPageLocators.signinButton);}

    @Step
    public void clickHeader(WebDriver driver){clickElementByLocator(driver,signupPageLocators.header);}

    @Step
    public void fillUserRegisterForm(WebDriver driver,User user){
        fillFirstNameField(driver,user.getFirstName());
        fillLastNameField(driver,user.getLastName());
        fillUserNameField(driver,user.getUsername());
        fillPasswordField(driver,user.getPassword());
        fillConfirmPasswordField(driver,user.getPassword());
    }

    @Step
    @Attachment
    public User registerRandomUser(WebDriver driver){
        User user = User.getRandomInstance();
        gotoSignupPage(driver);
        fillUserRegisterForm(driver,user);
        clickSignupButton(driver);
        return user;
    }

}
