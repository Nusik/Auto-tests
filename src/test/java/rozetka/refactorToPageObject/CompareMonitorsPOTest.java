package rozetka.refactorToPageObject;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pagesRozetka.*;
import rozetka.BaseTestRozetka;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class CompareMonitorsPOTest extends BaseTestRozetka {

    String url = "https://rozetka.com.ua/";
    String priceForSearch = "3000";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(url);
    }

    @Test
    public void comparisonOfMonitorsTest() throws Exception {
        MainPageObject mainPage = new MainPageObject(driver);
        SearchResultPageObject searchResultPage = new SearchResultPageObject(driver);
        ProductPageObject productPage = new ProductPageObject(driver);
        ComparisonPage comparisonPage = new ComparisonPage(driver);

        //1. Hover menu with laptops and then with monitors and open the last one
        mainPage.hoverMenu();
        mainPage.openSubMenuMonitors();

        //2. In Monitors menu find the first monitor with price < 3000
        searchResultPage.waitProductPrice();
        searchResultPage.findMonitor(priceForSearch);

        //3. Add first monitor to comparison, verify red icon (1)
        productPage.waitCompareButtonAppear();
        productPage.addProductToComparison();
        productPage.waitCompareCounter();
        productPage.getCompareCounter();

        Assert.assertEquals(productPage.getCompareCounter(), "1");

        String monitorName1 = productPage.getProductName();
        String monitorPrice1 = productPage.getProductPrice().replaceAll(" ", "");

        //4. Return to Monitors page and open page with monitor which price less than 1st find monitor
        driver.navigate().back();
        searchResultPage.waitProductPrice();

        searchResultPage.findMonitor(monitorPrice1.substring(0, 4)); //change arguments

        //5. Add second monitor to comparison, verify red icon (2)
        productPage.waitCompareButtonAppear();
        productPage.addProductToComparison();
        productPage.waitUpdatedCompareCounterAppear();
        //  int compareCounterSecond = Integer.parseInt(driver.findElement(compareCounter).getText());
        //  waiter.isDisplayed();
        Assert.assertEquals(productPage.getCompareCounter(), "2", "Wrong compare number");

        String monitorName2 = productPage.getProductName();
        String monitorPrice2 = productPage.getProductPrice().replaceAll(" ", "");

        //6. Click on comparison button in header, click on "Мониторы (2)" and open page with comparison of both products
        productPage.clickCompareInHeader();
        comparisonPage.waitComparisonModalAppear();
        comparisonPage.clickOnCompareProductInModal();

        //Verify that in comparison you have just 2 monitors
        comparisonPage.waitProductAppear();
        Assert.assertEquals(comparisonPage.getProductsList().size(), 2, "Wrong number of monitors");

        //Verify that names and prices are correct (equal to names/prices which you stored in previous steps)
        String productName1 = comparisonPage.getProductName(0);
        String productName2 = comparisonPage.getProductName(1);

        String productPrice1 = comparisonPage.getProductPrice(0);
        String productPrice2 = comparisonPage.getProductPrice(1);

        Assert.assertEquals(monitorName1, productName1);
        Assert.assertEquals(monitorName2, productName2);
        Assert.assertEquals(monitorPrice1, productPrice1);
        Assert.assertEquals(monitorPrice2, productPrice2);
    }
}