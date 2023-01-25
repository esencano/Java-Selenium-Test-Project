package locators;

import org.openqa.selenium.By;

public class SignupPageLocators {
    public By header = By.xpath("//h1[@data-test=\"signup-title\"]");
    public By signupButton = By.xpath("//form/button[@data-test=\"signup-submit\"]");

    public By firstNameField = By.id("firstName");
    public By firstNameErrorText = By.id("firstName-helper-text");

    public By lastNameField = By.id("lastName");
    public By lastNameErrorText = By.id("lastName-helper-text");

    public By userNameField = By.id("username");
    public By userNameErrorText = By.id("username-helper-text");

    public By passwordField = By.id("password");
    public By passwordErrorText = By.id("password-helper-text");

    public By confirmPasswordField = By.id("confirmPassword");
    public By confirmPasswordErrorText = By.id("confirmPassword-helper-text");

    public By signinButton = By.linkText("Have an account? Sign In");

}
