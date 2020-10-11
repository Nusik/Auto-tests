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

    @Test //works only when result in 1st page
    public void searchLinkInResultTest() {
        int count = 1;
        driver.findElement(By.name("q")).sendKeys("iphone kyiv buy" + Keys.ENTER);

        WebElement link = driver.findElement(By.partialLinkText("stylus.ua"));

        if (link.isDisplayed()) {
            System.out.println("STYLUS.UA found on " + count + " page");
        } else {
            while (count <= 5) {
                driver.findElement(cssSelector("#pnnext")).click();
                WebElement link1 = wait.until(presenceOfElementLocated(By.partialLinkText("stylus.ua")));
                count++;
                if (link.isDisplayed()) {
                    System.out.println("STYLUS.UA found on " + count + " page");
                } else {
                    return;
                }
            }
            System.out.println("STYLUS.UA not found on first 5 pages");
        }
    }

    @Test
    public void searchSite2() {
        int count = 1;
        driver.findElement(By.name("q")).sendKeys("iphone kyiv buy" + Keys.ENTER);

        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        for (WebElement element : allLinks) {
            String sites = element.getAttribute("href");
            System.out.println(sites);
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
                    System.out.println("STYLUS.UA found on " + count + " page");
                }
                if (Links.size() != 0 && Links.contains("stylus.ua")) {
                    System.out.println("STYLUS.UA found on " + count + " page");
                }
                System.out.println("STYLUS.UA not found on first 5 pages");
            }
            break;
        }
    }
}