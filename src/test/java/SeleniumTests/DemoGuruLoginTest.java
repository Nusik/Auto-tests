package SeleniumTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertEquals;

public class DemoGuruLoginTest extends BaseUiTest {

    String loginUrl = "http://demo.guru99.com/Agile_Project/Agi_V1/index.php";
    String login = "1303";
    String password = "Guru99";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(loginUrl);
    }

    @Test
    public void positiveLoginTest() {
        driver.findElement(By.name("uid")).sendKeys(login);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@name='btnLogin']")).click();
        driver.manage().window().fullscreen();
        WebElement logoutButton = wait.until(presenceOfElementLocated(By.linkText("Log out")));
        logoutButton.click();

        assertEquals(driver.switchTo().alert().getText(), "You Have Succesfully Logged Out!!");
        driver.switchTo().alert().accept();
        assertEquals(driver.getCurrentUrl(), loginUrl);
    }

    @Test
    public void negativeWrongPasswordLoginTest() {
        driver.findElement(By.name("uid")).sendKeys(login);
        driver.findElement(By.name("password")).sendKeys("12345");
        driver.findElement(By.xpath("//input[@name='btnLogin']")).click();

        assertEquals(driver.switchTo().alert().getText(), "User or Password is not valid");
        driver.switchTo().alert().accept();
        assertEquals(driver.getCurrentUrl(), loginUrl);
    }

    @Test
    public void negativeWrongLoginLoginTest() {
        driver.findElement(By.name("uid")).sendKeys("guru");
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@name='btnLogin']")).click();

        assertEquals(driver.switchTo().alert().getText(), "User or Password is not valid");
        driver.switchTo().alert().accept();
        assertEquals(driver.getCurrentUrl(), loginUrl);
    }

    @Test
    public void negativeWrongLoginPasswordLoginTest() {
        driver.findElement(By.name("uid")).sendKeys("guru");
        driver.findElement(By.name("password")).sendKeys(" ");
        driver.findElement(By.xpath("//input[@name='btnLogin']")).click();

        assertEquals(driver.switchTo().alert().getText(), "User or Password is not valid");
        driver.switchTo().alert().accept();
        assertEquals(driver.getCurrentUrl(), loginUrl);
    }

    @Test
    public void negativeEmptyFieldsLoginTest() {
        driver.findElement(By.name("btnLogin")).click();
        assertEquals(driver.switchTo().alert().getText(), "User or Password is not valid");
        driver.switchTo().alert().accept();
        assertEquals(driver.getCurrentUrl(), loginUrl);
    }

    @Test
    public void negativeEmptyLoginFieldLoginTest() {
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("btnLogin")).click();

        assertEquals(driver.switchTo().alert().getText(), "User or Password is not valid");
        driver.switchTo().alert().accept();
        assertEquals(driver.getCurrentUrl(), loginUrl);
    }

    @Test
    public void negativeEmptyPasswordFieldLoginTest() {
        driver.findElement(By.name("uid")).sendKeys(login);
        driver.findElement(By.name("btnLogin")).click();

        assertEquals(driver.switchTo().alert().getText(), "User or Password is not valid");
        driver.switchTo().alert().accept();
        assertEquals(driver.getCurrentUrl(), loginUrl);
    }

    @Test
    public void negativeWordingUnderEmptyFieldsLoginTest() throws InterruptedException {
        driver.findElement(By.name("uid")).sendKeys(" ");
        driver.findElement(By.name("uid")).clear();
        driver.findElement(By.name("password")).sendKeys(" ");
        driver.findElement(By.name("password")).clear();

        assertEquals(driver.findElement(By.xpath("//label[@id='message23']")).getText(), "User-ID must not be blank");
        assertEquals(driver.findElement(By.xpath("//label[@id='message18']")).getText(), "Password must not be blank");
    }
}
