package pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import locators.BankAccountsPageLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class BankAccountsPage extends BasePage{
    public final BankAccountsPageLocators bankAccountsPageLocators = new BankAccountsPageLocators();
    @Step
    public void gotoBankAccountsPage(WebDriver driver){
        driver.get(URL_BANK_ACCOUNTS);
    }
    @Step
    @Attachment
    public boolean waitUntilBankAccountPageLoaded(WebDriver driver){
        return waitUntilURLContains(driver,PATH_BANK_ACCOUNTS);
    }
    @Step
    @Attachment
    public String getHeaderText(WebDriver driver){return getTextOfElementByLocator(driver,bankAccountsPageLocators.header);}
    @Step
    @Attachment
    public int getCountOfBankAccounts(WebDriver driver){return  getAllBankAccounts(driver).size();}

    @Step
    @Attachment
    public String getBankAccountNameByIndex(WebDriver driver, int index){
        return getTextOfElement(getAllBankAccounts(driver).get(index).findElement(bankAccountsPageLocators.bankAccountBankName));
    }
    @Step
    @Attachment
    public List<String> getAllBankAccountsName(WebDriver driver){
        return getAllBankAccounts(driver).stream().map(f -> f.findElement(bankAccountsPageLocators.bankAccountBankName).getText()).collect(Collectors.toList());
    }

    @Step
    @Attachment
    public boolean isBankDeleted(WebDriver driver, int index){return getBankAccountNameByIndex(driver,index).endsWith("(Deleted)");}

    public int indexOfBankName(WebDriver driver, String bankName){
        List<String> bankAccountsNames = getAllBankAccountsName(driver);
        for (int i = 0; i < bankAccountsNames.size(); i++) {
            if(bankAccountsNames.get(i).equals(bankName) || bankAccountsNames.get(i).equals(bankName+" (Deleted)")){
                return i;
            }
        }
        return -1;
    }

    @Step
    @Attachment
    public boolean isDeleteButtonActiveByIndex(WebDriver driver, int index){
        return !getAllBankAccounts(driver).get(index).findElements(bankAccountsPageLocators.bankAccountDeleteButton).isEmpty();
    }
    @Step
    @Attachment
    public boolean isDeleteButtonActiveByName(WebDriver driver, String bankName){
        int index = indexOfBankName(driver,bankName);
        return isDeleteButtonActiveByIndex(driver,index);
    }
    @Step
    public void clickDeleteButtonByIndex(WebDriver driver, int index){
        getAllBankAccounts(driver).get(index).findElement(bankAccountsPageLocators.bankAccountDeleteButton).click();
    }
    @Step
    public void clickDeleteButtonByBankName(WebDriver driver, String bankName){
        int index = indexOfBankName(driver,bankName);
        clickDeleteButtonByIndex(driver,index);
    }

    @Step
    public void clickCreateButton(WebDriver driver){
        clickElementByLocator(driver,bankAccountsPageLocators.createButton);
    }
    @Step
    public void fillBankName(WebDriver driver, String value){
        fillElementByLocator(driver,bankAccountsPageLocators.bankNameField,value);}
    @Step
    public void fillRoutingNumber(WebDriver driver, String value){
        fillElementByLocator(driver,bankAccountsPageLocators.routingNumberField,value);}
    @Step
    public void fillAccountNumber(WebDriver driver, String value){
        fillElementByLocator(driver,bankAccountsPageLocators.accountNumberField,value);}

    @Step
    public void clearBankName(WebDriver driver){clearElement(driver, bankAccountsPageLocators.bankNameField);}
    @Step
    public void clearRoutingNumber(WebDriver driver){clearElement(driver, bankAccountsPageLocators.routingNumberField);}
    @Step
    public void clearAccountNumber(WebDriver driver){clearElement(driver, bankAccountsPageLocators.accountNumberField);}
    @Step
    public void clearAllFields(WebDriver driver){
        clearBankName(driver);
        clearRoutingNumber(driver);
        clearAccountNumber(driver);
    }

    @Step
    public void clickSaveButton(WebDriver driver){
        clickElementByLocator(driver,bankAccountsPageLocators.saveButton);}
    @Step
    @Attachment
    public boolean isEnabledSaveButton(WebDriver driver){return isElementEnabledByLocator(driver,bankAccountsPageLocators.saveButton);}
    @Step
    @Attachment
    public String getBankNameError(WebDriver driver){return getTextOfElementByLocator(driver,bankAccountsPageLocators.bankNameErrorText);}
    @Step
    @Attachment
    public String getRoutingNumberError(WebDriver driver){return getTextOfElementByLocator(driver,bankAccountsPageLocators.routingNumberErrorText);}
    @Step
    public String getAccountNumberError(WebDriver driver){return getTextOfElementByLocator(driver,bankAccountsPageLocators.accountNumberErrorText);}

    @Step
    public void clickHeader(WebDriver driver){
        clickElementByLocator(driver,bankAccountsPageLocators.header);}
    @Step
    @Attachment
    public boolean isBankNameErrorDisplayed(WebDriver driver){return isElementDisplayedByLocator(driver,bankAccountsPageLocators.bankNameErrorText);}
    @Step
    @Attachment
    public boolean isRoutingNumberErrorDisplayed(WebDriver driver){return isElementDisplayedByLocator(driver,bankAccountsPageLocators.routingNumberErrorText);}
    @Step
    @Attachment
    public boolean isAccountNumberErrorDisplayed(WebDriver driver){return isElementDisplayedByLocator(driver,bankAccountsPageLocators.accountNumberErrorText);}

    @Step
    public void waitUntilUserListLoaded(WebDriver driver){
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofMillis(5000));
        driverWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(bankAccountsPageLocators.bankAccountsList,0));
    }
    private List<WebElement> getAllBankAccounts(WebDriver driver){
        return driver.findElements(bankAccountsPageLocators.bankAccountsList);
    }

}
