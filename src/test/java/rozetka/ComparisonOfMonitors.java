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

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class ComparisonOfMonitors extends BaseTestRozetka {

    String url = "https://rozetka.com.ua/";
    String priceForSearch = "3000";

    By pcAndNotebooksMenu = By.xpath("//a[contains(@href, 'computers-notebooks')][@class='menu-categories__link']");
    By monitorMenu = By.xpath("//a[@class='menu__link'][contains(@href,'monitors/c80089')][contains(text(),'Мониторы')]");
    By compareCounter = By.cssSelector("span.header-actions__button-counter");
    By compareButtonHeader = By.xpath("//button[@class='header-actions__button header-actions__button_type_compare header-actions__button_state_active']");
    By compareButtonProductPage = By.xpath("//*[@class='compare-button']");
    By comparisonModal = By.className("comparison-modal__link");
    By actualPriceInProductPage = By.cssSelector("p.product-prices__big.product-prices__big_color_red");
    By productCellInComparisonPage = By.cssSelector("li.products-grid__cell");
    By productPriceInComparisonPage = By.cssSelector("div.product__price.product__price--red");
    By productNameInComparisonPage = By.className("product__heading");
    By productPriceInMonitorsPage = By.cssSelector("div.goods-tile__prices");

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(url);
    }

    @Test
    public void comparisonOfMonitorsTest() throws Exception {

        Actions action = new Actions(driver);

        //1. Hover menu with laptops and then with monitors and open the last one
        WebElement pcAndNotebooksShowMenu = driver.findElement(pcAndNotebooksMenu);
        action.moveToElement(pcAndNotebooksShowMenu).perform();
        wait.until(ExpectedConditions.elementToBeClickable(monitorMenu)).click();

        //2. In Monitors menu find the first monitor with price < 3000
        wait.until(presenceOfElementLocated(productPriceInMonitorsPage));
        findMonitor(priceForSearch);

        //3. Add first monitor to comparison, verify red icon (1)
        WebElement comparison = (new WebDriverWait(driver, 4))
                .until(ExpectedConditions.elementToBeClickable(compareButtonProductPage));
        comparison.click();
        WebElement waiter = (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.presenceOfElementLocated(compareCounter));
        int compareCounterFirst = Integer.parseInt(driver.findElement(compareCounter).getText());

        Assert.assertEquals(compareCounterFirst, 1, "Wrong compare number");

        WebElement name = driver.findElement(By.tagName("h1"));
        String monitorName1 = name.getText();
        WebElement price = driver.findElement(actualPriceInProductPage);
        String monitorPrice1 = price.getText().replaceAll(" ", "");

        //4. Return to Monitors page and open page with monitor which price less than 1st find monitor
        driver.navigate().back();
        wait.until(presenceOfElementLocated(productPriceInMonitorsPage));

        findMonitor(monitorPrice1.substring(0, 4));

        //5. Add second monitor to comparison, verify red icon (2)
        WebElement compareButtonSecondProduct = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(compareButtonProductPage));
        //  compareButtonSecondProduct.isDisplayed();
        compareButtonSecondProduct.click();

        WebElement compareCounterSecond = driver.findElement(compareCounter);

        //  int compareCounterSecond = Integer.parseInt(driver.findElement(compareCounter).getText());
        //  waiter.isDisplayed();
        wait.until(ExpectedConditions.not(textToBePresentInElement(compareCounterSecond, "1")));
        Assert.assertEquals(compareCounterSecond.getText(), "2", "Wrong compare number");

        WebElement name2 = driver.findElement(By.className("product__title"));
        String monitorName2 = name2.getText();
        WebElement price2 = driver.findElement(actualPriceInProductPage);
        String monitorPrice2 = price2.getText().replaceAll(" ", "");

        //6. Click on comparison button in header, click on "Мониторы (2)" and open page with comparison of both products
        driver.findElement(compareButtonHeader).click();
        WebElement comparisonOfProductsLink = (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.elementToBeClickable(comparisonModal));
        comparisonOfProductsLink.click();

        //Verify that in comparison you have just 2 monitors
        wait.until(presenceOfElementLocated(productCellInComparisonPage));
        WebElement waitCells = (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.presenceOfElementLocated(productCellInComparisonPage));
        List<WebElement> productsList = driver.findElements(productCellInComparisonPage);

        Assert.assertEquals(productsList.size(), 2, "Wrong number of monitors");

        //Verify that names and prices are correct (equal to names/prices which you stored in previous steps)
        String productName1 = productsList.get(0).findElement(productNameInComparisonPage).getText();
        String productName2 = productsList.get(1).findElement(productNameInComparisonPage).getText();

        String productPrice1 = productsList.get(0).findElement(productPriceInComparisonPage).getText()
                .replaceAll("\\s+", "");
        String[] productPrice1Split = productPrice1.split("₴");
        productPrice1 = productPrice1Split[1] + "₴";

        String productPrice2 = productsList.get(1).findElement(productPriceInComparisonPage).getText()
                .replaceAll("\\s+", "");
        String[] productPrice2Split = productPrice2.split("₴");
        productPrice2 = productPrice2Split[1] + "₴";

        Assert.assertEquals(monitorName1, productName1);
        Assert.assertEquals(monitorName2, productName2);
        Assert.assertEquals(monitorPrice1, productPrice1);
        Assert.assertEquals(monitorPrice2, productPrice2);
    }

    private void findMonitor(String price) throws Exception {

        List<WebElement> webList = driver.findElements(By.cssSelector("span.goods-tile__price-value"));
        for (int i = 0; i <= webList.size(); i++) {
            String monitorsListAll = webList.get(i).getText().replaceAll(" ", "");
            if (i == webList.size()) {
                throw new Exception("There is no such monitor");
            }
            if (Integer.parseInt(price) > Integer.parseInt(monitorsListAll)) {
                List<WebElement> monitorsListFiltered = driver.findElements(By.xpath("//a[@class='goods-tile__picture']"));
                monitorsListFiltered.get(i).click();
                break;
            }
        }
    }
}