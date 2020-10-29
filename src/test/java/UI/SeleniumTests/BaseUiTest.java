package UI.SeleniumTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseUiTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeClass
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
    }

//    private static ChromeOptions getBrowserOptions() {
//        ChromeOptions options = new ChromeOptions();
//       // options.addArguments("C:/Program Files (x86)/Google/Chrome/Application/chrome.exe");
//        options.addArguments("C:/Users/INNA/Library/Application Support/Google/Chrome");
//        options.addArguments("C:/Users/INNA/AppData/Local/Google/Chrome/User Data/Default");
//        return options;
//    }

    @AfterClass
    public void closeDriver() {
        driver.quit();
    }
}
