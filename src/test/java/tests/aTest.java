package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import listeners.TestListeners;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.SignupPage;

import java.io.IOException;

@Listeners({listeners.TestListeners.class, TestListeners.class})
@Test(groups = {"atest"})
public class aTest extends BaseTest {
    enum types {
        CHROME("chrome"),
        FIREFOX("firefox");
        private final String type;

        types(String type) {
            this.type = type;
        }
        public String toString() {
            return this.type;
        }
    }
    @Test
    @Description("abcstep")
    void Test1() throws IOException {
        SignupPage page = new SignupPage();
        page.gotoSignupPage(driver);
        WebElement element = driver.findElement(By.id("ercan"));
        //page.waitUntilVisibilityOfElementLocated(driver,driver.findElement(By.id("ercan")),3000);
    }

    @Step("Step2-1")
    void a() throws IOException {
        //Allure.addAttachment("Any text", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        /*TestUtilities testUtilities = new TestUtilities();
        testUtilities.getScreenShoot(driver,"abc");*/
        System.out.println("Ercan");
    }

}
