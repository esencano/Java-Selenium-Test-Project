package locators;

import org.openqa.selenium.By;

public class GetStartedDialogLocators {
    public By accountNumberField = By.id("bankaccount-accountNumber-input");
    public By bankNameField = By.id("bankaccount-bankName-input");
    public By dialog = By.xpath("/html/body/div[2]/div[3]/div[@role='dialog']");
    public By dialogAccountNumberErrorText = By.id("bankaccount-accountNumber-input-helper-text");
    public By dialogBankNameErrorText = By.id("bankaccount-bankName-input-helper-text");
    public By dialogFirstPageText = By.xpath("/html/body/div[2]/div[3]/div[@role='dialog']//p");
    public By dialogFirstPageTitle = By.xpath("/html/body/div[2]/div[3]/div[@role='dialog']/div/h2");
    public By dialogRoutingNumberErrorText = By.id("bankaccount-routingNumber-input-helper-text");
    public By dialogSecondPageSaveButton = By.xpath("/html/body/div[2]/div[3]/div[@role='dialog']//button[@data-test='bankaccount-submit']");
    public By dialogThirdPageDoneButton = By.xpath("/html/body/div[2]/div[3]/div[@role='dialog']//button[@data-test='user-onboarding-next']/span[text()='Done']");
    public By dialogThirdPageText = dialogFirstPageText;
    public By dialogThirdPageTitle = dialogFirstPageTitle;
    public By dialogSecondPageTitle = dialogFirstPageTitle;
    public By nextButton = By.xpath("/html/body/div[2]/div[3]/div[@role='dialog']//button[@data-test='user-onboarding-next']");
    public By routingNumberField = By.id("bankaccount-routingNumber-input");

}
