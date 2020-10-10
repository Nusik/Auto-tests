import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

        WebElement link = driver.findElement(By.partialLinkText("stylus.ua"));

        if (link.isDisplayed()) {
            System.out.println("STYLUS.UA found on " + count + " page");
        } else {
            while (count <= 5) {
                driver.findElement(By.cssSelector("#pnnext")).click();
                driver.findElement(By.partialLinkText("stylus.ua"));
                count++;
                if (link.isDisplayed()) {
                    System.out.println("STYLUS.UA found on " + count + " page");
                } else {
                    System.out.println("STYLUS.UA not found on first 5 pages");
                }
            }
        }
    }
}