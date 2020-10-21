package pagesRozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class ProductPageObject {

    WebDriver webDriver;
    WebDriverWait wait;

    public ProductPageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, 5);
    }

    By compareCounter = By.cssSelector("span.header-actions__button-counter");
    By compareButtonHeader = By.xpath("//button[@class='header-actions__button header-actions__button_type_compare header-actions__button_state_active']");
    By compareButtonProductPage = By.xpath("//*[@class='compare-button']");
    By productName = By.className("product__title");
    By productPrice = By.cssSelector("p.product-prices__big.product-prices__big_color_red");

    public void waitCompareButtonAppear() {
        wait.until(ExpectedConditions.elementToBeClickable(compareButtonProductPage));
    }

    public void addProductToComparison() {
        webDriver.findElement(compareButtonProductPage).click();
    }

    public void waitCompareCounter() {
        wait.until(ExpectedConditions.presenceOfElementLocated(compareCounter));
    }

    public void waitUpdatedCompareCounterAppear() {
        wait.until(ExpectedConditions.not(textToBePresentInElement(compareCounter, "1")));
    }

    public String getCompareCounter() {
        return webDriver.findElement(compareButtonHeader).getText();
//        return Integer.parseInt(webDriver.findElement(compareCounter).getText());
    }

    public String getProductName() {
        return webDriver.findElement(productName).getText();
    }

    public String getProductPrice() {
        return webDriver.findElement(productPrice).getText();
    }

    public void clickCompareInHeader() {
        webDriver.findElement(compareButtonHeader).click();
    }
}
