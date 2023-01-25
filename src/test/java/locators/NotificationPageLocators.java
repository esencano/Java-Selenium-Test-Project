package locators;

import org.openqa.selenium.By;

public class NotificationPageLocators {

    public By header = By.xpath("//main//h2");
    public By notificationList = By.xpath("//ul[@data-test='notifications-list']//li");
    public By notificationIcon = By.xpath("./div/*[local-name()='svg']/*[local-name()='path']");
    public By notificationDescription = By.xpath("./div/span");
    public By notificationDismissButton = By.xpath("./button");

}
