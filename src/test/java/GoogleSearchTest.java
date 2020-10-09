import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class GoogleSearchTest {

    @Test
    public void positiveEnterSearchTest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get("https://google.com/ncr");
        driver.findElement(By.name("q")).sendKeys("cheese" + Keys.ENTER);

        WebElement stats = wait.until(presenceOfElementLocated(By.cssSelector("#result-stats")));

        System.out.println(stats.getText());
        driver.quit();
    }

    @Test
    public void positiveClickButtonSearchTest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get("https://google.com/ncr");
        driver.findElement(By.name("q")).sendKeys("cheese");
        driver.findElement(By.xpath("//*[contains(text(), 'Ukraine')]"));
        Thread.sleep(200);
        driver.findElement(By.name("btnK")).click();

        WebElement stats = wait.until(presenceOfElementLocated(By.cssSelector("#result-stats")));

        System.out.println(stats.getText());
        driver.quit();
    }





}