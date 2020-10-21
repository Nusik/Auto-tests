package pagesRozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.refreshed;

public class ComparisonPage {

    WebDriver webDriver;
    WebDriverWait wait;

    public ComparisonPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, 3);
    }

    By comparisonModal = By.className("comparison-modal__link");

    By productCellInComparisonPage = By.cssSelector("li.products-grid__cell");
    By productPriceInComparisonPage = By.cssSelector("div.product__price.product__price--red");
    By productNameInComparisonPage = By.className("product__heading");

    public void waitComparisonModalAppear() {
        wait.until(ExpectedConditions.elementToBeClickable(comparisonModal));
    }

    public void clickOnCompareProductInModal() {
        webDriver.findElement(comparisonModal).click();
    }

    public void waitProductAppear() {
        wait.until(ExpectedConditions.presenceOfElementLocated(productCellInComparisonPage));
//        WebElement waitCells = (new WebDriverWait(driver, 3))
//                .until(ExpectedConditions.presenceOfElementLocated(productCellInComparisonPage));
    }

    public List<WebElement> getProductsList() {
        return webDriver.findElements(productCellInComparisonPage);
    }

    public String getProductName(int i) {
        return getProductsList().get(i).findElement(productNameInComparisonPage).getText();
    }

    public String getProductPrice(int i) {
        String price = getProductsList().get(i).findElement(productPriceInComparisonPage)
                .getText().replaceAll("\\s+", "");
        String[] priceSplit = price.split("₴");
        return priceSplit[1] + "₴";
    }


}
