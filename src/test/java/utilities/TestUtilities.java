package utilities;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;


public class TestUtilities {

    public File getScreenShoot(WebDriver driver)  {
        TakesScreenshot ts = ((TakesScreenshot) driver);
        File ss = ts.getScreenshotAs(OutputType.FILE);
        return ss;
    }

    public String saveSS(File ss, String ssName) throws IOException{
        TestProperties properties = new TestProperties();
        String path = properties.readProperty("ss_path");
        String fullPath = String.format("%s/%s.png",path,ssName);
        FileUtils.copyFile(ss, new File(fullPath));
        return fullPath;
    }
}
