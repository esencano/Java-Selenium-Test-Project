package pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import locators.NewTransactionPageLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class NewTransactionPage extends BasePage{

    public final NewTransactionPageLocators newTransactionPageLocators = new NewTransactionPageLocators();

    @Step
    public void gotoNewTransactionPage(WebDriver driver){driver.get(URL_NEW_TRANSACTIONS);}

    @Step
    @Attachment
    public boolean waitUntilNewTransactionPageLoaded(WebDriver driver){
        return waitUntilURLContains(driver,URL_NEW_TRANSACTIONS);
    }

    @Step
    @Attachment
    public boolean isSelectContactIconActivated(WebDriver driver){return isElementClassContains(getProgressIconByIndex(driver,0),"MuiStepIcon-active");}

    @Step
    @Attachment
    public boolean isSelectContactCompletedIconShowed(WebDriver driver){return isElementClassContains(getProgressIconByIndex(driver,0),"MuiStepIcon-completed");}

    @Step
    @Attachment
    public boolean isPaymentIconActivated(WebDriver driver){return isElementClassContains(getProgressIconByIndex(driver,1),"MuiStepIcon-active");}
    @Step
    @Attachment
    public boolean isPaymentCompletedIconShowed(WebDriver driver){return isElementClassContains(getProgressIconByIndex(driver,1),"MuiStepIcon-completed");}
    @Step
    @Attachment
    public boolean isIconActivatedForCompletedStep(WebDriver driver){return isElementClassContains(getProgressIconByIndex(driver,2),"MuiStepIcon-active");}
    @Step
    @Attachment
    public boolean isCompletedIconShowedForCompletedStep(WebDriver driver){return isElementClassContains(getProgressIconByIndex(driver,2),"MuiStepIcon-completed");}

    @Step
    public void clickContactsByIndex(WebDriver driver, int index){driver.findElements(newTransactionPageLocators.userList).get(index).click();}
    @Step
    public void fillAmountField(WebDriver driver, String amount){
        fillElementByLocator(driver,newTransactionPageLocators.paymentSectionAmountField,amount);}
    @Step
    public void fillNoteField(WebDriver driver, String note){
        fillElementByLocator(driver,newTransactionPageLocators.paymentSectionNoteField,note);}
    @Step
    public void clickRequestButton(WebDriver driver){
        clickElementByLocator(driver,newTransactionPageLocators.paymentSectionRequestButton);}
    @Step
    public void clickPayButton(WebDriver driver){
        clickElementByLocator(driver,newTransactionPageLocators.paymentSectionPayButton);}

    @Step
    @Attachment
    public String getUserFirstNameAndLastnameByIndex(WebDriver driver,int index){return getUserList(driver).get(index).findElement(newTransactionPageLocators.userFirstNameAndLastname).getText();}
    @Step
    @Attachment
    public String getUserUsernameByIndex(WebDriver driver,int index){return getUserList(driver).get(index).findElement(newTransactionPageLocators.userUsername).getText();}
    @Step
    @Attachment
    public String getUserPhoneNumberByIndex(WebDriver driver,int index){return getUserList(driver).get(index).findElement(newTransactionPageLocators.userPhoneNumber).getText();}
    @Step
    @Attachment
    public String getUserEmailByIndex(WebDriver driver,int index){return getUserList(driver).get(index).findElement(newTransactionPageLocators.userEmail).getText();}
    @Step
    @Attachment
    private List<WebElement> getUserList(WebDriver driver){return driver.findElements(newTransactionPageLocators.userList);}
    @Step
    @Attachment
    private List<String> getAllUsernames(WebDriver driver){return getUserList(driver).stream().map(f -> f.findElement(newTransactionPageLocators.userUsername).getText()).collect(Collectors.toList());}
    @Step
    @Attachment
    private int findIndexOfUsername(WebDriver driver,String username){return getAllUsernames(driver).indexOf("U: "+username);}
    @Step
    @Attachment
    public String getUserFirstNameAndLastnameByUsername(WebDriver driver,String username){ return getUserFirstNameAndLastnameByIndex(driver,findIndexOfUsername(driver,username));}
    @Step
    @Attachment
    public String getUserUsernameByUsername(WebDriver driver,String username){ return getUserUsernameByIndex(driver,findIndexOfUsername(driver,username));}
    @Step
    @Attachment
    public String getUserEmailByUsername(WebDriver driver,String username){ return getUserEmailByIndex(driver,findIndexOfUsername(driver,username));}
    @Step
    @Attachment
    public String getUserPhoneNumberByUsername(WebDriver driver,String username){ return getUserPhoneNumberByIndex(driver,findIndexOfUsername(driver,username));}

    @Step
    public void fillSearchField(WebDriver driver, String value){
        fillElementByLocator(driver,newTransactionPageLocators.searchField,value);}
    @Step
    @Attachment
    public boolean isAvatarExistByIndex(WebDriver driver, int index){return !getUserList(driver).get(index).findElements(newTransactionPageLocators.userAvatar).isEmpty();}
    @Step
    @Attachment
    public boolean isDefaultAvatarExistByIndex(WebDriver driver, int index){return !getUserList(driver).get(index).findElements(newTransactionPageLocators.userAvatarDefault).isEmpty();}
    @Step
    @Attachment
    public boolean isDefaultAvatarExistByUsername(WebDriver driver, String  username){return !getUserList(driver).get(findIndexOfUsername(driver,username)).findElements(newTransactionPageLocators.userAvatarDefault).isEmpty();}
    @Step
    public boolean isRequestButtonEnabled(WebDriver driver){return isElementEnabledByLocator(driver,newTransactionPageLocators.paymentSectionRequestButton);}
    @Step
    public boolean isPayButtonEnabled(WebDriver driver){return isElementEnabledByLocator(driver,newTransactionPageLocators.paymentSectionPayButton);}
    @Step
    public void clearAmountField(WebDriver driver){clearElement(driver, newTransactionPageLocators.paymentSectionAmountField);}
    @Step
    public void clearNoteField(WebDriver driver){clearElement(driver, newTransactionPageLocators.paymentSectionNoteField);}
    @Step
    @Attachment
    public String getAmountFieldError(WebDriver driver){return getTextOfElementByLocator(driver,newTransactionPageLocators.paymentSectionAmountError);}
    @Step
    @Attachment
    public String getNoteFieldError(WebDriver driver){return getTextOfElementByLocator(driver,newTransactionPageLocators.paymentSectionNoteError);}
    @Step
    @Attachment
    public boolean isAmountFieldErrorDisplayed(WebDriver driver){return isElementDisplayedByLocator(driver,newTransactionPageLocators.paymentSectionAmountError);}
    @Step
    @Attachment
    public boolean isNoteFieldErrorDisplayed(WebDriver driver){return isElementDisplayedByLocator(driver,newTransactionPageLocators.paymentSectionNoteError);}
    @Step
    public void clickNameOnSecondStep(WebDriver driver){
        clickElementByLocator(driver,newTransactionPageLocators.paymentSectionFirstNameAndLastName);}
    @Step
    @Attachment
    public String getValueOfAmountField(WebDriver driver){return getValueOfElementByLocator(driver,newTransactionPageLocators.paymentSectionAmountField);}
    @Step
    @Attachment
    public String getFirstNameAndLastnameOnSecondStep(WebDriver driver){return getTextOfElementByLocator(driver,newTransactionPageLocators.paymentSectionFirstNameAndLastName);}
    @Step
    @Attachment
    public String getFirstNameAndLastnameOnCompletedStep(WebDriver driver){return getTextOfElementByLocator(driver,newTransactionPageLocators.completeSectionFirstNameAndLastName);}
    @Step
    @Attachment
    public String getMessageOnCompletedStep(WebDriver driver){return getTextOfElement(driver.findElements(newTransactionPageLocators.completeSectionCompleteMessage).get(1));}
    @Step
    @Attachment
    public boolean isAvatarExistOnSecondStep(WebDriver driver){return isElementDisplayedByLocator(driver,newTransactionPageLocators.paymentSectionUserAvatar);}
    @Step
    @Attachment
    public boolean isAvatarExistOnCompletedStep(WebDriver driver){return isElementDisplayedByLocator(driver,newTransactionPageLocators.completeSectionUserAvatar);}
    @Step
    public void clickCompleteSectionReturnToTransactionsButton(WebDriver driver){clickElementByLocator(driver,newTransactionPageLocators.completeSectionReturnToTransactionsButton);}
    @Step
    public void clickCompleteSectionCreateAnotherTransactionButton(WebDriver driver){clickElementByLocator(driver,newTransactionPageLocators.completeSectionCreateAnotherTransactionButton);}

    @Step
    public void waitUntilUserListLoaded(WebDriver driver){
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofMillis(5000));
        driverWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(newTransactionPageLocators.userList,0));
    }
    private List<WebElement> getProgressIcons(WebDriver driver){
        return driver.findElements(newTransactionPageLocators.progressIcons);
    }

    private WebElement getProgressIconByIndex(WebDriver driver,int index){
        return getProgressIcons(driver).get(index);
    }

}
