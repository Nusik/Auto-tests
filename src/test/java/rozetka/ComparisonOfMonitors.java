package rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SourceType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertEquals;

public class ComparisonOfMonitors extends BaseTestRozetka {

    String url = "https://rozetka.com.ua/";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(url);
    }

    @Test
    public void comparisonOfMonitorsTest() {
        driver.manage().window().maximize();
        Actions action = new Actions(driver);

        By pcAndNotebooksMenu = By.xpath("//a[contains(@href, 'computers-notebooks')][@class='menu-categories__link']");
        WebElement pcAndNotebooksShowMenu = driver.findElement(pcAndNotebooksMenu);
        action.moveToElement(pcAndNotebooksShowMenu).perform();

        By monitorElement = By.xpath("//a[@class='menu__link'][contains(@href,'monitors/c80089')][contains(text(),'Мониторы')]");
        wait.until(presenceOfElementLocated(monitorElement));
        WebElement selectElement = driver.findElement(monitorElement);
        selectElement.click();

        wait.until(presenceOfElementLocated(By.cssSelector("div.goods-tile__prices")));
        System.out.println(driver.findElements(By.xpath("//*[contains(@class,'goods-tile__price-value')]")).size());
        assertEquals(driver.findElements(By.xpath("//*[contains(@class,'goods-tile__price-value')]")).size(), 60);

        List<WebElement> monitorPrice = driver.findElements(By.xpath("//*[contains(@class,'goods-tile__price-value')]"));
        List<String> prices = new ArrayList<>();
        for (WebElement element : monitorPrice) {
            prices.add(element.getText());
            String priceString = String.valueOf(prices.add(element.getText())).replace("[^0-9]", "");
            int priceInt = Integer.parseInt(priceString);
            if (priceInt < 3000) {
                prices.stream().sorted()
                        .filter(e -> priceInt < 3000)
                        .findFirst();
                System.out.println(prices);


            } else {
                System.out.println("No monitors with price < 3000");
            }
        }
    }
}

