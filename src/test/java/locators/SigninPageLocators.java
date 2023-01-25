package locators;

import org.openqa.selenium.By;

public class SigninPageLocators {
    public By header = By.xpath("//h1[text()='Sign in']");
    public By username = By.id("username");
    public By password = By.id("password");
    public By usernameErrorText = By.id("username-helper-text");
    public By passwordErrorText = By.id("password-helper-text");
    public By rememberMeCheckbox = By.name("remember");
    public By signInButton = By.xpath("//button[@data-test='signin-submit']");
    public By signupButton = By.linkText("Don't have an account? Sign Up");

    public By wrongUserNAmePasswordError = By.xpath("//div[@data-test='signin-error']/div[@class='MuiAlert-message']");

}
