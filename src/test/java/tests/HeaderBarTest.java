package tests;

import models.Transaction;
import enums.TransactionType;
import models.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HeaderBar;
import pages.SigninPage;
import requests.BankAccountRequest;
import requests.CommentsRequest;
import requests.TransactionRequest;
import requests.UserRequests;

@Test(groups = {"header-bar","regression"})
public class HeaderBarTest extends BaseTest {
    private final HeaderBar headerBar = new HeaderBar();
    private final UserRequests userRequests = new UserRequests();
    private final BankAccountRequest bankAccountRequest = new BankAccountRequest();
    private final SigninPage signinPage =new SigninPage();
    private User user;

    @Test
    void TestClickLogoRedirectToMainPage(){
        driver.get(headerBar.URL_USER_SETTING);
        headerBar.clickLogo(driver);
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL,headerBar.BASE_URL+"/","Clicking logo to redirect main page doesn't work");
    }

    @Test
    void TestNewButtonRedirectToNewTransactainPage(){
        headerBar.clickNewButton(driver);
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL,headerBar.URL_NEW_TRANSACTIONS,"Clicking New Button doesn't redirect to new transaction page");
    }

    @Test
    void TestClickNotificationsIconRedirectToNotificationsPage(){
        headerBar.clickNotificationsIcon(driver);
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL,headerBar.URL_NOTIFICATIONS,"Clicking Notifications Button doesn't redirect to notifications page");
    }

    @Test
    void TestNotificationsCountIsNotDisplayedIfThereIsNotError(){
        Assert.assertFalse(headerBar.isCountOfNotificationDisplayed(driver),"Notification count isn't displayed");
    }

    @Test(groups = {"smoke"})
    void TestCountOfNotificationsDisplayedCorrectly(){
        TransactionRequest transactionRequest = new TransactionRequest();
        CommentsRequest commentsRequest = new CommentsRequest();

        User user2 = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(user2);
        Transaction transaction = transactionRequest.makeTransaction(user,user2.getId(), TransactionType.REQUEST,1.23,"test");
        transactionRequest.rejectTransactionRequest(user2,transaction.getId());
        commentsRequest.likeTransaction(user2,transaction.getId());
        commentsRequest.makeComment(user2,transaction.getId(),"test comment");

        driver.navigate().refresh();
        int notificationCount = headerBar.getCountOfNotifications(driver);
        Assert.assertEquals(notificationCount,3,"Notifications count isn't displayed correctly");
    }

    @BeforeMethod(dependsOnMethods = {"BaseSetup"},alwaysRun = true)
    private void HeaderBarBeforeMethod(){
        user = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(user);
        signinPage.login(driver,user, false);
        signinPage.waitUntilBasePageLoaded(driver);
    }
}
