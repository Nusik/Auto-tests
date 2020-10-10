import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertTrue;

public class GoogleSearchResultInListTest extends BaseUiTest {

    String url = "https://google.com/ncr";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(url);
    }

    @Test
    public void searchLinkInResultTest() {
        String siteName = "stylus.ua";

        driver.findElement(By.name("q")).sendKeys("iphone kyiv buy" + Keys.ENTER);
        List<WebElement> links = driver.findElements(By.cssSelector("#cite"));        // не нашел сайт стилуса, хотя в результате на 1ой странице
//        for (WebElement element : links) {
//           System.out.println("site: " + element.getText());}

        for (int i = 1; i <= 5; i = i + 1) {
            if (!links.contains(siteName)) {

                driver.findElement(By.cssSelector("#pnnext")).click();
                List<WebElement> link2 = driver.findElements(By.cssSelector("#cite"));

                if (!link2.contains(siteName)) {
                    return;
                } else {
                    System.out.println(siteName.toUpperCase() + " found on page " + i);
                }
                System.out.println(siteName.toUpperCase() + " not found on page " + i);  //как указать номер страницы? какой цикл нужен?

            } else {
                System.out.println(siteName.toUpperCase() + " found on page " + i);
            }
        }
    }
}

//for (int i = 1; i <= numberOfPages; i = i + 1) {
//        for (int i = 1; i <= links.size(); i = i + 1) {
//            for (int j = 1; j <= links.size(); j = j + 1) {
//                String list = links.get(i).getText();
//                System.out.println(list);

//driver.findElement(By.xpath("//span[text()='Next']")).click();
//        if (list.contains("stylus.ua"))
//            {
//                System.out.println("STYLUS.UA found on " + " page");
//                driver.findElements(By.xpath("//cite[text()='stylus.ua']"));
//            } else {
//                System.out.println("STYLUS.UA not found on first 5 pages");
//            }


//       driver.findElement(By.xpath("//*[contains(text(), 'Ukraine')]"));
//       driver.findElement(By.name("btnK")).click();

//        List<WebElement> link = wait.until(presenceOfElementLocated(By.cssSelector("#result-stats")));
//      assertTrue(link.get() getText().contains("About"));
