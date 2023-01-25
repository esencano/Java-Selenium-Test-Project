package pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import locators.HomePageLocators;
import org.openqa.selenium.*;

import java.util.List;

public class HomePage extends BasePage{
    public final HomePageLocators homePageLocators = new HomePageLocators();

    private List<WebElement> getAllTransactions(WebDriver driver){return driver.findElements(homePageLocators.transactionList);}
    private WebElement getTransactionWebElementByIndex(WebDriver driver, int index){return getAllTransactions(driver).get(index);}

    @Step
    public void gotoHomePagePublicSection(WebDriver driver){driver.get(URL_HOME_PAGE_PUBLIC_SECTION);}
    @Step
    public void gotoHomePageContactsSection(WebDriver driver){driver.get(URL_HOME_PAGE_CONTACTS_SECTION);}
    @Step
    public void gotoHomePagePersonalSection(WebDriver driver){driver.get(URL_HOME_PAGE_PERSONAL_SECTION);}

    @Step
    public void waitHomePageLoaded(WebDriver driver){
        waitUntilURLTobe(driver,BASE_URL+"/");
    }

    @Step
    public void clickEveryoneTabButton(WebDriver driver){
        clickElementByLocator(driver,homePageLocators.everyoneTab);}
    @Step
    public void clickFriendsTabButton(WebDriver driver){
        clickElementByLocator(driver,homePageLocators.friendsTab);}
    @Step
    public void clickMineTabButton(WebDriver driver){
        clickElementByLocator(driver,homePageLocators.mineTab);}

    @Step
    @Attachment
    public boolean isSenderAvatarDisplayed(WebDriver driver, int index){return !getTransactionWebElementByIndex(driver,index).findElements(homePageLocators.senderAvatar).isEmpty();}
    @Step
    @Attachment
    public boolean isSenderDefaultAvatarDisplayed(WebDriver driver, int index){return !getTransactionWebElementByIndex(driver,index).findElements(homePageLocators.senderDefaultAvatar).isEmpty();}
    @Step
    @Attachment
    public String getSenderAvatarPath(WebDriver driver, int index){return getTransactionWebElementByIndex(driver,index).findElement(homePageLocators.senderAvatar).getAttribute("src");}
    @Step
    @Attachment
    public boolean isReceiverAvatarDisplayed(WebDriver driver, int index){return !getTransactionWebElementByIndex(driver,index).findElements(homePageLocators.receiverAvatar).isEmpty();}
    @Step
    @Attachment
    public boolean isReceiverDefaultAvatarDisplayed(WebDriver driver, int index){return !getTransactionWebElementByIndex(driver,index).findElements(homePageLocators.receiverDefaultAvatar).isEmpty();}
    @Step
    @Attachment
    public String getReceiverAvatarPath(WebDriver driver, int index){return getTransactionWebElementByIndex(driver,index).findElement(homePageLocators.receiverAvatar).getAttribute("src");}
    @Step
    @Attachment
    public int getCountOfDisplayedTransactions(WebDriver driver){return getAllTransactions(driver).size();}

    @Step
    @Attachment
    public String getSenderFirstNameAndLastname(WebDriver driver, int index){return getTransactionWebElementByIndex(driver,index).findElement(homePageLocators.senderFirstNameAndLastname).getText();}
    @Step
    @Attachment
    public String getReceiverFirstNameAndLastname(WebDriver driver, int index){return getTransactionWebElementByIndex(driver,index).findElement(homePageLocators.receiverFirstNameAndLastname).getText();}
    @Step
    @Attachment
    public String getTransactionType(WebDriver driver, int index){return getTransactionWebElementByIndex(driver,index).findElement(homePageLocators.transactionType).getText();}
    @Step
    @Attachment
    public String getTransactionDescription(WebDriver driver, int index){return getTransactionWebElementByIndex(driver,index).findElement(homePageLocators.transactionDescription).getText();}

    @Step
    @Attachment
    public String getTransactionLikeCount(WebDriver driver, int index){return getTransactionWebElementByIndex(driver,index).findElement(homePageLocators.transactionLikeCount).getText();}
    @Step
    @Attachment
    public String getTransactionCommentCount(WebDriver driver, int index){return getTransactionWebElementByIndex(driver,index).findElement(homePageLocators.transactionCommentCount).getText();}
    @Step
    @Attachment
    public String getTransactionAmount(WebDriver driver, int index){return getTransactionWebElementByIndex(driver,index).findElement(homePageLocators.transactionAmount).getText();}
    @Step
    @Attachment
    public boolean isPositiveStyleClassAppliedForAmount(WebDriver driver, int index){return isElementClassContains(getTransactionWebElementByIndex(driver,index).findElement(homePageLocators.transactionAmount),"makeStyles-amountNegative");}
    @Step
    @Attachment
    public boolean isNegativeStyleClassAppliedForAmount(WebDriver driver, int index){return isElementClassContains(getTransactionWebElementByIndex(driver,index).findElement(homePageLocators.transactionAmount),"makeStyles-amountPositive");}

    @Step
    public void clickTransaction(WebDriver driver,int index){getTransactionWebElementByIndex(driver,index).click();}
   @Step
    public void scrollTransactions(WebDriver driver){
        for (int i = 0; i < 5; i++) {
            driver.findElement(homePageLocators.transactionsGrid).sendKeys(Keys.PAGE_DOWN);
        }

    }
    @Step
    public void clickDatePickerFilterButton(WebDriver driver){
        clickElementByLocator(driver,homePageLocators.filterDatePickerButton);}

}
