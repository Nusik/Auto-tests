package rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    public void comparisonOfMonitorsTest() throws InterruptedException {
        Actions action = new Actions(driver);

        By pcAndNotebooksMenu = By.xpath("//a[contains(@href, 'computers-notebooks')][@class='menu-categories__link']");
        WebElement pcAndNotebooksShowMenu = driver.findElement(pcAndNotebooksMenu);
        action.moveToElement(pcAndNotebooksShowMenu).perform();

        By monitorMenu = By.xpath("//a[@class='menu__link'][contains(@href,'monitors/c80089')][contains(text(),'Мониторы')]");
        wait.until(ExpectedConditions
                .elementToBeClickable(monitorMenu)).click();

        wait.until(presenceOfElementLocated(By.cssSelector("div.goods-tile__prices")));
        List<WebElement> monitorFirstPrice = driver.findElements(By.xpath("//*[contains(@class,'goods-tile__price-value')]"));
        Objects.requireNonNull(monitorFirstPrice.stream()
                .filter(e -> Integer.parseInt(e.getText().replaceAll("[^0-9]", "")) < 3000)
                .findFirst().orElse(null))
                .findElement(By.xpath(".//ancestor::*/a[1]")).click();
        Thread.sleep(5000);

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@class='compare-button']")))).click();
        Thread.sleep(5000);

        int compareCounterFirst = Integer.parseInt(driver.findElement(By.cssSelector("[class='header-actions__button-counter']")).getText());
        Assert.assertEquals(compareCounterFirst, 1, "Wrong compare number");

        driver.navigate().back();
        wait.until(presenceOfElementLocated(By.cssSelector("div.goods-tile__prices")));
        List<WebElement> monitorSecondPrice = driver.findElements(By.xpath("//*[contains(@class,'goods-tile__price-value')]"));
        Objects.requireNonNull(monitorSecondPrice.stream()
                .filter(e -> Integer.parseInt(e.getText().replaceAll("[^0-9]", "")) < 2999)
                .findFirst().orElse(null))
                .findElement(By.xpath(".//ancestor::*/a[2]")).click();
        Thread.sleep(5000);

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@class='compare-button']"))))
                .click();
        Thread.sleep(5000);

        int compareCounterSecond = Integer.parseInt(driver.findElement(By.cssSelector("[class='header-actions__button-counter']")).getText());
        Thread.sleep(5000);
        Assert.assertEquals(compareCounterSecond, 2, "Wrong compare number");

        driver.findElement(By.xpath("//button[@class='header-actions__button header-actions__button_type_compare header-actions__button_state_active']"))
                .click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("comparison-modal__link"))))
                .click();
        Thread.sleep(4000);

        Assert.assertEquals(driver.findElements(By.xpath("//li[@class='products-grid__cell']")).size(), 2, "Wrong number of monitors");
    }
}