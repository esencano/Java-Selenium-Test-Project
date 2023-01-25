package locators;

import org.openqa.selenium.By;

public class UserSettingPageLocators {

    public By header = By.xpath("//main/div[2]/div/div/div/h2");

    public By firstNameField = By.id("user-settings-firstName-input");
    public By lastNameField = By.id("user-settings-lastName-input");
    public By emailField = By.id("user-settings-email-input");
    public By phoneNumberField = By.id("user-settings-phoneNumber-input");
    public By saveButton = By.xpath("//button[@data-test='user-settings-submit']");

    public By firstNameErrorText = By.id("user-settings-firstName-input-helper-text");
    public By lastNameErrorText = By.id("user-settings-lastName-input-helper-text");
    public By emailErrorText = By.id("user-settings-email-input-helper-text");
    public By phoneNumberErrorText = By.id("user-settings-phoneNumber-input-helper-text");
}
