package tests;

import managers.DriverFactory;
import models.User;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.SigninPage;
import requests.BankAccountRequest;
import requests.UserRequests;

import java.net.MalformedURLException;


@Test(groups = {"signin-page","regression"})
public class SigninPageTest extends BaseTest{
    private SigninPage signinPage = new SigninPage();
    private UserRequests userRequests = new UserRequests();
    private BankAccountRequest bankAccountRequest = new BankAccountRequest();
    @Test
    void TestSigninPageTitle(){
        String title = driver.getTitle();
        Assert.assertEquals(title,"Cypress Real World App","Signin Page title isn't correct");
    }

    @Test
    void TestSigninHeader(){
        String header = signinPage.getSigninPageHeader(driver);
        Assert.assertEquals(header,"Sign In","Signin Page header isn't correct");
    }

    @Test
    void TestSigninButtonIsDisabledOnFirstPageLoad(){
        Assert.assertFalse(signinPage.isEnabledSigninButton(driver),"Signin button isn't disabled on first page load");
    }

    @Test
    void TestRememberMeUncheckedOnFirstPageLoad(){
        Assert.assertFalse(signinPage.isRememberMeChecked(driver),"Remember me checkbox is unchecked on first page load");
    }

    @Test
    void TestUsernameRequired(){
        signinPage.clickHeader(driver);
        String actualErrorMessage = signinPage.getUserNameError(driver);
        Assert.assertEquals(actualErrorMessage,"Username is required","Username error isn't displayed correctly");
    }

    @Test
    void TestSigninButtonDisabledIfAllFieldsNotFilled(){
        signinPage.clickHeader(driver);
        Assert.assertFalse(signinPage.isEnabledSigninButton(driver),"Signin button isn't enabled");

        signinPage.fillUserNameField(driver,"testusername");
        signinPage.clickHeader(driver); // for unfocus from username field
        Assert.assertFalse(signinPage.isEnabledSigninButton(driver),"Signin button isn't disabled");
    }

    @Test
    void TestSigninButtonIsDisabledIfPasswordShort(){
        signinPage.fillUserNameField(driver,"testusername");
        signinPage.fillPasswordField(driver,"1");
        signinPage.clickHeader(driver); // for unfocus from username field
        String actualErrorMessage = signinPage.getPasswordError(driver);
        Assert.assertEquals(actualErrorMessage,"Password must contain at least 4 characters","Error message isn't displayed correctly for password field");
        Assert.assertFalse(signinPage.isEnabledSigninButton(driver),"Signin button isn't disabled");

        signinPage.clearPasswordField(driver);
        signinPage.fillPasswordField(driver,"1234");
        signinPage.clickHeader(driver); // for unfocus from username field
        Assert.assertFalse(signinPage.isPasswordErrorExist(driver),"Error field isn't displayed on password field");
        Assert.assertTrue(signinPage.isEnabledSigninButton(driver),"Signin button isn't disabled");
    }

    @Test
    void TestSignUpButtonOnSigninPage(){
        signinPage.clickHeader(driver);
        signinPage.clickSignupButton(driver);
        String currentURL = signinPage.getCurrentURL(driver);
        Assert.assertEquals(currentURL,signinPage.URL_SIGNUP,"It isn't navigate to correct page after clicking signup button");
    }

    @Test(groups = {"smoke"})
    void TestLoginWithInvalidCredentials(){
        signinPage.fillUserNameField(driver,"testabc");
        signinPage.fillPasswordField(driver,"testabc");
        signinPage.clickSigninButton(driver);

        String currentURL = signinPage.getCurrentURL(driver);
        Assert.assertEquals(currentURL,signinPage.URL_SIGNIN,"It has been navigated to wrong page after clicking signin button");

        String actualError = signinPage.getWrongUserNamePasswordError(driver);
        Assert.assertEquals(actualError,"Username or password is invalid","Error message isn't displayed correctly");

        Assert.assertEquals("",signinPage.getUsernameFieldValue(driver),"Username field isn't clear after trying to login wrong credentials");
        Assert.assertEquals("",signinPage.getPasswordFieldValue(driver),"Password field isn't clear after trying to login wrong credentials");
    }

    @Test(description = "Login with remember me checked, get session storages and close the browser and then create same kind of browser, set session storages back ,navigate to signin page then verify it navigates to main page")
    void TestLoginWithRememberMe(ITestContext context) throws MalformedURLException {
        User user = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(user);

        signinPage.fillUserNameField(driver,user.getUsername());
        signinPage.fillPasswordField(driver,user.getPassword());
        signinPage.selectRememberMe(driver);
        signinPage.clickSigninButton(driver);

        String browserName = context.getAttribute("browser_name").toString();

        final String authStateKey = "authState";
        String localStorageAuthState = signinPage.getLocalStorage(driver,authStateKey,true);
        String sessionStorageAuthState = signinPage.getSessionStorage(driver,authStateKey,true);

        driver.close();
        driver = DriverFactory.getDriverInstance(browserName); /// might change

        signinPage.goToBasePage(driver);

        signinPage.setLocalStorage(driver,authStateKey,localStorageAuthState,true);
        signinPage.setSessionStorage(driver,authStateKey,sessionStorageAuthState,true);
        signinPage.goToBasePage(driver);
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL,signinPage.BASE_URL+"/","It doesn't redirect to main page after creating new browser");
    }

    @Test(description = "Login with remember me unchecked, get session storages and close the browser and then create same kind of browser, set session storages back ,navigate to signin page then verify it doesn't navigate to main page")
    void TestLoginWithoutRememberMe(ITestContext context) throws MalformedURLException {
        User user = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(user);

        signinPage.fillUserNameField(driver,user.getUsername());
        signinPage.fillPasswordField(driver,user.getPassword());
        signinPage.clickSigninButton(driver);

        String browserName = context.getAttribute("browser_name").toString();

        final String authStateKey = "authState";
        String localStorageAuthState = signinPage.getLocalStorage(driver,authStateKey,true);
        String sessionStorageAuthState = signinPage.getSessionStorage(driver,authStateKey,true);

        driver.close();
        driver = DriverFactory.getDriverInstance(context.getAttribute("browser_name").toString()); /// might change

        signinPage.goToBasePage(driver);
        signinPage.setLocalStorage(driver,authStateKey,localStorageAuthState,true);
        signinPage.setSessionStorage(driver,authStateKey,sessionStorageAuthState,true);
        signinPage.goToBasePage(driver);
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL,signinPage.URL_SIGNIN,"It doesn't navigates to signin page after creating new browser");
    }

    @Test(groups = {"smoke"})
    void TestGoToSigninAndSignUpPagesAfterLogin(){
        User user = userRequests.createARandomSimpleUser();
        bankAccountRequest.loginAndCreateRandomBankAccount(user);
        signinPage.login(driver,user,false);

        signinPage.gotoSigninPage(driver);
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL,signinPage.BASE_URL+"/","It can be reached signin page even if after login");

        driver.get(signinPage.URL_SIGNUP);
        Assert.assertEquals(currentURL,signinPage.BASE_URL+"/","It can be reached signup page even if after login");
    }

    @BeforeMethod(dependsOnMethods = {"BaseSetup"},alwaysRun = true)
    private void SigninTestBeforeMethod(){
        signinPage.gotoSigninPage(driver);
    }

}
