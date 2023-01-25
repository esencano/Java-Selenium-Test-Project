package locators;

import org.openqa.selenium.By;

public class BankAccountsPageLocators {
    /*
        Another approach to find webelements showed below.

        @FindBy(xpath = "//main/div[2]//h2")
        public WebElement header;
     */

    public By accountNumberErrorText = By.id("bankaccount-accountNumber-input-helper-text");
    public By accountNumberField = By.id("bankaccount-accountNumber-input");
    public By bankAccountBankName = By.xpath("div/div[1]/p");
    public By bankAccountDeleteButton = By.xpath(".//button[@data-test='bankaccount-delete']");
    public By bankAccountsList = By.xpath("//ul[@data-test='bankaccount-list']//li");
    public By bankNameErrorText = By.id("bankaccount-bankName-input-helper-text");
    public By bankNameField = By.id("bankaccount-bankName-input");
    public By createButton = By.xpath("//a[@data-test='bankaccount-new']");
    public By header = By.xpath("//main/div[2]//h2");
    public By routingNumberErrorText = By.id("bankaccount-routingNumber-input-helper-text");
    public By routingNumberField = By.id("bankaccount-routingNumber-input");
    public By saveButton = By.xpath("//button[@data-test='bankaccount-submit']");

}
