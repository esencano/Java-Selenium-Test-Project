package locators;

import org.openqa.selenium.By;

public class HeaderBarLocators {

    public By logo = By.xpath("//header//h1/a");
    public By newButton = By.xpath("//header//a[@data-test='nav-top-new-transaction']");
    public By notificationsButton = By.xpath("//header//a[@data-test='nav-top-notifications-link']");
    public By notificationsCount = By.xpath("//header//a[@data-test='nav-top-notifications-link']//span/span/span");
}
