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
    String textForSearchResult = "iphone kyiv buy";
    String expectedText = "stylus.ua";
    int maxPageNumber = 5;

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(url);
    }

    @Test
    public void searchLinkInResultCorrectTest() {
        driver.findElement(By.name("q")).sendKeys(textForSearchResult + Keys.ENTER);
        WebElement stats = wait.until(presenceOfElementLocated(By.cssSelector("#result-stats")));

        aa:
        for (int i = 1; i <= maxPageNumber; i++) {
            List<WebElement> searchResults = driver.findElements(By.cssSelector("div cite"));
            bb:
            for (WebElement element : searchResults) {
                if (element.getText().contains(expectedText)) {
                    System.out.println("[" + expectedText + "] was found on page " + i);
                    break aa;
                }
            }
            driver.findElement(cssSelector("#pnnext")).click();
            if (maxPageNumber == i) {
                System.out.println("[" + expectedText + "] was not found on " + i + " pages");
            }
        }
    }
}
