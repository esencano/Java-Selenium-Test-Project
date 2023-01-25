package tests;


import models.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;


import org.testng.annotations.Test;
import pages.Navbar;
import pages.SigninPage;
import requests.BankAccountRequest;
import requests.UserRequests;

@Test(groups = {"logout","regression"})
public class LogoutTest extends BaseTest{

    private final SigninPage signinPage = new SigninPage();
    private final Navbar navbar = new Navbar();
    private final UserRequests userRequests = new UserRequests();
    private final BankAccountRequest bankAccountRequest = new BankAccountRequest();
    private User user;

    @Test
    void TestEnterMainPageAfterLogoutAfterLoginWithoutRememberMe(){
        signinPage.login(driver,user,false);
        navbar.logout(driver);
        Assert.assertEquals(driver.getCurrentUrl(),navbar.URL_SIGNIN,"After clicking logout button it isn't redirect to sigin page");

        driver.get(navbar.BASE_URL);
        Assert.assertEquals(driver.getCurrentUrl(),navbar.URL_SIGNIN,"It can navigate to main page even after logout");
    }

    @Test
    void TestEnterMainPageAfterLogoutAfterLoginWithRememberMe(){
        signinPage.login(driver,user,true);
        navbar.logout(driver);
        Assert.assertEquals(driver.getCurrentUrl(),navbar.URL_SIGNIN,"After clicking logout button it isn't redirect to sigin page");

        driver.get(navbar.BASE_URL);
        Assert.assertEquals(driver.getCurrentUrl(),navbar.URL_SIGNIN,"It can navigate to main page even after logout");
    }

    @BeforeClass(alwaysRun = true)
    private void LogoutTestBeforeMethod(){
        user = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(user);
    }
}
