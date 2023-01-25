package tests;


import models.User;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.Navbar;
import pages.SigninPage;
import requests.BankAccountRequest;
import requests.UserRequests;

@Test(groups = {"nav-bar","regression"})
public class NavbarTest extends BaseTest{

    private SigninPage signinPage = new SigninPage();
    private UserRequests userRequests = new UserRequests();
    private BankAccountRequest bankAccountRequest = new BankAccountRequest();

    private Navbar navbar = new Navbar();
    private User user;

    void TestNavbarVisibleAfterFirstLogin() {
        Assert.assertTrue(navbar.isNavbarVisible(driver),"Navbar isn't visible on first login");
    }

    @Test(groups = {"smoke"})
    void TestNavbarOpenClose() {
        navbar.clickNavbarToggleButton(driver);
        navbar.waitUntilPresenceOfElementLocated(driver,navbar.navbarLocators.navbarToggleButton, 2);

        Assert.assertFalse(navbar.isNavbarVisible(driver),"Navbar is visible after clicking toggle button");
        navbar.clickNavbarToggleButton(driver);
        Assert.assertTrue(navbar.isNavbarVisible(driver),"Navbar isn't visible after clicking toggle button");
    }

    @Test
    void TestNavbarFullNameAndUsername(){
        String expectedFullName = user.getFirstName() + " " + user.getLastName().charAt(0);
        String expectedUsername = "@" + user.getUsername();
        String actualFullName = navbar.getFullNameText(driver);
        String actualUsername = navbar.getUsernameText(driver);

        Assert.assertEquals(actualFullName,expectedFullName,"User full name isn't displayed correctly on navbar");
        Assert.assertEquals(actualUsername,expectedUsername,"Username isn't displayed correctly on navbar");
    }

    @Test
    void TestAccountBalance(){
        String expectedBalance = "$0.00";
        String actualBalance = navbar.getAccountBalanceText(driver);
        Assert.assertEquals(actualBalance,expectedBalance,"Account balance isn't displayed correctly on navbar");
    }

    @Test
    void TestNavbarHomeButton(){
        driver.get(navbar.URL_BANK_ACCOUNTS);
        navbar.clickHomeButton(driver);
        Assert.assertEquals(driver.getCurrentUrl(),navbar.BASE_URL+"/","Clicking home button doesn't navigate to \'Main\' page");
    }
    @Test
    void TestNavbarMyAccountButton(){
        navbar.clickMyAccountButton(driver);
        Assert.assertEquals(driver.getCurrentUrl(),navbar.URL_USER_SETTING,"Clicking My Account button doesn't navigate to \'User Settings\' page");
    }
    @Test
    void TestNavbarBankAccountsButton(){
        navbar.clickBankAccountstButton(driver);
        Assert.assertEquals(driver.getCurrentUrl(),navbar.URL_BANK_ACCOUNTS,"Clicking Bank Accounts button doesn't navigate to \'Bank Account\' page");
    }
    @Test
    void TestNavbarNotificationsButton(){
        navbar.clickNotificationsButton(driver);
        Assert.assertEquals(driver.getCurrentUrl(),navbar.URL_NOTIFICATIONS,"Clicking Notifications button doesn't navigate to \'Notifications\' page");
    }

    @Test
    void TestNavbarLogoutButton(){
        navbar.clickLogoutButton(driver);
        Assert.assertTrue(signinPage.waitUntilSigninPageLoaded(driver),"Clicking Logout button doesn't navigate to \'Signin\' page");
    }

    @BeforeMethod(dependsOnMethods = {"BaseSetup"},alwaysRun = true)
    private void NavbarTestBeforeMethod(){
        user = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(user);
        signinPage.login(driver,user, false);
        signinPage.waitUntilBasePageLoaded(driver);
    }
}
