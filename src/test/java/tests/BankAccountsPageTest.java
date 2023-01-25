package tests;

import models.BankAccount;
import models.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.BankAccountsPage;
import pages.SigninPage;
import requests.BankAccountRequest;
import requests.UserRequests;

import java.util.List;

@Test(groups = {"bank-account-page","regression"})
public class BankAccountsPageTest extends BaseTest{
    private final BankAccountsPage bankAccountsPage = new BankAccountsPage();
    private final SigninPage signinPage = new SigninPage();
    private final UserRequests userRequests = new UserRequests();
    private final BankAccountRequest bankAccountRequest = new BankAccountRequest();
    private User user;
    private BankAccount bankAccount;

    @Test()
    void TestBankAccountsPageTitle(){
        String title = driver.getTitle();
        Assert.assertEquals(title,"Cypress Real World App", "Bank Account Page Title isn't Correct");
    }

    @Test
    void TesBankAccountsPageHeader(){
        String header = bankAccountsPage.getHeaderText(driver);
        Assert.assertEquals(header,"Bank Accounts","Bank Account Page Headr isn't Correct");
    }

    @Test(groups = "smoke")
    void TestBankAccountsListedCorrectly(){
        BankAccount bankAccount2 = bankAccountRequest.loginAndCreateRandomBankAccount(user);
        bankAccountsPage.getAllBankAccountsName(driver);
        bankAccountRequest.loginAndDeleteBankAccount(user, bankAccount.getBankAccountID());
        driver.navigate().refresh();

        List<String> bankAccounts = bankAccountsPage.getAllBankAccountsName(driver);

        Assert.assertEquals(bankAccounts.size(), 2,"Bank Account size isn't correct");
        Assert.assertEquals(bankAccounts.get(0),bankAccount.getBankName()+" (Deleted)","Deleted Bank Account Name isn't shown correctly");
        Assert.assertEquals(bankAccounts.get(1),bankAccount2.getBankName() ,"Bank Account Name isn't shown correctly");
        Assert.assertFalse(bankAccountsPage.isDeleteButtonActiveByName(driver,bankAccount.getBankName()),"Delete Button is Active for Deleted Bank Account");
        Assert.assertTrue(bankAccountsPage.isDeleteButtonActiveByName(driver,bankAccount2.getBankName()), "Delete Button isn't Active for Bank account");

    }

    @Test
    void TestBankAccountsPageCreateButtonAndSecondPageHeaderAndURL(){
        bankAccountsPage.clickCreateButton(driver);
        String header = bankAccountsPage.getHeaderText(driver);
        Assert.assertEquals(header,"Create Bank Account");

        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, bankAccountsPage.URL_BANK_ACCOUNTS+"/new","Page isn't Redirected after clicking create button");
    }

    @Test
    void TestBankAccountPageSaveButtonDisabledIfAllFieldsNotFilled() {
        bankAccountsPage.clickCreateButton(driver);
        BankAccount bankAccount = BankAccount.getRandomInstance();
        String assertErrorMessage = "Save button is enabled";

        bankAccountsPage.fillRoutingNumber(driver,bankAccount.getRoutingNumber());
        bankAccountsPage.fillAccountNumber(driver,bankAccount.getAccountNumber());
        bankAccountsPage.clickHeader(driver); // just to click somewhere else on the body
        Assert.assertFalse(bankAccountsPage.isEnabledSaveButton(driver),assertErrorMessage);


        bankAccountsPage.clearAllFields(driver);
        bankAccountsPage.fillBankName(driver,bankAccount.getBankName());
        bankAccountsPage.fillAccountNumber(driver,bankAccount.getAccountNumber());
        bankAccountsPage.clickHeader(driver); // just to click somewhere else on the body
        Assert.assertFalse(bankAccountsPage.isEnabledSaveButton(driver),assertErrorMessage);

        bankAccountsPage.clearAllFields(driver);
        bankAccountsPage.fillBankName(driver,bankAccount.getBankName());
        bankAccountsPage.fillRoutingNumber(driver,bankAccount.getRoutingNumber());
        bankAccountsPage.clickHeader(driver); // just to click somewhere else on the body
        Assert.assertFalse(bankAccountsPage.isEnabledSaveButton(driver),assertErrorMessage);

        bankAccountsPage.fillAccountNumber(driver,bankAccount.getAccountNumber());
        bankAccountsPage.clickHeader(driver); // just to click somewhere else on the body
        Assert.assertTrue(bankAccountsPage.isEnabledSaveButton(driver),"Save button isn't enabled");
    }

    @Test(dataProvider = "dp_bank_account_page_invalid_bank_name") // here used another approach then other pages for verifying errors
    void TestBankAccountPageCreateAccountBankNameErrors(String bankName, String errorMessage){
        bankAccountsPage.clickCreateButton(driver);
        bankAccountsPage.fillBankName(driver,bankName);
        bankAccountsPage.clickHeader(driver);

        Assert.assertTrue(bankAccountsPage.isBankNameErrorDisplayed(driver),"Error isn't displayed for bank name field");

        String actualErrorMessage = bankAccountsPage.getBankNameError(driver);
        Assert.assertEquals(actualErrorMessage,errorMessage, "Error message isn't displayed correctly for bank name field");
    }

    @Test(dataProvider = "dp_bank_account_page_invalid_routing_number")
    void TestBankAccountPageCreateAccountRoutingNumberErrors(String routingNumber, String errorMessage){
        bankAccountsPage.clickCreateButton(driver);
        bankAccountsPage.fillRoutingNumber(driver,routingNumber);
        bankAccountsPage.clickHeader(driver);

        Assert.assertTrue(bankAccountsPage.isRoutingNumberErrorDisplayed(driver),"Error isn't displayed for routing number field");

        String actualErrorMessage = bankAccountsPage.getRoutingNumberError(driver);
        Assert.assertEquals(actualErrorMessage,errorMessage,"Error message isn't displayed correctly for routing number field");
    }

    @Test(dataProvider = "dp_bank_account_page_invalid_account_number")
    void TestBankAccountPageCreateAccountAccountNumberErrors(String accountNumber, String errorMessage){
        bankAccountsPage.clickCreateButton(driver);
        bankAccountsPage.fillAccountNumber(driver,accountNumber);
        bankAccountsPage.clickHeader(driver);

        Assert.assertTrue(bankAccountsPage.isAccountNumberErrorDisplayed(driver),"Error isn't displayed for account number field");

        String actualErrorMessage = bankAccountsPage.getAccountNumberError(driver);
        Assert.assertEquals(actualErrorMessage,errorMessage,"Error message isn't displayed correctly for account number field");
    }

    @Test
    void TestErrorAreInvisibleIfFieldsAreFilled(){
        bankAccountsPage.clickCreateButton(driver);

        BankAccount bankAccountInstance = BankAccount.getRandomInstance();
        bankAccountsPage.fillBankName(driver,"");
        bankAccountsPage.fillRoutingNumber(driver,"");
        bankAccountsPage.fillAccountNumber(driver,"");
        bankAccountsPage.clickHeader(driver);

        bankAccountsPage.fillBankName(driver,bankAccountInstance.getBankName());
        bankAccountsPage.fillRoutingNumber(driver,bankAccountInstance.getRoutingNumber());
        bankAccountsPage.fillAccountNumber(driver,bankAccountInstance.getAccountNumber());

        Assert.assertFalse(bankAccountsPage.isBankNameErrorDisplayed(driver),"Error is displayed for bank name field");
        Assert.assertFalse(bankAccountsPage.isRoutingNumberErrorDisplayed(driver),"Error is displayed for routing number field");
        Assert.assertFalse(bankAccountsPage.isAccountNumberErrorDisplayed(driver),"Error is displayed for account number field");
    }

    @Test(groups = "smoke")
    void TestCreateBankAccount(){
        bankAccountsPage.clickCreateButton(driver);

        BankAccount bankAccountInstance = BankAccount.getRandomInstance();
        bankAccountsPage.fillBankName(driver,bankAccountInstance.getBankName());
        bankAccountsPage.fillRoutingNumber(driver,bankAccountInstance.getRoutingNumber());
        bankAccountsPage.fillAccountNumber(driver,bankAccountInstance.getAccountNumber());
        bankAccountsPage.clickSaveButton(driver);

        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, bankAccountsPage.URL_BANK_ACCOUNTS,"Page isn't Redirected after clicking create button");

        bankAccountsPage.waitUntilBankAccountPageLoaded(driver);
        String actualBankName = bankAccountsPage.getBankAccountNameByIndex(driver,1);
        Assert.assertEquals(actualBankName,bankAccountInstance.getBankName(),"Bank account name isn't displayed correctly");

        Assert.assertTrue(bankAccountsPage.isDeleteButtonActiveByIndex(driver,1),"Delete button isn't activated");
    }

    @Test(groups = "smoke")
    void TestDeleteBankAccount() throws InterruptedException {
        bankAccountsPage.clickCreateButton(driver);

        BankAccount bankAccountInstance = BankAccount.getRandomInstance();
        bankAccountsPage.fillBankName(driver,bankAccountInstance.getBankName());
        bankAccountsPage.fillRoutingNumber(driver,bankAccountInstance.getRoutingNumber());
        bankAccountsPage.fillAccountNumber(driver,bankAccountInstance.getAccountNumber());
        bankAccountsPage.clickSaveButton(driver);

        bankAccountsPage.waitUntilBankAccountPageLoaded(driver);
        Assert.assertTrue(bankAccountsPage.isDeleteButtonActiveByName(driver,bankAccountInstance.getBankName()),"Delete button isn't activated");
        bankAccountsPage.clickDeleteButtonByBankName(driver,bankAccountInstance.getBankName());
        Assert.assertFalse(bankAccountsPage.isDeleteButtonActiveByName(driver,bankAccountInstance.getBankName()),"Delete button is activated");

        Assert.assertTrue(bankAccountsPage.isBankDeleted(driver,1),"Bank isn't deleted after clicking delete button");
    }

    @DataProvider(name = "dp_bank_account_page_invalid_bank_name")
    public Object[][] dpMethodInvalidBankName() {
        return new Object [][] {
                {"","Enter a bank name"},
                {"ABCD","Must contain at least 5 characters"},
                {"     ","Must contain at least 5 characters"},//5 space charachters
                {"\t\n","Enter a bank name"},
                {"13$#$@#%","Enter a valid bank name"}
        };
    }

    @DataProvider(name = "dp_bank_account_page_invalid_routing_number")
    public Object[][] dpMethodInvalidRoutingNumber() {
        return new Object [][] {
                {"","Enter a valid bank routing number"},
                {"daDadDdadd","Must contain a valid routing number"},
                {"12345678","Must contain a valid routing number"},//8 number charachters
                {"        ","Must contain a valid routing number"},//9 spave charachters
                {"13$#@$@$@#%","Must contain a valid routing number"},
                {"123214 2442","Must contain a valid routing number"}
        };
    }

    @DataProvider(name = "dp_bank_account_page_invalid_account_number")
    public Object[][] dpMethodInvalidAccountNumber() {
        return new Object [][] {
                {"","Enter a valid bank account number"},
                {"daDadDdadd","Must contain a valid account number"},
                {"12345678","Must contain at least 9 digits"},//8 number charachters
                {"         ","Must contain a valid account number"},//9 space charachters
                {"13$#@$@$@#%","Must contain a valid account number"},
                {"123214 2442","Must contain a valid account number"}
        };
    }

    @BeforeMethod(dependsOnMethods = {"BaseSetup"},alwaysRun = true)
    private void BankAccountsPageTestBeforeMethod() {
        user = userRequests.createARandomFullUser();
        bankAccount = bankAccountRequest.loginAndCreateRandomBankAccount(user);
        signinPage.login(driver,user, false);
        signinPage.waitUntilBasePageLoaded(driver);
        bankAccountsPage.gotoBankAccountsPage(driver);
        bankAccountsPage.waitUntilBankAccountPageLoaded(driver);
    }
}
