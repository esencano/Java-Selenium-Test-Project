package tests;
;
import models.User;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.SigninPage;
import pages.SignupPage;

@Test(groups = {"signup-page","regression"})
public class SignupPageTest extends BaseTest {

    private final SignupPage signupPage = new SignupPage();
    private final SigninPage signinPage = new SigninPage();

    // test cases can be added to verify invalid data example: firstname=1234, lastname=3254234asda username=select * from; but it is better to verify that with api
    // test cases can be added to see how it shows api errors
    @Test
    void TestSignupPageTitle(){
        String title = driver.getTitle();
        Assert.assertEquals(title,"Cypress Real World App","Signup Page title isn't correct");
    }

    @Test
    void TestSignupHeader(){
        String header = signupPage.getSignupPageHeader(driver);
        Assert.assertEquals(header,"Sign Up","Signup Page header isn't correct");
    }


    @Test
    void TestSignupButtonIsDisabledOnFirstPageLoadOnSignupPage(){
        Assert.assertFalse(signupPage.isEnabledSignupButton(driver),"Signup button isn't disabled");
    }

    @Test(dataProvider = "dp_required_fields_and_messages") // silinip static yapilabilir
    void TestSignupPageFieldsAreRequired(By fieldLocater, By errorMessageLocater, String expectedErrorMessage){
        signupPage.clickElementByLocator(driver,fieldLocater);
        signupPage.clickHeader(driver); // just to click somewhere else on the body
        String actualErrorMessage = signupPage.getTextOfElementByLocator(driver,errorMessageLocater);
        Assert.assertEquals(actualErrorMessage,expectedErrorMessage,"Error message isn't displayed correctly");
    }

    @Test
    void TestSignupButtonDisabledIfAllFieldsNotFilled(){
        String errorAssertMessageOfSingupButton = "Signup button isn't disabled ";
        signupPage.fillFirstNameField(driver,"");
        signupPage.clickHeader(driver); // just to click somewhere else on the body
        Assert.assertFalse(signupPage.isEnabledSignupButton(driver),errorAssertMessageOfSingupButton);

        signupPage.fillFirstNameField(driver,"test");
        signupPage.clickHeader(driver);
        Assert.assertFalse(signupPage.isEnabledSignupButton(driver),errorAssertMessageOfSingupButton);

        signupPage.fillLastNameField(driver,"test");
        signupPage.clickHeader(driver);
        Assert.assertFalse(signupPage.isEnabledSignupButton(driver),errorAssertMessageOfSingupButton);

        signupPage.fillUserNameField(driver,"test");
        signupPage.clickHeader(driver);
        Assert.assertFalse(signupPage.isEnabledSignupButton(driver),errorAssertMessageOfSingupButton);

        signupPage.fillPasswordField(driver,"1");
        signupPage.clickHeader(driver);
        Assert.assertFalse(signupPage.isEnabledSignupButton(driver),errorAssertMessageOfSingupButton);

        signupPage.clearPasswordField(driver);
        signupPage.fillPasswordField(driver,"1234");
        signupPage.clickHeader(driver);
        Assert.assertFalse(signupPage.isEnabledSignupButton(driver),errorAssertMessageOfSingupButton);

        signupPage.fillConfirmPasswordField(driver,"12345");
        signupPage.clickHeader(driver);
        Assert.assertFalse(signupPage.isEnabledSignupButton(driver),errorAssertMessageOfSingupButton);

        signupPage.clearConfirmPasswordField(driver);
        signupPage.fillConfirmPasswordField(driver,"1234");
        signupPage.clickHeader(driver);
        Assert.assertTrue(signupPage.isEnabledSignupButton(driver),"Signup button isn't enabled");
    }


    @Test
    void TestSignupButtonIsDisabledIfPasswordsDoesNotMatchAndIfPasswordShort(){
        signupPage.fillFirstNameField(driver,"test");
        signupPage.fillLastNameField(driver,"test");
        signupPage.fillUserNameField(driver,"test");
        signupPage.fillPasswordField(driver,"1");
        signupPage.fillConfirmPasswordField(driver,"1");

        String error = signupPage.getPasswordError(driver);
        Assert.assertEquals(error,"Password must contain at least 4 characters","Error message isn't displayed correctly");
        Assert.assertFalse(signupPage.isEnabledSignupButton(driver),"Signup button isn't disabled");

        signupPage.fillPasswordField(driver,"12434");
        String confirmError = signupPage.getConfirmPasswordError(driver);
        Assert.assertEquals(confirmError,"Password does not match","Error message isn't displayed correctly");
        Assert.assertFalse(signupPage.isEnabledSignupButton(driver),"Signup button isn't disabled");

        signupPage.fillConfirmPasswordField(driver,"12434");
        Assert.assertTrue(signupPage.isEnabledSignupButton(driver),"Signup button isn't enabled");
    }

    @Test
    void TestSignInButtonOnSignupPage(){
        signupPage.clickSigninButton(driver); // firstly need to unfocus from fields
        signupPage.clickSigninButton(driver);
        String currentURL = signupPage.getCurrentURL(driver);
        Assert.assertEquals(currentURL,signupPage.URL_SIGNIN,"Clicking signin button doesn't navigate go correct page");
    }

    @Test(groups = {"smoke"})
    void TestSignup(){
        User user = User.getRandomInstance();
        signupPage.fillUserRegisterForm(driver,user);
        signupPage.clickSignupButton(driver);

        signinPage.waitUntilSigninPageLoaded(driver);
        String currentURL = signupPage.getCurrentURL(driver);
        Assert.assertEquals(currentURL,signupPage.URL_SIGNIN,"It doesn't redirect to signin page after signing up");

        // api or db verification can be added to test case to verify that user registered
        // test data cleaning can be added at the end of this test case
    }

    @DataProvider (name = "dp_required_fields_and_messages")
    public Object[][] dpMethod() {
        return new Object [][] {
                {signupPage.signupPageLocators.firstNameField,signupPage.signupPageLocators.firstNameErrorText,"First Name is required"},
                {signupPage.signupPageLocators.lastNameField,signupPage.signupPageLocators.lastNameErrorText,"Last Name is required"},
                {signupPage.signupPageLocators.userNameField,signupPage.signupPageLocators.userNameErrorText,"Username is required"},
                {signupPage.signupPageLocators.passwordField,signupPage.signupPageLocators.passwordErrorText,"Enter your password"},
                {signupPage.signupPageLocators.confirmPasswordField,signupPage.signupPageLocators.confirmPasswordErrorText,"Confirm your password"},
        };
    }
    @BeforeMethod(dependsOnMethods = {"BaseSetup"},alwaysRun = true)
    private void SignupTestBeforeMethod(){
        signupPage.gotoSignupPage(driver);
    }
}
