package tests;

import models.BankAccount;
import models.User;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.GetStartedDialog;
import pages.NewTransactionPage;
import pages.SigninPage;
import requests.BankAccountRequest;
import requests.UserRequests;

import java.lang.reflect.Method;


@Test(groups = {"get-started-dialog","regression"})
public class GetStartedDialogTest extends BaseTest {

    private SigninPage signinPage = new SigninPage();
    private UserRequests userRequests = new UserRequests();
    private BankAccountRequest bankAccountRequest = new BankAccountRequest();
    private GetStartedDialog getStartedDialog = new GetStartedDialog();
    @Test
    void TestGetStartedDialogShownIfUserDoesNotHaveBankAccount(){
       Assert.assertTrue(getStartedDialog.isDialogExist(driver),"Get Started Dialog doesn't exist in first login");
    }

    @Test(groups = {"skipBeforeMethod"})
    void TestGetStartedDialogNotShownIfUserDoesHasBankAccount(){
        User user = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(user);
        signinPage.login(driver,user, false);

        Assert.assertFalse(getStartedDialog.isDialogExist(driver),"Get Started Dialog exists even if user has bank account");
    }

    @Test
    void TestGetStartedDialogShownEvenAfterRefreshAndOnOtherPages(){
        driver.navigate().refresh();
        Assert.assertTrue(getStartedDialog.isDialogExist(driver));

        NewTransactionPage newTransactionPage = new NewTransactionPage();
        newTransactionPage.gotoNewTransactionPage(driver);
        newTransactionPage.waitUntilNewTransactionPageLoaded(driver);

        Assert.assertTrue(getStartedDialog.isDialogExist(driver),"Get Started Dialog doesn't exist for user who doesn't have bank account");
    }

    @Test
    void TestContentOfGetStartedDialog() {
        String actualFirstPageTitle = getStartedDialog.getDialogFirstPageTitle(driver);
        String actualParagraph = getStartedDialog.getFirstPageDialogText(driver);
        String expectedFirstPageTitle = "Get Started with Real World App";
        String expectedFirstPageParagraph = "Real World App requires a Bank Account to perform transactions.\n\n"+
        "Click Next to begin setup of your Bank Account.";
        Assert.assertEquals(actualFirstPageTitle,expectedFirstPageTitle,"Get Started Dialog Title is wrong");
        Assert.assertEquals(actualParagraph,expectedFirstPageParagraph, "Get Started Dialog description text is wrong");

        getStartedDialog.clickNextButton(driver);
        String actualSecondPageTitle = getStartedDialog.getDialogSecondPageTitle(driver);
        String expectedSecondPageTitle = "Create Bank Account";
        Assert.assertEquals(actualSecondPageTitle,expectedSecondPageTitle,"Get Started Dialog Second Title is wrong");
    }


    @Test(groups = {"smoke"})
    void TestGetStartedDialogCreateAccount() throws InterruptedException {
        BankAccount bankAccount = BankAccount.getRandomInstance();

        getStartedDialog.clickNextButton(driver);
        getStartedDialog.fillBankName(driver,bankAccount.getBankName());
        getStartedDialog.fillRoutingNumber(driver,bankAccount.getRoutingNumber());
        getStartedDialog.fillAccountNumber(driver,bankAccount.getAccountNumber());
        getStartedDialog.clickSaveButton(driver);

        String actualThirdPageTitle = getStartedDialog.getDialogThirdPageTitle(driver);
        String actualThirdPageParagraph = getStartedDialog.getThirdPageDialogText(driver);
        String expectedThirdPageTitle = "Finished";
        String expectedThirdPageParagraph = "You're all set!\n\n" +
                "We're excited to have you aboard the Real World App!";
        Assert.assertEquals(actualThirdPageTitle,expectedThirdPageTitle,"Get Started Dialog Third Title is wrong");
        Assert.assertEquals(actualThirdPageParagraph,expectedThirdPageParagraph,"Get Started Dialog description text is wrong on third page");

        getStartedDialog.clickDoneButton(driver);
        Thread.sleep(500);
        Assert.assertFalse(getStartedDialog.isDialogExist(driver),"Dialog is still shown after clicking done button");

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL,getStartedDialog.BASE_URL+"/","It isn't redirected to base page after creating bank account");
    }

    @Test
    void TestSaveButtonIsDisabledOnSecondPageLoad(){
        getStartedDialog.clickNextButton(driver);
        Assert.assertFalse(getStartedDialog.isEnabledSaveButton(driver),"Save button isn't disabled on second page load");
    }

    @Test
    void TestSaveButtonDisabledIfAllFieldsNotFilled() {
        BankAccount bankAccount = BankAccount.getRandomInstance();
        getStartedDialog.clickNextButton(driver);
        String assertErrorMessage = "Save button isn't disabled";

        getStartedDialog.fillRoutingNumber(driver,bankAccount.getRoutingNumber());
        getStartedDialog.fillAccountNumber(driver,bankAccount.getAccountNumber());
        getStartedDialog.clickDialogSecondPageTitle(driver); // just to click somewhere else on the body
        Assert.assertFalse(getStartedDialog.isEnabledSaveButton(driver),assertErrorMessage);

        getStartedDialog.clearDialogAllFields(driver);
        getStartedDialog.fillBankName(driver,bankAccount.getBankName());
        getStartedDialog.fillAccountNumber(driver,bankAccount.getAccountNumber());
        getStartedDialog.clickDialogSecondPageTitle(driver); // just to click somewhere else on the body
        Assert.assertFalse(getStartedDialog.isEnabledSaveButton(driver),assertErrorMessage);

        getStartedDialog.clearDialogAllFields(driver);
        getStartedDialog.fillBankName(driver,bankAccount.getBankName());
        getStartedDialog.fillRoutingNumber(driver,bankAccount.getRoutingNumber());
        getStartedDialog.clickDialogSecondPageTitle(driver); // just to click somewhere else on the body
        Assert.assertFalse(getStartedDialog.isEnabledSaveButton(driver),assertErrorMessage);

        getStartedDialog.fillAccountNumber(driver,bankAccount.getAccountNumber());
        getStartedDialog.clickDialogSecondPageTitle(driver); // just to click somewhere else on the body
        Assert.assertTrue(getStartedDialog.isEnabledSaveButton(driver),"Save button isn't enabled");
    }

    @Test(dataProvider = "dp_dialog_required_fields_and_messages") // it might be better to convert it to a static test case instead of using dataprovider
    void TestGetStartedDialogCreateAccountFieldsAreRequired(By fieldLocater, By errorMessageLocater, String expectedErrorMessage){
        getStartedDialog.clickNextButton(driver);
        getStartedDialog.clickElementByLocator(driver,fieldLocater);
        getStartedDialog.clickDialogSecondPageTitle(driver); // just to click somewhere else on the body
        String actualErrorMessage = getStartedDialog.getTextOfElementByLocator(driver,errorMessageLocater);
        Assert.assertEquals(actualErrorMessage,expectedErrorMessage,"Error message isn't displayed correctly");
    }

    @Test // parts inside this test case can be seperated to different test cases
    void TestGetStartedDialogCreateAccountFieldsWithInvalidValues(){
        // these test cases can be separated
        String assertErrorMessage = "Error message isn't displayed correctly";
        getStartedDialog.clickNextButton(driver);

        // test for bank name shorter than 5 charachters
        getStartedDialog.fillBankName(driver,"abcd");
        getStartedDialog.clickDialogSecondPageTitle(driver);
        String bankNameError = getStartedDialog.getDialogBankNameError(driver);
        Assert.assertEquals(bankNameError,"Must contain at least 5 characters",assertErrorMessage);

        /***disabled because it is a bug now
         * // test for bank name only with space charahters
        getStartedDialog.clearDialogBankNam(driver);
        getStartedDialog.fillBankName(driver,"        ");// or more space charachter
        getStartedDialog.clickDialogSecondPageTitle(driver);
        bankNameError = getStartedDialog.getDialogBankNameError(driver);
        Assert.assertEquals(bankNameError,"Must contain at least 5 characters");
        */

        // test for routing number shorter than 9 charachters
        getStartedDialog.fillRoutingNumber(driver, "12345678");
        getStartedDialog.clickDialogSecondPageTitle(driver);
        String routingNumberError = getStartedDialog.getDialogRoutingNumberError(driver);
        Assert.assertEquals(routingNumberError,"Must contain a valid routing number",assertErrorMessage);

        // test for routing number longer than 9 charachters
        getStartedDialog.clearDialogRoutingNumber(driver);
        getStartedDialog.fillRoutingNumber(driver, "1234567890");
        getStartedDialog.clickDialogSecondPageTitle(driver);
        routingNumberError = getStartedDialog.getDialogRoutingNumberError(driver);
        Assert.assertEquals(routingNumberError,"Must contain a valid routing number",assertErrorMessage);

        /***disabled because it is a bug now
        // test for routing number only with space charahters
         getStartedDialog.clearDialogRoutingNumber(driver);
         getStartedDialog.fillBankName(driver,"         ");//9 or more space charachter
         getStartedDialog.clickDialogSecondPageTitle(driver);
        routingNumberError = getStartedDialog.getDialogBankNameError(driver);
         Assert.assertEquals(routingNumberError,"Must contain a valid routing number");

        // test for routing number only with alphatic charachters
        getStartedDialog.clearDialogRoutingNumber(driver);
        getStartedDialog.fillBankName(driver,"abcdefghi");//9 alphabetic charachters
        getStartedDialog.clickDialogSecondPageTitle(driver);
        routingNumberError = getStartedDialog.getDialogBankNameError(driver);
        Assert.assertEquals(routingNumberError,"Must contain a valid routing number");
         */



        // test for account number shorter than 9 charachters
        getStartedDialog.fillAccountNumber(driver, "12345678");
        getStartedDialog.clickDialogSecondPageTitle(driver);
        String accountNumberError = getStartedDialog.getDialogAccountNumberError(driver);
        Assert.assertEquals(accountNumberError,"Must contain at least 9 digits",assertErrorMessage);


        /***
        // test for account number only with space charahters
        getStartedDialog.clearDialogRoutingNumber(driver);
        getStartedDialog.fillAccountNumber(driver,"         ");//9 or more space charachter
        getStartedDialog.clickDialogSecondPageTitle(driver);
        accountNumberError = getStartedDialog.getDialogBankNameError(driver);
        Assert.assertEquals(accountNumberError,"Must contain at least 9 digits");

        // test for account number only with alphatic charachters
        getStartedDialog.clearDialogAccountNumber(driver);
        getStartedDialog.fillAccountNumber(driver,"abcdefghi");//9 alphabetic charachters
        getStartedDialog.clickDialogSecondPageTitle(driver);
        accountNumberError = getStartedDialog.getDialogBankNameError(driver);
        Assert.assertEquals(accountNumberError,"Must contain at least 9 digits");
         */

        /*
         * Other test cases might be added, example: bank name with speacial chrachters,routing number with digits and space, account number digits and alphabatic charachters max chrachter size for fields
         *
         */

    }

    // skipping below test case because there is not an option to close dialog now but it should be added to logout from system
    @Test(enabled = false)
    void TestCloseGetStartedDialog(){
        // getStartedDialog.clickCloseButton(driver);// there is not a button like that
    }

    @DataProvider(name = "dp_dialog_required_fields_and_messages")
    public Object[][] dpMethod() {
        return new Object [][] {
                {getStartedDialog.getStartedDialogLocators.bankNameField,getStartedDialog.getStartedDialogLocators.dialogBankNameErrorText,"Enter a bank name"},
                {getStartedDialog.getStartedDialogLocators.routingNumberField,getStartedDialog.getStartedDialogLocators.dialogRoutingNumberErrorText,"Enter a valid bank routing number"},
                {getStartedDialog.getStartedDialogLocators.accountNumberField,getStartedDialog.getStartedDialogLocators.dialogAccountNumberErrorText,"Enter a valid bank account number"},
        };
    }

    @BeforeMethod(dependsOnMethods = {"BaseSetup"},alwaysRun = true)
    private void GetStartedDialogTestBeforeMethod(Method method){
        if (!method.getName().equals("TestGetStartedDialogNotShownIfUserDoesHasBankAccount")) {
            User user = userRequests.createARandomSimpleUser();
            signinPage.login(driver,user, false);
            signinPage.waitUntilBasePageLoaded(driver);
        }

    }
}
