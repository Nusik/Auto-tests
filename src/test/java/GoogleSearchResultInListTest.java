import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertTrue;

public class GoogleSearchResultInListTest extends BaseUiTest{

    String url = "https://google.com/ncr";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(url);
    }

    @Test
    public void positiveClickButtonSearchTest() throws InterruptedException {
        driver.findElement(By.name("q")).sendKeys("cheese");
        driver.findElement(By.xpath("//*[contains(text(), 'Ukraine')]"));
        Thread.sleep(200);
        driver.findElement(By.name("btnK")).click();
        WebElement stats = wait.until(presenceOfElementLocated(By.cssSelector("#result-stats")));
        assertTrue(stats.getText().contains("About"));
    }


}

