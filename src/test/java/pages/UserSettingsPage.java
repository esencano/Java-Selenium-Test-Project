package pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import locators.UserSettingPageLocators;
import org.openqa.selenium.WebDriver;

public class UserSettingsPage extends BasePage{
    public final UserSettingPageLocators userSettingPageLocators = new UserSettingPageLocators();

    @Step
    public void gotoUserSettingsPage(WebDriver driver){driver.get(URL_USER_SETTING);}

    @Step
    @Attachment
    public boolean waitUntilUserSettingsPageLoaded(WebDriver driver){
        return waitUntilURLContains(driver,URL_USER_SETTING);
    }

    @Step
    @Attachment
    public String getHeaderText(WebDriver driver){return getTextOfElementByLocator(driver,userSettingPageLocators.header);}

    @Step
    public void fillFirstNameField(WebDriver driver, String value){fillElementByLocator(driver,userSettingPageLocators.firstNameField,value);}
    @Step
    public void fillLastNameField(WebDriver driver, String value){fillElementByLocator(driver,userSettingPageLocators.lastNameField,value);}
    @Step
    public void fillEmailField(WebDriver driver, String value){fillElementByLocator(driver,userSettingPageLocators.emailField,value);}
    @Step
    public void fillPhoneNumberField(WebDriver driver, String value){fillElementByLocator(driver,userSettingPageLocators.phoneNumberField,value);}

    @Step
    public void clickSaveButton(WebDriver driver){clickElementByLocator(driver,userSettingPageLocators.saveButton);}
    @Step
    @Attachment
    public boolean isEnabledSaveButton(WebDriver driver){return isElementEnabledByLocator(driver,userSettingPageLocators.saveButton);}
    @Step
    @Attachment
    public String getFirstNameValue(WebDriver driver){return  getValueOfElementByLocator(driver,userSettingPageLocators.firstNameField);}
    @Step
    @Attachment
    public String getLastNameValue(WebDriver driver){return  getValueOfElementByLocator(driver,userSettingPageLocators.lastNameField);}
    @Step
    @Attachment
    public String getEmailValue(WebDriver driver){return  getValueOfElementByLocator(driver,userSettingPageLocators.emailField);}
    @Step
    @Attachment
    public String getPhoneNumberValue(WebDriver driver){return getValueOfElementByLocator(driver,userSettingPageLocators.phoneNumberField);}
    @Step
    @Attachment
    public String getFirstNameError(WebDriver driver){return getTextOfElementByLocator(driver,userSettingPageLocators.firstNameErrorText);}
    @Step
    @Attachment
    public String getLastNameError(WebDriver driver){return getTextOfElementByLocator(driver,userSettingPageLocators.lastNameErrorText);}
    @Step
    @Attachment
    public String getEmailError(WebDriver driver){return getTextOfElementByLocator(driver,userSettingPageLocators.emailErrorText);}
    @Step
    @Attachment
    public String getPhoneNumberError(WebDriver driver){return getTextOfElementByLocator(driver,userSettingPageLocators.phoneNumberErrorText);}
    @Step
    @Attachment
    public boolean isFirstNameErrorDisplayed(WebDriver driver){return isElementDisplayedByLocator(driver,userSettingPageLocators.firstNameErrorText);}
    @Step
    @Attachment
    public boolean isLastNameErrorDisplayed(WebDriver driver){return isElementDisplayedByLocator(driver,userSettingPageLocators.lastNameErrorText);}
    @Step
    @Attachment
    public boolean isPhoneNumberErrorDisplayed(WebDriver driver){return isElementDisplayedByLocator(driver,userSettingPageLocators.phoneNumberErrorText);}
    @Step
    @Attachment
    public boolean isEmailErrorDisplayed(WebDriver driver){return isElementDisplayedByLocator(driver,userSettingPageLocators.emailErrorText);}

    @Step
    public void clearFirstNameField(WebDriver driver){clearElement(driver, userSettingPageLocators.firstNameField);}
    @Step
    public void clearLastNameField(WebDriver driver){clearElement(driver, userSettingPageLocators.lastNameField);}
    @Step
    public void clearPhoneNumberField(WebDriver driver){clearElement(driver, userSettingPageLocators.phoneNumberField);}
    @Step
    public void clearEmailField(WebDriver driver){clearElement(driver, userSettingPageLocators.emailField);}
    @Step
    public void clickHeader(WebDriver driver){clickElementByLocator(driver,userSettingPageLocators.header);}

}
