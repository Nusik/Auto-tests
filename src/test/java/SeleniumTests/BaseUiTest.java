package SeleniumTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;

public class BaseUiTest {

    public WebDriver driver;
    public WebDriverWait wait;

    @BeforeClass
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
//        ChromeOptions options = new ChromeOptions();
//        options. addExtensions(new File("/path/to/extension.crx"));
//        options.setBinary(new File("/path/to/chrome"));
//        ChromeDriver driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
    }

    @AfterClass
    public void closeDriver() {
        driver.quit();
    }
}
