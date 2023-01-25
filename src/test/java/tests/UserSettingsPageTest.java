package tests;

import models.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.SigninPage;
import pages.UserSettingsPage;
import requests.BankAccountRequest;
import requests.UserRequests;

// Tests cases should be belong to only one group from grp_user_type_not_matter, grp_full_user, grp_simple_user

//test casses can be added for user which doesn't have email and phone number
//test cases can be added to see how it will shows errors if api return error
@Test(groups = {"user-settings-page","regression"})
public class UserSettingsPageTest extends BaseTest{
    private final SigninPage signinPage = new SigninPage();
    private final UserSettingsPage userSettingsPage = new UserSettingsPage();
    private final UserRequests userRequests = new UserRequests();
    private final BankAccountRequest bankAccountRequest = new BankAccountRequest();
    private User user;


    @Test(groups = {"grp_user_type_not_matter"})
    void TestUserSettingsPageTitle(){
        String title = driver.getTitle();
        Assert.assertEquals(title,"Cypress Real World App","User Settings Page title isn't correct");
    }

    @Test(groups = {"grp_user_type_not_matter"})
    void TestUserSettingsPageHeader(){
        String header = userSettingsPage.getHeaderText(driver);
        Assert.assertEquals(header,"User Settings","User Settings Page header isn't correct");
    }

    @Test(groups = {"grp_full_user"})
    void TestSaveButtonIsEnabledOnFirstPageLoadOnUserSettingsPage(){
        Assert.assertTrue(userSettingsPage.isEnabledSaveButton(driver),"Save button isn't enabled");
    }

    @Test(groups = {"grp_simple_user"})
    void TestSaveButtonIsDisabledOnFirstPageLoadOnUserSettingsPage(){
        Assert.assertFalse(userSettingsPage.isEnabledSaveButton(driver),"Save button isn't disabled");
    }

    @Test(groups = {"grp_user_type_not_matter"})
    void TestUserSettingsPageErrorsNotDisplayedOnFirstLoad(){
        Assert.assertFalse(userSettingsPage.isFirstNameErrorDisplayed(driver),"Error isn't displayed correctly for first name field");
        Assert.assertFalse(userSettingsPage.isLastNameErrorDisplayed(driver),"Error isn't displayed correctly for last name field");
        Assert.assertFalse(userSettingsPage.isPhoneNumberErrorDisplayed(driver),"Error isn't displayed correctly for phone number field");
        Assert.assertFalse(userSettingsPage.isEmailErrorDisplayed(driver),"Error isn't displayed correctly for email field");
    }

    @Test(groups = {"grp_full_user"})
    void TestSignupPageFieldsAreLoadedCorrectlyForFullUser(){
        Assert.assertEquals(userSettingsPage.getFirstNameValue(driver),user.getFirstName(),"Error isn't displayed correctly for first name field");
        Assert.assertEquals(userSettingsPage.getLastNameValue(driver),user.getLastName(),"Error isn't displayed correctly for last name field");
        Assert.assertEquals(userSettingsPage.getPhoneNumberValue(driver),user.getPhoneNumber(),"Error isn't displayed correctly for phone number field");
        Assert.assertEquals(userSettingsPage.getEmailValue(driver),user.getEmail(),"Error isn't displayed correctly for email field");
    }

    @Test(groups = {"grp_simple_user"})
    void TestSignupPageFieldsAreLoadedCorrectlyForSimpleUser(){
        Assert.assertEquals(userSettingsPage.getFirstNameValue(driver),user.getFirstName(),"First name doesn't match");
        Assert.assertEquals(userSettingsPage.getLastNameValue(driver),user.getLastName(),"Last name doesn't match");
        Assert.assertEquals(userSettingsPage.getPhoneNumberValue(driver),"","Phone number isn't null");
        Assert.assertEquals(userSettingsPage.getEmailValue(driver),"","Email isn't null");
    }

    @Test(groups = {"grp_user_type_not_matter"})
    void TestUserSettingsPageFirstNameIsRequired(){
        userSettingsPage.clearFirstNameField(driver);
        userSettingsPage.clickHeader(driver);

        Assert.assertFalse(userSettingsPage.isEnabledSaveButton(driver),"Save button isn't disabled");
        Assert.assertEquals(userSettingsPage.getFirstNameError(driver),"Enter a first name","Error isn't displayed correctly for first name field");
    }

    @Test(groups = {"grp_user_type_not_matter"})
    void TestUserSettingsPageLastNameIsRequired(){
        userSettingsPage.clearLastNameField(driver);
        userSettingsPage.clickHeader(driver);

        Assert.assertFalse(userSettingsPage.isEnabledSaveButton(driver),"Save button isn't disabled");
        Assert.assertEquals(userSettingsPage.getLastNameError(driver),"Enter a last name","Error isn't displayed correctly for last name field");
    }

    @Test(groups = {"grp_user_type_not_matter"})
    void TestUserSettingsEmailIsRequired(){;
        userSettingsPage.clearEmailField(driver);
        userSettingsPage.clickHeader(driver);

        Assert.assertFalse(userSettingsPage.isEnabledSaveButton(driver),"Save button isn't disabled");
        Assert.assertEquals(userSettingsPage.getEmailError(driver),"Enter an email address","Error isn't displayed correctly for email field");
    }

    @Test(groups = {"grp_user_type_not_matter"})
    void TestUserSettingsPhoneNumberIsRequired(){;
        userSettingsPage.clearPhoneNumberField(driver);
        userSettingsPage.clickHeader(driver);

        Assert.assertFalse(userSettingsPage.isEnabledSaveButton(driver),"Save button isn't disabled");
        Assert.assertEquals(userSettingsPage.getPhoneNumberError(driver),"Enter a phone number","Error isn't displayed correctly for phone number field");
    }

    @Test(groups = {"grp_user_type_not_matter"}, dataProvider = "dp_user_settings_invalid_name")
    void TestEnterInvalidCharactersToFirstNameField(String invalidFirstName){
        userSettingsPage.clearFirstNameField(driver);
        userSettingsPage.fillFirstNameField(driver,invalidFirstName);
        userSettingsPage.clickHeader(driver);
        Assert.assertTrue(userSettingsPage.isFirstNameErrorDisplayed(driver),"Error isn't displayed for first name filed"); // can be deleted
        Assert.assertEquals(userSettingsPage.getFirstNameError(driver),"First name is not valid","Error is displayed wrong for first name field");
    }

    @Test(groups = {"grp_user_type_not_matter"}, dataProvider = "dp_user_settings_invalid_name")
    void TestEnterInvalidCharactersToLastNameField(String invalidLastName){
        userSettingsPage.clearLastNameField(driver);
        userSettingsPage.fillLastNameField(driver,invalidLastName);
        userSettingsPage.clickHeader(driver);
        Assert.assertTrue(userSettingsPage.isLastNameErrorDisplayed(driver),"Error isn't displayed for last name filed");  // can be deleted
        Assert.assertEquals(userSettingsPage.getLastNameError(driver),"Last name is not valid","Error is displayed wrong for last name field");
    }

    @Test(groups = {"grp_user_type_not_matter"}, dataProvider = "dp_user_settings_invalid_email")
    void TestEnterInvalidCharactersToEmailField(String invalidEmail){
        userSettingsPage.clearEmailField(driver);
        userSettingsPage.fillEmailField(driver,invalidEmail);
        userSettingsPage.clickHeader(driver);
        Assert.assertTrue(userSettingsPage.isEmailErrorDisplayed(driver),"Error isn't displayed for email filed");// can be deleted
        Assert.assertEquals(userSettingsPage.getEmailError(driver),"Must contain a valid email address","Error is displayed wrong for email field");
    }

    @Test(groups = {"grp_user_type_not_matter"}, dataProvider = "dp_user_settings_invalid_phone_number")
    void TestEnterInvalidCharactersToPhoneNumberField(String invalidPhoneNumber){
        userSettingsPage.clearPhoneNumberField(driver);
        userSettingsPage.fillPhoneNumberField(driver,invalidPhoneNumber);
        userSettingsPage.clickHeader(driver);
        Assert.assertTrue(userSettingsPage.isPhoneNumberErrorDisplayed(driver),"Error isn't displayed for phone number filed");  // can be deleted
        Assert.assertEquals(userSettingsPage.getPhoneNumberError(driver),"Phone number is not valid","Error is displayed wrong for phone number field");
    }

    @Test(groups = {"grp_user_type_not_matter","smoke"})
    void TestChangeUserSettings() throws InterruptedException {
        User newUser = User.getRandomInstance();

        userSettingsPage.clearFirstNameField(driver);
        userSettingsPage.fillFirstNameField(driver,newUser.getFirstName());

        userSettingsPage.clearLastNameField(driver);
        userSettingsPage.fillLastNameField(driver,newUser.getLastName());

        userSettingsPage.clearEmailField(driver);
        userSettingsPage.fillEmailField(driver,newUser.getEmail());

        userSettingsPage.clearPhoneNumberField(driver);
        userSettingsPage.fillPhoneNumberField(driver,newUser.getPhoneNumber());


        userSettingsPage.clickSaveButton(driver);
        // A message can be added here to show user if it is succesfull or no

        Thread.sleep(300);
        driver.navigate().refresh();

        Assert.assertEquals(userSettingsPage.getFirstNameValue(driver), newUser.getFirstName(),"First name doesn't match");
        Assert.assertEquals(userSettingsPage.getLastNameValue(driver), newUser.getLastName(),"Last name doesn't match");
        Assert.assertEquals(userSettingsPage.getEmailValue(driver), newUser.getEmail(),"Email doesn't match");
        Assert.assertEquals(userSettingsPage.getPhoneNumberValue(driver), newUser.getPhoneNumber(),"Phone number doesn't match");

    }

    @DataProvider(name = "dp_user_settings_invalid_name")
    public Object[][] dpInvalidName() {
        return new Object [][] {
                {"     "},
                {"/n"},
                {"1313 123"},
                {"dad2e3w2e"},
        };
    }

    @DataProvider(name = "dp_user_settings_invalid_email")
    public Object[][] dpInvalidEmail() {
        return new Object [][] {
                {"     "},
                {"/n"},
                {"abc"},
                {"qwerty abcd@ercansencanoglu.com"},
                {"qwerty abcd@ercansencanoglu .com"},
                {"qweerty@"},
        };
    }

    @DataProvider(name = "dp_user_settings_invalid_phone_number")
    public Object[][] dpInvalidPhoneNumber() {
        return new Object [][] {
                {"     "},
                {"/n"},
                {"abc"},
                {"31312 31 3213"},
                {"1313dasd213"},
        };
    }

    @BeforeMethod(onlyForGroups = {"grp_user_type_not_matter","grp_full_user"},alwaysRun = true,dependsOnMethods = {"BaseSetup"}) //alwaysRun = false
    private void UserSettingsPageTestBeforeMethodForAllGroups(){
        user = userRequests.createARandomFullUser();
        beforeMethodContents(driver);
    }

    @BeforeMethod(onlyForGroups = {"grp_simple_user"},dependsOnMethods = {"BaseSetup"},alwaysRun = true)
    private void UserSettingsPageTestBeforeMethodOnGroupSimpleUser(){
        user = userRequests.createARandomSimpleUser();
        beforeMethodContents(driver);
    }

    private void beforeMethodContents(WebDriver driver){
        bankAccountRequest.loginAndCreateRandomBankAccount(user);
        signinPage.login(driver,user, false);
        signinPage.waitUntilBasePageLoaded(driver);
        userSettingsPage.gotoUserSettingsPage(driver);
        userSettingsPage.waitUntilUserSettingsPageLoaded(driver);
    }
}
