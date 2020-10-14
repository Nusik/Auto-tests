package rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertEquals;

public class RozetkaBusketTest extends BaseTestRozetka {

    String url = "https://rozetka.com.ua/";
    String searchText = "samsung";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(url);
    }

    @Test
    public void basketIconTest() {

        driver.findElement(By.name("search")).sendKeys(searchText + Keys.ENTER);
        By firstProductLink = By.cssSelector("a.goods-tile__picture");
        wait.until(presenceOfElementLocated(firstProductLink));
        driver.findElement(firstProductLink).click();

        By buyButtonSelector = By.cssSelector("buy-button button button_with_icon");
        scrollToElement(driver.findElement(buyButtonSelector));
        wait.until(presenceOfElementLocated(By.cssSelector("ul.product-statuses")));
        scrollToElement(driver.findElement(By.cssSelector("div.product-credit")));

        wait.until(presenceOfElementLocated(buyButtonSelector));
        scrollToElement(driver.findElement(By.cssSelector("ul.product-statuses")));
        assertEquals(driver.findElements(By.xpath("//a[@href='https://rozetka.com.ua/cart/']/span")).size(), 0);

        driver.findElement(buyButtonSelector).click();
        driver.findElement(By.cssSelector("button.modal__close")).click();

        WebElement basketIcon = driver.findElement(By.xpath("//a[@href='https://rozetka.com.ua/cart/']/span"));
        assertEquals(basketIcon.getText(), 1);
    }


    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
