package locators;

import org.openqa.selenium.By;

public class NavbarLocators {
    public By navbar = By.xpath("//div[@data-test='sidenav']/div");
    public By navbarToggleButton = By.xpath("//button[@data-test='sidenav-toggle']");
    public By navbarFullNameText = By.xpath("//h6[@data-test='sidenav-user-full-name']");
    public By navbarUsernameText = By.xpath("//h6[@data-test='sidenav-username']");

    public By navbarAccountBalanceText = By.xpath("//h6[@data-test='sidenav-user-balance']");

    // 2 kind of usage is possible for navbar buttons by xpath and by link text. Example shows below
    public By navbarHomeButton = By.xpath("//a[@data-test='sidenav-home']");
    public By navbarMyAccountButton = By.linkText("My Account");
    public By navbarBankAccountsButton =  By.xpath("//a[@data-test='sidenav-bankaccounts']");
    public By navbarNotificationsButton=  By.xpath("//a[@data-test='sidenav-notifications']");
    public By navbarLogouttButton = By.xpath("//div[@data-test='sidenav-signout']");
}
