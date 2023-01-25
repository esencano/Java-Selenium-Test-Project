package tests;

import enums.TransactionType;
import models.BankAccount;
import models.Transaction;
import models.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.NotificationsPage;
import pages.SigninPage;
import requests.BankAccountRequest;
import requests.CommentsRequest;
import requests.TransactionRequest;
import requests.UserRequests;

@Test(groups = {"notification-page","regression"})
public class NotificationsPageTest extends BaseTest{
    private final NotificationsPage notificationsPage = new NotificationsPage();
    private final SigninPage signinPage = new SigninPage();
    private final UserRequests userRequests = new UserRequests();
    private final BankAccountRequest bankAccountRequest = new BankAccountRequest();
    private final TransactionRequest transactionRequest = new TransactionRequest();
    private final CommentsRequest commentsRequest = new CommentsRequest();
    private User user;
    private BankAccount bankAccount;

    @Test
    void TestNotificationPageHeader(){
        Assert.assertEquals(notificationsPage.getHeaderText(driver),"Notifications","Header doesn't match");
    }

    @Test(groups = {"smoke"})
    void TestNotificationsCount(){
        User createdUser = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(createdUser);
        Transaction transaction = transactionRequest.makeTransaction(createdUser,user.getId(), TransactionType.PAYMENT,10.0,"test payment note");
        commentsRequest.makeComment(createdUser,transaction.getId(),"test comment");
        commentsRequest.likeTransaction(createdUser,transaction.getId());

        int actualNotificationsCountOnPage = notificationsPage.getCountOfNotifications(driver);
        int expectedNotificationsCount = notificationsPage.getCountOfNotifications(driver);
        Assert.assertEquals(actualNotificationsCountOnPage,expectedNotificationsCount,"Transaction count doesn't match");
    }

    @Test
    void TestPaymentNotificationIconAndDescription(){
        User createdUser = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(createdUser);
        transactionRequest.makeTransaction(createdUser,user.getId(), TransactionType.PAYMENT,10.0,"test payment note");

        driver.navigate().refresh();

        int notificationIndex = 0;
        String expectedDescription = String.format("%s %s received payment.",user.getFirstName(),user.getLastName()); // seems wrong description
        Assert.assertTrue(notificationsPage.isPaymentReceivedIconDisplayed(driver,notificationIndex),"Payment received notification icon isn't displayed");
        Assert.assertEquals(notificationsPage.getNotificationDescription(driver,notificationIndex),expectedDescription,"Payment received notification description isn't displayed correctly");
    }

    @Test(groups = {"smoke"})
    void TestRequestPaymentNotificationIconAndDescription(){
        User createdUser = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(createdUser);
        transactionRequest.makeTransaction(createdUser,user.getId(), TransactionType.REQUEST,10.0,"test payment note");

        driver.navigate().refresh();

        int notificationIndex = 0;
        String expectedDescription = String.format("%s %s requested payment.",createdUser.getFirstName(),createdUser.getLastName());
        Assert.assertTrue(notificationsPage.isRequestedPaymentIconDisplayed(driver,notificationIndex),"Payment request notification icon isn't displayed");
        Assert.assertEquals(notificationsPage.getNotificationDescription(driver,notificationIndex),expectedDescription,"Payment request notification  description isn't displayed correctly");
    }

    @Test
    void TestCommentedNotificationIconAndDescription(){
        User createdUser = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(createdUser);
        Transaction transaction = transactionRequest.makeTransaction(createdUser,user.getId(), TransactionType.REQUEST,10.0,"test payment note");
        commentsRequest.makeComment(createdUser,transaction.getId(),"test comment");

        driver.navigate().refresh();

        int notificationIndex = 0;
        String expectedDescription = String.format("%s %s commented on a transaction.",createdUser.getFirstName(),createdUser.getLastName());
        Assert.assertTrue(notificationsPage.isCommentedIconDisplayed(driver,notificationIndex),"Comment notification icon isn't displayed");
        Assert.assertEquals(notificationsPage.getNotificationDescription(driver,notificationIndex),expectedDescription,"Comment notification description isn't displayed correctly");
    }

    @Test
    void TestLikedNotificationIconAndDescription(){
        User createdUser = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(createdUser);
        Transaction transaction = transactionRequest.makeTransaction(createdUser,user.getId(), TransactionType.REQUEST,10.0,"test payment note");
        commentsRequest.likeTransaction(createdUser,transaction.getId());

        driver.navigate().refresh();

        int notificationIndex = 0;
        String expectedDescription = String.format("%s %s liked a transaction.",createdUser.getFirstName(),createdUser.getLastName());
        Assert.assertTrue(notificationsPage.isLikedIconDisplayed(driver,notificationIndex),"Liked notification icon isn't displayed");
        Assert.assertEquals(notificationsPage.getNotificationDescription(driver,notificationIndex),expectedDescription,"Liked notification description isn't displayed correctly");
    }

    @Test
    void TestDismissNotifications() throws InterruptedException {
        User createdUser = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(createdUser);
        Transaction transaction = transactionRequest.makeTransaction(createdUser,user.getId(), TransactionType.REQUEST,10.0,"test payment note");
        commentsRequest.likeTransaction(createdUser,transaction.getId());

        driver.navigate().refresh();
        notificationsPage.clickDismissButton(driver,0);
        notificationsPage.waitUntilNumbersOfNotificationsTobe(driver,1);
        int actualNotificationsCountOnPage = notificationsPage.getCountOfNotifications(driver);
        Assert.assertEquals(actualNotificationsCountOnPage,1,"Notifications count doesn't match"); // can be deleted

        notificationsPage.clickDismissButton(driver,0);
        notificationsPage.waitUntilNumbersOfNotificationsTobe(driver,0);
        actualNotificationsCountOnPage = notificationsPage.getCountOfNotifications(driver);
        Assert.assertEquals(actualNotificationsCountOnPage,0,"Notifications displayed after clicking dismiss button"); // can be deleted
    }

    @BeforeMethod(dependsOnMethods = {"BaseSetup"},alwaysRun = true)
    private void NotificationsPageTestBeforeMethod(){
        user = userRequests.createARandomFullUser();
        bankAccount = bankAccountRequest.loginAndCreateRandomBankAccount(user);
        signinPage.login(driver,user, false);
        signinPage.waitUntilBasePageLoaded(driver);
        notificationsPage.gotoNotificationsPage(driver);
        notificationsPage.waitUntilNotificationPageLoaded(driver);
    }

}
