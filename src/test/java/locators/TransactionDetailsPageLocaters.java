package locators;

import org.openqa.selenium.By;

public class TransactionDetailsPageLocaters {
    public By acceptButton = By.xpath("//button[contains(@data-test,'transaction-accept-request')]");
    public By rejectButton = By.xpath("//button[contains(@data-test,'transaction-reject-request')]");

}
