package SeleniumTests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class GoogleSearchResultInListTest extends BaseUiTest {

    String url = "https://google.com/ncr";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(url);
    }

    @Test
    public void searchLinkInResultTest() {
        int count = 1;
        driver.findElement(By.name("q")).sendKeys("iphone kyiv buy" + Keys.ENTER);

        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        for (WebElement element : allLinks) {
            String sites = element.getAttribute("href");
        }
        while (count <= 5) {
            if (allLinks.size() != 0 && allLinks.contains("stylus.ua")) {
                System.out.println("STYLUS.UA found on " + count + " page");
            } else {
                driver.findElement(cssSelector("#pnnext")).click();
                count++;
                List<WebElement> Links = driver.findElements(By.tagName("cite"));
                for (WebElement element : Links) {
                    String sites = element.getAttribute("stylus.ua");
                }
                if (Links.size() != 0 && !Links.contains("stylus.ua")) {
                    System.out.println("STYLUS.UA found on " + count + " page");
                } else {
                    System.out.println("STYLUS.UA not found on first 5 pages");
                }
            }
            break;
        }
    }
}
