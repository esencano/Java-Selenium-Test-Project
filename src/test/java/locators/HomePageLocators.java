package locators;

import org.openqa.selenium.By;

public class HomePageLocators {
    public By everyoneTab = By.xpath("//a[@data-test='nav-public-tab']");
    public By friendsTab = By.xpath("//a[@data-test='nav-contacts-tab']");
    public By mineTab = By.xpath("//a[@data-test='nav-personal-tab']");

    public By filterDatePickerButton = By.xpath("//div[@data-test='transaction-list-filter-date-range-button']");
    public By filterDatePickerRoot= By.xpath("//div[@class='Cal__MonthList__root']/div/div");
    public By filterDatePickerDay = By.xpath("//ul[@class='Cal__Month__row']//li");
    public By filterDatePickerMonth = By.xpath("//ul[@class='Cal__Month__row']//li/span[@class='Cal__Day__month']");
    public By filterDatePickerDate= By.xpath("//li[@class='Cal__Day__root Cal__Day__enabled']");
    public By filterDatePickerYear = By.xpath("//ul[@class='Cal__Month__row' or @class='Cal__Month__row Cal__Month__partial']//li/span[@class='Cal__Day__year']");
    public By filterAmountRange = By.xpath("//div[@data-test='transaction-list-filter-amount-range-button']");

    public By transactionList = By.xpath("//div[@data-test='transaction-list']/div/div/div/li");

    public By senderAvatar = By.xpath(".//span[contains(@class,'MuiBadge-root')]/div/img");
    public By senderDefaultAvatar = By.xpath(".//span[contains(@class,'MuiBadge-root')]/div/*[local-name()='svg']/*[local-name()='path']");
    public By receiverAvatar = By.xpath(".//span[contains(@class,'MuiBadge-badge')]/div/img");
    public By receiverDefaultAvatar = By.xpath(".//span[contains(@class,'MuiBadge-badge')]/div/*[local-name()='svg']/*[local-name()='path']");

    public By senderFirstNameAndLastname = By.xpath(".//p[1]/span[1]");
    public By transactionType = By.xpath(".//p[1]/span[2]");
    public By receiverFirstNameAndLastname = By.xpath(".//p[1]/span[3]");

    public By transactionDescription = By.xpath(".//p[2]");
    public By transactionLikeCount = By.xpath(".//p[@data-test='transaction-like-count']");
    public By transactionCommentCount = By.xpath(".//p[contains(@data-test,'transaction-comment-count')]");
    public By transactionAmount = By.xpath(".//span[contains(@data-test,'transaction-amount-')]");

    public By transactionsGrid = By.xpath("//div[@data-test='transaction-list']/div");


}
