package rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Objects;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class ComparisonOfMonitors extends BaseTestRozetka {

    String url = "https://rozetka.com.ua/";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(url);
    }

    @Test
    public void comparisonOfMonitorsTest() {

        By pcAndNotebooksMenu = By.xpath("//a[contains(@href, 'computers-notebooks')][@class='menu-categories__link']");
        By monitorMenu = By.xpath("//a[@class='menu__link'][contains(@href,'monitors/c80089')][contains(text(),'Мониторы')]");
        By actualPrice = By.xpath("//*[contains(@class,'goods-tile__price-value')]");
        By compareButtonProductPage = By.xpath("//*[@class='compare-button']");
        By compareCounter = By.cssSelector("[class='header-actions__button-counter']");
        By compareButtonHeader = By.xpath("//button[@class='header-actions__button header-actions__button_type_compare header-actions__button_state_active']");
        By comparisonModal = By.className("comparison-modal__link");
        By productElementCell = By.xpath("//li[@class='products-grid__cell']");

        Actions action = new Actions(driver);

        //1. Hover menu with laptops and then with monitors and open the last one
        WebElement pcAndNotebooksShowMenu = driver.findElement(pcAndNotebooksMenu);
        action.moveToElement(pcAndNotebooksShowMenu).perform();
        wait.until(ExpectedConditions.elementToBeClickable(monitorMenu)).click();

        //2. In Monitors menu find the first monitor with price < 3000
        wait.until(presenceOfElementLocated(By.cssSelector("div.goods-tile__prices")));
        List<WebElement> monitorFirstPrice = driver.findElements(actualPrice);
        Objects.requireNonNull(monitorFirstPrice.stream()
                .filter(e -> Integer.parseInt(e.getText().replaceAll("[^0-9]", "")) < 3000)
                .findFirst().orElse(null))
                .findElement(By.xpath(".//ancestor::*/a[1]")).click();

        //3. Add first monitor to comparison
        WebElement comparison = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(compareButtonProductPage));
        comparison.click();

        WebElement waiter = (new WebDriverWait(driver, 2))
                .until(ExpectedConditions.presenceOfElementLocated(compareCounter));
        int compareCounterFirst = Integer.parseInt(driver.findElement(compareCounter).getText());

        Assert.assertEquals(compareCounterFirst, 1, "Wrong compare number");

        //4. Return to Monitors page and open page with monitor which price less than 1st find monitor
        driver.navigate().back();
        wait.until(presenceOfElementLocated(By.cssSelector("div.goods-tile__prices")));
        List<WebElement> monitorSecondPrice = driver.findElements(actualPrice);
        Objects.requireNonNull(monitorSecondPrice.stream()
                .filter(e -> Integer.parseInt(e.getText().replaceAll("[^0-9]", "")) < 2999)
                .findFirst().orElse(null))
                .findElement(By.xpath(".//ancestor::*/a[2]")).click();

        //5. Add second monitor to comparison
        WebElement compareButtonSecondProduct = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(compareButtonProductPage));
        compareButtonSecondProduct.isDisplayed();
        compareButtonSecondProduct.click();
        waiter.isDisplayed();
        int compareCounterSecond = Integer.parseInt(driver.findElement(compareCounter).getText());

        Assert.assertEquals(compareCounterSecond, 2, "Wrong compare number");

        //6. Click on comparison button in header and open page with comparison of both products
        driver.findElement(compareButtonHeader).click();
        WebElement comparisonOfProductsLink = (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.elementToBeClickable(comparisonModal));
        comparisonOfProductsLink.click();
        WebElement waitCells = (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.presenceOfElementLocated(productElementCell));

        Assert.assertEquals(driver.findElements(productElementCell).size(), 2, "Wrong number of monitors");


    }
}