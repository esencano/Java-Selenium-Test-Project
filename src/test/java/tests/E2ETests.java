package tests;

import models.BankAccount;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import requests.BankAccountRequest;
import requests.UserRequests;

// add more test cases
@Test(groups = {"e2e","regression"})
public class E2ETests extends BaseTest {
    private final SignupPage signupPage = new SignupPage();
    private final SigninPage signinPage = new SigninPage();
    private final GetStartedDialog getStartedDialog = new GetStartedDialog();
    private final HomePage homePage = new HomePage();
    private final NewTransactionPage newTransactionPage = new NewTransactionPage();
    private final HeaderBar headerBar = new HeaderBar();
    private final NotificationsPage notificationsPage = new NotificationsPage();
    private final TransactionDetailsPage transactionDetailsPage = new TransactionDetailsPage();
    private final Navbar navbar = new Navbar();

    private final UserRequests userRequests = new UserRequests();
    private final BankAccountRequest bankAccountRequest = new BankAccountRequest();

    @Test(groups = {"smoke"},description = "Signup, signin go to new transaction page send a user transaction request and logout then sign in with that user and verify notification then accept request then logout and login back with first user and verify notification")
    void TestVerifyTransactionRequestNotificationReceived() throws InterruptedException {
        User secondUser = userRequests.createARandomFullUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(secondUser);

        User user = User.getRandomInstance();
        signupPage.gotoSignupPage(driver);
        signupPage.fillUserRegisterForm(driver,user);
        signupPage.clickSignupButton(driver);

        signinPage.login(driver,user,false);
        signinPage.waitUntilBasePageLoaded(driver);

        BankAccount bankAccount = BankAccount.getRandomInstance();
        getStartedDialog.clickNextButton(driver);
        getStartedDialog.fillAccountNumber(driver,bankAccount.getAccountNumber());
        getStartedDialog.fillRoutingNumber(driver,bankAccount.getRoutingNumber());
        getStartedDialog.fillBankName(driver,bankAccount.getBankName());
        getStartedDialog.clickSaveButton(driver);
        getStartedDialog.clickDoneButton(driver);

        headerBar.clickNewButton(driver);

        String transactionAmount = "12.34";
        String transactionNote = "test transaction request note";
        newTransactionPage.fillSearchField(driver, secondUser.getUsername());
        newTransactionPage.waitUntilUserListLoaded(driver);
        newTransactionPage.clickContactsByIndex(driver,0);
        newTransactionPage.fillAmountField(driver,transactionAmount);
        newTransactionPage.fillNoteField(driver,transactionNote);
        newTransactionPage.clickRequestButton(driver);

        String completedMessage = newTransactionPage.getMessageOnCompletedStep(driver);
        Assert.assertTrue(completedMessage.contains("Requested"),"Completed Message is wrong");

        navbar.logout(driver);

        signinPage.login(driver,secondUser,false);
        signinPage.waitUntilBasePageLoaded(driver);

        Assert.assertEquals(headerBar.getCountOfNotifications(driver),1,"Notification count is wrong");

        homePage.clickMineTabButton(driver);
        homePage.clickTransaction(driver,0);

        //transactions details verification can be added
        transactionDetailsPage.clickAcceptButton(driver);

        navbar.logout(driver);

        signinPage.login(driver,user,false);
        signinPage.waitUntilSigninPageLoaded(driver);
        Thread.sleep(300);
        //amount verification can be added
        Assert.assertEquals(headerBar.getCountOfNotifications(driver),1,"Notification count is wrong");

        headerBar.clickNotificationsIcon(driver);
        String expectedDescription = String.format("%s %s received payment.",user.getFirstName(),user.getLastName());
        Assert.assertEquals(notificationsPage.getNotificationDescription(driver,0),expectedDescription,"Notification description is wrong");
    }
}
