package pages;

import io.qameta.allure.Step;
import locators.TransactionDetailsPageLocaters;
import org.openqa.selenium.WebDriver;

public class TransactionDetailsPage extends BasePage{
    private final TransactionDetailsPageLocaters transactionDetailsPageLocaters = new TransactionDetailsPageLocaters();

    @Step
    public void clickAcceptButton(WebDriver driver){clickElementByLocator(driver,transactionDetailsPageLocaters.acceptButton);}
    @Step
    public void clickRejectButton(WebDriver driver){clickElementByLocator(driver,transactionDetailsPageLocaters.rejectButton);}
}
