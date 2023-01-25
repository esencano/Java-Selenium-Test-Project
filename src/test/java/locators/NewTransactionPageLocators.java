package locators;

import org.openqa.selenium.By;

public class NewTransactionPageLocators {
    public By progressIcons = By.xpath("//main//span//*[local-name()='svg'][1]"); //1st element 'Select Contact', 2nd element 'Payment', 3rd element 'Completed'

    public By searchField = By.id("user-list-search-input");
    public By userList = By.xpath("//ul[@data-test='users-list']//li");
    public By userAvatar = By.xpath(".//div/img");
    public By userAvatarDefault = By.xpath(".//div//*[local-name()='path']");
    public By userFirstNameAndLastname = By.xpath("./div[2]/span");
    public By userUsername = By.xpath(".//p//span[contains(@class,'MuiGrid-item')][1]");
    public By userEmail= By.xpath(".//p//span[contains(@class,'MuiGrid-item')][3]");
    public By userPhoneNumber= By.xpath(".//p//span[contains(@class,'MuiGrid-item')][5]");

    public By paymentSectionUserAvatar = By.cssSelector("img.MuiAvatar-img");

    public By paymentSectionFirstNameAndLastName = By.xpath("//main//h2");
    public By paymentSectionAmountField = By.id("amount");
    public By paymentSectionAmountError = By.id("transaction-create-amount-input-helper-text");

    public By paymentSectionNoteField = By.id("transaction-create-description-input");
    public By paymentSectionNoteError = By.id("transaction-create-description-input-helper-text");
    public By paymentSectionRequestButton = By.xpath("//button[@data-test='transaction-create-submit-request']");
    public By paymentSectionPayButton = By.xpath("//button[@data-test='transaction-create-submit-payment']");

    public By completeSectionUserAvatar = By.cssSelector("img.MuiAvatar-img");
    public By completeSectionFirstNameAndLastName = By.xpath("//main//h2[1]");
    public By completeSectionCompleteMessage = By.xpath("//main//h2[1]");//second element of this list
    public By completeSectionReturnToTransactionsButton = By.xpath("//a[@data-test='new-transaction-return-to-transactions']");
    public By completeSectionCreateAnotherTransactionButton = By.xpath("//button[@data-test='new-transaction-create-another-transaction']");
}
