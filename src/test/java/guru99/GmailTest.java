package guru99;

import SeleniumTests.BaseUiTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class GmailTest extends BaseUiTest {

    String loginUrl = "https://mail.google.com/";
    private String login = "jotest91@gmail.com";
    private String password = "jotest_2020";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(loginUrl);
    }

    @Test
    public void positiveVerifyInboxEmail() throws InterruptedException {
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys(login + Keys.ENTER);
        Thread.sleep(3000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='password']"))).
                sendKeys(password + Keys.ENTER);

        //Google doesn't let me enter to my e-mail

    }


}
