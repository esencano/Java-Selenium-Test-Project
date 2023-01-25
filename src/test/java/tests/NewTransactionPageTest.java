package tests;

import com.google.gson.Gson;

import models.BankAccount;
import models.User;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.NewTransactionPage;
import pages.SigninPage;
import requests.BankAccountRequest;
import requests.UserRequests;

import java.text.DecimalFormat;
import java.util.List;

@Test(groups = {"new-transaction-page","regression"})
public class NewTransactionPageTest extends BaseTest{

    private final NewTransactionPage newTransactionPage = new NewTransactionPage();
    private final SigninPage signinPage = new SigninPage();
    private final UserRequests userRequests = new UserRequests();
    private final BankAccountRequest bankAccountRequest = new BankAccountRequest();
    private User user;
    private BankAccount bankAccount;
    private final String defaultUser = "Edgar Johns";

    @Test
    void TestNewTransactionPageTitle(){
        String title = driver.getTitle();
        Assert.assertEquals(title,"Cypress Real World App","New Transaction Page title isn't correct");
    }

    @Test
    void TestSelectContactIconIsActiveAndOthersInactiveOnFirstStep(){
        Assert.assertTrue(newTransactionPage.isSelectContactIconActivated(driver),"Select Icon isn't activated on progress section on first step");
        Assert.assertFalse(newTransactionPage.isPaymentIconActivated(driver),"Payment Icon is activated on progress section on first step");
        Assert.assertFalse(newTransactionPage.isIconActivatedForCompletedStep(driver),"Completed Icon is activated on progress section on first step");
    }

    @Test
    void TestSelectContactCompletedIconShowedAndPaymentIconActiveAndCompletedIconIsInactiveOnSecondStep(){
        newTransactionPage.clickContactsByIndex(driver,0);
        Assert.assertTrue(newTransactionPage.isSelectContactCompletedIconShowed(driver),"Select Icon isn't activated on progress section on second step");
        Assert.assertTrue(newTransactionPage.isPaymentIconActivated(driver),"Payment Icon isn't activated on progress section on second step");
        Assert.assertFalse(newTransactionPage.isIconActivatedForCompletedStep(driver),"Completed Icon is activated on progress section on second step");
    }

    @Test
    void TestSelectContactAndPaymentCompletedIconsAreShowedAndCompletedIconIsActiveOnThirdStep(){
        newTransactionPage.clickContactsByIndex(driver,0);
        newTransactionPage.fillAmountField(driver,"1.5");
        newTransactionPage.fillNoteField(driver,"test note");
        newTransactionPage.clickRequestButton(driver);

        Assert.assertTrue(newTransactionPage.isSelectContactCompletedIconShowed(driver),"Select Icon isn't activated on progress section on third step");
        Assert.assertTrue(newTransactionPage.isPaymentCompletedIconShowed(driver),"Payment Icon isn't activated on progress section on third step");
        Assert.assertTrue(newTransactionPage.isCompletedIconShowedForCompletedStep(driver),"Completed Icon isn't activated on progress section on third step");
    }

    @Test(groups = {"smoke"})
    void TestOneUserWithFullDetails(){
        List<User> userList = userRequests.getAllUsers(user);
        int expectedUserIndex = 0;

        Gson gson = new Gson();
        User expectedUserDetails = gson.fromJson(gson.toJson(userList.get(expectedUserIndex)),User.class); // to convert to object
        String expectedUserNameAndLastname = expectedUserDetails.getFirstName()+" "+expectedUserDetails.getLastName();
        String expectedUserUsername ="U: "+ expectedUserDetails.getUsername();
        String expectedUserEmail ="E: "+ expectedUserDetails.getEmail();
        String expectedUserPhoneNumber ="P: "+ expectedUserDetails.getPhoneNumber();

        int actualUserIndex = 0;
        Assert.assertEquals(newTransactionPage.getUserFirstNameAndLastnameByIndex(driver,actualUserIndex),expectedUserNameAndLastname,"User full name doesn't match");
        Assert.assertEquals(newTransactionPage.getUserUsernameByIndex(driver,actualUserIndex),expectedUserUsername, "Username doesn't match");
        Assert.assertEquals(newTransactionPage.getUserEmailByIndex(driver,actualUserIndex),expectedUserEmail,"Email doesn't match");
        Assert.assertEquals(newTransactionPage.getUserPhoneNumberByIndex(driver,actualUserIndex),expectedUserPhoneNumber, "Phone number doesn't match");
        Assert.assertTrue(newTransactionPage.isAvatarExistByIndex(driver,actualUserIndex),"Avatar doesn't exist");
    }

    @Test
    void TestOneUserWithoutDetails(){
        User createdUser = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(createdUser);

        String expectFirstNameAndLastname = createdUser.getFirstName()+" "+createdUser.getLastName();
        String expectedUserUsername ="U: "+createdUser.getUsername();
        String expectedUserEmail ="E:";
        String expectedUserPhoneNumber ="P:";

        driver.navigate().refresh();
        newTransactionPage.waitUntilUserListLoaded(driver);

        String createdUserUsername = createdUser.getUsername();
        String actualFirstNameAndLastname = newTransactionPage.getUserFirstNameAndLastnameByUsername(driver, createdUserUsername);
        String actualUserUserName= newTransactionPage.getUserUsernameByUsername(driver, createdUserUsername);
        String actualUserEmail = newTransactionPage.getUserEmailByUsername(driver, createdUserUsername);
        String actualPhoneNumber= newTransactionPage.getUserPhoneNumberByUsername(driver, createdUserUsername);

        Assert.assertEquals(actualFirstNameAndLastname,expectFirstNameAndLastname,"User full name doesn't match");
        Assert.assertEquals(actualUserUserName,expectedUserUsername, "Username doesn't match");
        Assert.assertEquals(actualUserEmail,expectedUserEmail,"Email doesn't match");
        Assert.assertEquals(actualPhoneNumber,expectedUserPhoneNumber, "Phone number doesn't match");
        Assert.assertTrue(newTransactionPage.isDefaultAvatarExistByUsername(driver, createdUserUsername),"Avatar doesn't exist");
    }

    @Test
    void TestSearchField() throws InterruptedException {
        User createdUser = userRequests.createARandomFullUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(createdUser);

        driver.navigate().refresh();
        newTransactionPage.fillSearchField(driver,createdUser.getUsername());
        newTransactionPage.waitUntilUserListLoaded(driver);

        String expectedUserNameAndLastname = createdUser.getFirstName()+" "+createdUser.getLastName();
        String expectedUserUsername ="U: "+ createdUser.getUsername();
        String expectedUserEmail ="E: "+ createdUser.getEmail();
        String expectedUserPhoneNumber ="P: "+ createdUser.getPhoneNumber();

        int actualUserIndex = 0;
        Thread.sleep(1000);

        Assert.assertEquals(newTransactionPage.getUserFirstNameAndLastnameByIndex(driver,actualUserIndex),expectedUserNameAndLastname,"User full name doesn't match");
        Assert.assertEquals(newTransactionPage.getUserUsernameByIndex(driver,actualUserIndex),expectedUserUsername, "Username doesn't match");
        Assert.assertEquals(newTransactionPage.getUserEmailByIndex(driver,actualUserIndex),expectedUserEmail,"Email doesn't match");
        Assert.assertEquals(newTransactionPage.getUserPhoneNumberByIndex(driver,actualUserIndex),expectedUserPhoneNumber, "Phone number doesn't match");
        Assert.assertTrue(newTransactionPage.isDefaultAvatarExistByIndex(driver, actualUserIndex),"Avatar doesn't exist");
    }

    @Test
    void TesNewTransactionPageRequestAndPayButtonsDisabledIfAllFieldsNotFilled() {
        newTransactionPage.clickContactsByIndex(driver,0);
        String payButtonError = "Pay button is enabled";
        String requestButtonError = "Request button is enabled";

        Assert.assertFalse(newTransactionPage.isPayButtonEnabled(driver),payButtonError);
        Assert.assertFalse(newTransactionPage.isRequestButtonEnabled(driver),requestButtonError);

        newTransactionPage.fillAmountField(driver,"1");
        Assert.assertFalse(newTransactionPage.isPayButtonEnabled(driver),payButtonError);
        Assert.assertFalse(newTransactionPage.isRequestButtonEnabled(driver),requestButtonError);

        newTransactionPage.clearAmountField(driver);
        newTransactionPage.fillNoteField(driver,"test note");
        Assert.assertFalse(newTransactionPage.isPayButtonEnabled(driver),payButtonError);
        Assert.assertFalse(newTransactionPage.isRequestButtonEnabled(driver),requestButtonError);
    }

    @Test(dataProvider = "dp_new_trancation_page_invalid_amount")
    void TestAmountFieldError(String amount, String description){
        newTransactionPage.clickContactsByIndex(driver,0);
        newTransactionPage.fillAmountField(driver,amount);
        newTransactionPage.clickNameOnSecondStep(driver); // just to click somewhere empty

        Assert.assertTrue(newTransactionPage.isAmountFieldErrorDisplayed(driver),description);
        Assert.assertEquals(newTransactionPage.getAmountFieldError(driver),"Please enter a valid amount",description);
    }

    @Test
    void TestAmountFieldWithCommaCharacter(){
        newTransactionPage.clickContactsByIndex(driver,0);
        newTransactionPage.fillAmountField(driver,"1,2");
        newTransactionPage.clickNameOnSecondStep(driver); // just to click somewhere empty

        Assert.assertFalse(newTransactionPage.isAmountFieldErrorDisplayed(driver),"Error isn't displayed for amount field");
        Assert.assertEquals(newTransactionPage.getValueOfAmountField(driver),"$12","Amount value isn't displayed correctly");
    }


    @Test
    void TestNoteFieldEmptyError(){
        newTransactionPage.clickContactsByIndex(driver,0);
        newTransactionPage.fillNoteField(driver,"");
        newTransactionPage.clickNameOnSecondStep(driver); // just to click somewhere empty

        Assert.assertTrue(newTransactionPage.isNoteFieldErrorDisplayed(driver),"Error isn't displayed for not field");
        Assert.assertEquals(newTransactionPage.getNoteFieldError(driver),"Please enter a note","Error isn't displayed correctly for note field");
    }

    @Test
    void TestErrorAreInvisibleIfFieldsAreFilled(){
       newTransactionPage.clickContactsByIndex(driver,0);

       newTransactionPage.fillAmountField(driver,"");
       newTransactionPage.fillNoteField(driver,"");
       newTransactionPage.clickNameOnSecondStep(driver); // just to click somewhere empty

        newTransactionPage.fillAmountField(driver,"10");
        newTransactionPage.fillNoteField(driver,"test note");

        Assert.assertFalse(newTransactionPage.isAmountFieldErrorDisplayed(driver),"Error is displayed for amount field");
        Assert.assertFalse(newTransactionPage.isNoteFieldErrorDisplayed(driver),"Error is displayed for note field");
    }

    @Test
    void TestUserInfoOnSecondStep() throws InterruptedException {
        newTransactionPage.clickContactsByIndex(driver,0);
        Assert.assertEquals(newTransactionPage.getFirstNameAndLastnameOnSecondStep(driver),defaultUser);// default first registered user on app. It is already registered when dev app deployed
        Thread.sleep(1000);
        Assert.assertTrue(newTransactionPage.isAvatarExistOnSecondStep(driver),"User avatar doesn't exist on second step");
    }

    @Test // there is not a back button to go previous step that's why it has been tested with browser back button
    void TestBackButton(){
        newTransactionPage.clickContactsByIndex(driver,0);
        driver.navigate().back();

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL,newTransactionPage.URL_NEW_TRANSACTIONS,"It  hasn't been redirected correct page after clicking back button");
        Assert.assertTrue(newTransactionPage.isSelectContactIconActivated(driver),"Select Icon isn't activated on progress section after clicking back button");
        Assert.assertFalse(newTransactionPage.isPaymentIconActivated(driver),"Payment Icon is activated on progress section after clicking back button");
    }

    @Test
    void TestMakePaymentRequest(){
        newTransactionPage.clickContactsByIndex(driver,0);
        String amount = "10";
        String note = "test payment request";
        newTransactionPage.fillAmountField(driver,amount);
        newTransactionPage.fillNoteField(driver, note);
        newTransactionPage.clickRequestButton(driver);

        newTransactionPage.isAvatarExistOnCompletedStep(driver);
        Assert.assertEquals(newTransactionPage.getFirstNameAndLastnameOnCompletedStep(driver),defaultUser,"User full name doesn't match");

        DecimalFormat df = new DecimalFormat("0.00");
        String expectedMessage = String.format("Requested $%s for %s",df.format(Double.parseDouble(amount)),note);
        Assert.assertEquals(newTransactionPage.getMessageOnCompletedStep(driver),expectedMessage, "Completed message doesn't match");
    }

    @Test
    void TestMakePayment(){
        newTransactionPage.clickContactsByIndex(driver,0);
        String amount = "10";
        String note = "test payment";
        newTransactionPage.fillAmountField(driver,amount);
        newTransactionPage.fillNoteField(driver, note);
        newTransactionPage.clickPayButton(driver);

        newTransactionPage.isAvatarExistOnCompletedStep(driver);
        Assert.assertEquals(newTransactionPage.getFirstNameAndLastnameOnCompletedStep(driver),defaultUser,"User full name doesn't match");

        DecimalFormat df = new DecimalFormat("0.00");
        String expectedMessage = String.format("Paid $%s for %s",df.format(Double.parseDouble(amount)),note);
        Assert.assertEquals(newTransactionPage.getMessageOnCompletedStep(driver),expectedMessage, "Completed message doesn't match");
    }

    @Test
    void TestCreateAnotherTransactionButton(){
        newTransactionPage.clickContactsByIndex(driver,0);
        String amount = "10";
        String note = "test payment";
        newTransactionPage.fillAmountField(driver,amount);
        newTransactionPage.fillNoteField(driver, note);
        newTransactionPage.clickPayButton(driver);

        newTransactionPage.clickCompleteSectionCreateAnotherTransactionButton(driver);
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL,newTransactionPage.URL_NEW_TRANSACTIONS,"It  hasn't been redirected correct page after clicking create another transaction button");
        Assert.assertTrue(newTransactionPage.isSelectContactIconActivated(driver),"Select Icon isn't activated on progress section after clicking back button");
        Assert.assertFalse(newTransactionPage.isPaymentIconActivated(driver),"Payment Icon is activated on progress section after clicking back button");
    }

    @Test
    void TestReturnToTransactionButton() {
        newTransactionPage.clickContactsByIndex(driver, 0);
        String amount = "10";
        String note = "test payment";
        newTransactionPage.fillAmountField(driver, amount);
        newTransactionPage.fillNoteField(driver, note);
        newTransactionPage.clickPayButton(driver);

        newTransactionPage.clickCompleteSectionReturnToTransactionsButton(driver);
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, newTransactionPage.BASE_URL+"/","It  hasn't been redirected correct page after clicking return to transactions button");
    }


    @DataProvider(name = "dp_new_trancation_page_invalid_amount")
    public Object[][] dpMethodInvalidAmount() {
        return new Object [][] {
                {"","Test with null value"},
                {"0","Test with zero"},
                {"-1","Test with negative value"},
                {"  ","Test with space charachter"},
        };
    }

    @BeforeMethod(dependsOnMethods = {"BaseSetup"},alwaysRun = true)
    private void NewTransactionPageTestBeforeMethod(){
        user = userRequests.createARandomFullUser();
        bankAccount = bankAccountRequest.loginAndCreateRandomBankAccount(user);
        signinPage.login(driver,user, false);
        signinPage.waitUntilBasePageLoaded(driver);
        newTransactionPage.gotoNewTransactionPage(driver);
        newTransactionPage.waitUntilNewTransactionPageLoaded(driver);
    }
}
