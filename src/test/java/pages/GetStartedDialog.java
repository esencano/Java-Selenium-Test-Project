package pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import locators.GetStartedDialogLocators;
import org.openqa.selenium.WebDriver;

public class GetStartedDialog extends BasePage{
    public final GetStartedDialogLocators getStartedDialogLocators = new GetStartedDialogLocators();

    @Step
    @Attachment
    public boolean isDialogExist(WebDriver driver){return driver.findElements(getStartedDialogLocators.dialog).size() == 0 ? false : true;}
    @Step
    public void clickNextButton(WebDriver driver){
        clickElementByLocator(driver,getStartedDialogLocators.nextButton); }
    @Step
    @Attachment
    public String getDialogFirstPageTitle(WebDriver driver){return getTextOfElementByLocator(driver,getStartedDialogLocators.dialogFirstPageTitle);}
    @Step
    @Attachment
    public String getFirstPageDialogText(WebDriver driver){return getTextOfElementByLocator(driver,getStartedDialogLocators.dialogFirstPageText); }
    @Step
    @Attachment
    public String getDialogSecondPageTitle(WebDriver driver){return getTextOfElementByLocator(driver,getStartedDialogLocators.dialogSecondPageTitle);}

    @Step
    public void fillBankName(WebDriver driver, String value){
        fillElementByLocator(driver,getStartedDialogLocators.bankNameField,value);}
    @Step
    public void fillRoutingNumber(WebDriver driver, String value){
        fillElementByLocator(driver,getStartedDialogLocators.routingNumberField,value);}
    @Step
    public void fillAccountNumber(WebDriver driver, String value){
        fillElementByLocator(driver,getStartedDialogLocators.accountNumberField,value);}
    @Step
    public void clickSaveButton(WebDriver driver){
        clickElementByLocator(driver,getStartedDialogLocators.dialogSecondPageSaveButton);}
    @Step
    @Attachment
    public String getDialogThirdPageTitle(WebDriver driver){return getTextOfElementByLocator(driver,getStartedDialogLocators.dialogThirdPageTitle);}
    @Step
    @Attachment
    public String getThirdPageDialogText(WebDriver driver){return getTextOfElementByLocator(driver,getStartedDialogLocators.dialogThirdPageText); }

    @Step
    public void clickDoneButton(WebDriver driver){
        clickElementByLocator(driver,getStartedDialogLocators.dialogThirdPageDoneButton);}
    @Step
    @Attachment
    public boolean isEnabledSaveButton(WebDriver driver){return isElementEnabledByLocator(driver,getStartedDialogLocators.dialogSecondPageSaveButton);}

    @Step
    public void clickDialogSecondPageTitle(WebDriver driver){
        clickElementByLocator(driver,getStartedDialogLocators.dialogSecondPageTitle);}
    @Step
    @Attachment
    public String getDialogBankNameError(WebDriver driver){return getTextOfElementByLocator(driver,getStartedDialogLocators.dialogBankNameErrorText);}
    @Step
    @Attachment
    public String getDialogRoutingNumberError(WebDriver driver){return getTextOfElementByLocator(driver,getStartedDialogLocators.dialogRoutingNumberErrorText);}
    @Step
    @Attachment
    public String getDialogAccountNumberError(WebDriver driver){return getTextOfElementByLocator(driver,getStartedDialogLocators.dialogAccountNumberErrorText);}

    @Step
    public void clearDialogBankName(WebDriver driver){clearElement(driver, getStartedDialogLocators.bankNameField);}
    @Step
    public void clearDialogRoutingNumber(WebDriver driver){clearElement(driver, getStartedDialogLocators.routingNumberField);}
    @Step
    public void clearDialogAccountNumber(WebDriver driver){clearElement(driver, getStartedDialogLocators.accountNumberField);}

    @Step
    public void clearDialogAllFields(WebDriver driver){
        clearDialogBankName(driver);
        clearDialogRoutingNumber(driver);
        clearDialogAccountNumber(driver);
    }
}
