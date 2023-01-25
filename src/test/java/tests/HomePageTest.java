package tests;

import com.google.gson.Gson;
import models.BankAccount;
import models.TransactionDetailed;
import models.User;
import org.checkerframework.checker.units.qual.Temperature;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BankAccountsPage;
import pages.HomePage;
import pages.SigninPage;
import requests.BankAccountRequest;
import requests.CommentsRequest;
import requests.TransactionRequest;
import requests.UserRequests;

import java.util.List;
import java.util.Random;

@Test(groups = {"home-page","regression"})
public class HomePageTest extends BaseTest{

    private final SigninPage signinPage = new SigninPage();
    private final UserRequests userRequests = new UserRequests();
    private final BankAccountRequest bankAccountRequest = new BankAccountRequest();
    private final TransactionRequest transactionRequest = new TransactionRequest();
    private final CommentsRequest commentsRequest = new CommentsRequest();
    private User user;

    private final HomePage homePage = new HomePage();

    @Test
    void TestHomePageTitle(){
        String title = driver.getTitle();
        Assert.assertEquals(title,"Cypress Real World App","Home Page title isn't correct");
    }

    @Test
    void TestHomePageEveryoneButton(){
        homePage.gotoHomePagePersonalSection(driver);
        homePage.clickEveryoneTabButton(driver);
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, homePage.URL_HOME_PAGE_PUBLIC_SECTION+"/","Clicking Everyone Button doesn't redirect to everyone page");
    }

    @Test
    void TestHomePageFriendsButton(){
        homePage.clickFriendsTabButton(driver);
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, homePage.URL_HOME_PAGE_CONTACTS_SECTION,"Clicking friends Button doesn't redirect to friends page");
    }

    @Test
    void TestHomePageMineButton(){
        homePage.clickMineTabButton(driver);
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, homePage.URL_HOME_PAGE_PERSONAL_SECTION,"Clicking Mine Button doesn't redirect to personal page");
    }

    @Test
    void TestCountOfTransactions(){
        Assert.assertEquals(homePage.getCountOfDisplayedTransactions(driver),10,"Displayed transaction count are wrong");//10 transactions should be displayed
    }

    @Test(groups = {"smoke"})
    void TestFirstTenSenderAndReceiverAvatars(){
        List<TransactionDetailed> transactionDetailedList = transactionRequest.getAllTransactions(user);
        for (int index = 0; index < transactionDetailedList.size(); index++) {
            Gson gson = new Gson();
            TransactionDetailed transactionDetailed = gson.fromJson(gson.toJson(transactionDetailedList.get(index)),TransactionDetailed.class);

            Assert.assertTrue(homePage.isSenderAvatarDisplayed(driver,index),"Sender Avatar isn't displayed");
            Assert.assertTrue(homePage.isReceiverAvatarDisplayed(driver,index),"Receiver Avatar isn't displayed");

            if(transactionDetailed.getSenderAvatar() != ""){
                Assert.assertTrue(homePage.isSenderAvatarDisplayed(driver,index),"Sender Avatar isn't displayed");
                Assert.assertEquals(transactionDetailed.getSenderAvatar(),homePage.getSenderAvatarPath(driver,index),"Sender Avatar isn't displayed correctly");
            }else{
                Assert.assertTrue(homePage.isSenderDefaultAvatarDisplayed(driver,index),"Sender Default Avatar isn't displayed");
            }

            if(transactionDetailed.getReceiverAvatar() != ""){
                Assert.assertTrue(homePage.isReceiverAvatarDisplayed(driver,index),"Receiver Avatar isn't displayed");
                Assert.assertEquals(transactionDetailed.getReceiverAvatar(),homePage.getReceiverAvatarPath(driver,index),"Receiver Avatar isn't displayed correctly");
            }else{
                Assert.assertTrue(homePage.isReceiverDefaultAvatarDisplayed(driver,index),"Receiver Default Avatar isn't displayed");
            }
        }
    }

    @Test(groups = {"smoke"})
    void TestFirstTenTransactionTypeSenderAndReceiverNames(){
        List<TransactionDetailed> transactionDetailedList = transactionRequest.getAllTransactions(user);
        for (int index = 0; index < transactionDetailedList.size(); index++) {
            Gson gson = new Gson();
            TransactionDetailed transactionDetailed = gson.fromJson(gson.toJson(transactionDetailedList.get(index)), TransactionDetailed.class);


            Assert.assertEquals(homePage.getSenderFirstNameAndLastname(driver, index), transactionDetailed.getSenderName(),"Sender name isn't displayed correctly");
            Assert.assertEquals(homePage.getReceiverFirstNameAndLastname(driver, index), transactionDetailed.getReceiverName(),"Receiver name isn't displayed correctly");
            Assert.assertEquals(homePage.getTransactionType(driver, index), getTypeString(transactionDetailed.getRequestStatus()),"Transaction type isn't displayed correctly");
        }
    }

    @Test(groups = {"smoke"})
    void TestFirstTenTransactionDescription(){
        List<TransactionDetailed> transactionDetailedList = transactionRequest.getAllTransactions(user);
        for (int index = 0; index < transactionDetailedList.size(); index++) {
            Gson gson = new Gson();
            TransactionDetailed transactionDetailed = gson.fromJson(gson.toJson(transactionDetailedList.get(index)), TransactionDetailed.class);

            Assert.assertEquals(homePage.getTransactionDescription(driver, index), transactionDetailed.getDescription(),"Transaction description isn't displayed correctly");
        }
    }

    @Test
    void testTransactionLikeAndCommentCount(){
        User testUser = userRequests.createARandomFullUser();
        List<TransactionDetailed> transactionDetailedList = transactionRequest.getAllTransactions(user);
        int index = 0;
        Gson gson = new Gson();
        TransactionDetailed transactionDetailed = gson.fromJson(gson.toJson(transactionDetailedList.get(index)),TransactionDetailed.class);
        commentsRequest.makeComment(testUser,transactionDetailed.getId(),"test note 1");
        commentsRequest.likeTransaction(testUser,transactionDetailed.getId());
        commentsRequest.likeTransaction(user,transactionDetailed.getId());

        driver.navigate().refresh();

        Assert.assertEquals(Integer.parseInt(homePage.getTransactionLikeCount(driver,index)),transactionDetailed.getLikes().size()+2,"Transaction like count isn't displayed correctly");
        Assert.assertEquals(Integer.parseInt(homePage.getTransactionCommentCount(driver,index)),transactionDetailed.getComments().size()+1,"Transaction comment count isn't displayed correctly");
    }

    @Test(groups = {"smoke"})
    void TestFirstTenTransactionAmount(){
        List<TransactionDetailed> transactionDetailedList = transactionRequest.getAllTransactions(user);

        for (int index = 0; index < transactionDetailedList.size(); index++) {
            Gson gson = new Gson();
            TransactionDetailed transactionDetailed = gson.fromJson(gson.toJson(transactionDetailedList.get(index)),TransactionDetailed.class);

            String actualAmount = homePage.getTransactionAmount(driver,index);

            String transactionAmount = transactionDetailed.getAmount();
            String expectedAmount = String.format("%c$%s.%s",(isTransactionAmountPositive(transactionDetailed.getRequestStatus()) ? '-':'+'),transactionAmount.substring(0,transactionAmount.length()-2),transactionAmount.substring(transactionAmount.length()-2));
            Assert.assertEquals(actualAmount,expectedAmount,"Transaction amount isn't displayed correctly");
            if (isTransactionAmountPositive(transactionDetailed.getRequestStatus())){
                Assert.assertTrue(homePage.isPositiveStyleClassAppliedForAmount(driver,index),"Positive style isn't applied for transaction amount");
            }else{
                Assert.assertTrue(homePage.isNegativeStyleClassAppliedForAmount(driver,index),"Negative style isn't applied for transaction amount");
            }
        }
    }

    @Test
    void TestClickTransaction(){
        List<TransactionDetailed> transactionDetailedList = transactionRequest.getAllTransactions(user);
        int count = homePage.getCountOfDisplayedTransactions(driver);
        Random random = new Random();
        int randomIndex = random.nextInt(count);

        // below 2 lines repeats many times, a function can be written for that
        Gson gson = new Gson();
        TransactionDetailed transactionDetailed = gson.fromJson(gson.toJson(transactionDetailedList.get(randomIndex)),TransactionDetailed.class);

        homePage.clickTransaction(driver,randomIndex);
        String currentURL = driver.getCurrentUrl();
        String expectedURL = homePage.URL_TRANSACTION_DETAILS + transactionDetailed.getId();
        Assert.assertEquals(currentURL, expectedURL,"Clicking Transaction isn't redirected to clicked transaction details page");
    }

    @Test
    void TestScrollTransactions() throws InterruptedException {
        homePage.scrollTransactions(driver);
        Thread.sleep(1000);
        Assert.assertTrue(homePage.getCountOfDisplayedTransactions(driver) >= 10,"Scrolling transaction doesn't displaying new transactions");
    }


    private String getTypeString(String requestStatus){
        switch (requestStatus){
            case "":
                return "paid";
            case "pending":
                return "requested";
            case "accepted":
                return "charged";
        }
        return "";
    }

    private boolean isTransactionAmountPositive(String requestStatus){
        return getTypeString(requestStatus) == "paid" ? true : false;
    }

    @BeforeMethod(dependsOnMethods = {"BaseSetup"},alwaysRun = true)
    private void HomePageTestBeforeMethod(){
        user = userRequests.createARandomFullUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(user);
        signinPage.login(driver,user, false);
        homePage.waitHomePageLoaded(driver);
    }
}
