package pagesRozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class SearchResultPageObject {

    WebDriver webDriver;
    WebDriverWait wait;

    public SearchResultPageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, 3);
    }

    By mobilePhoneTitle = By.cssSelector("span.goods-tile__title");
    By productPriceActual = By.xpath("//*[contains(@class,'goods-tile__price-value')]");
    By priceList = By.cssSelector("div.goods-tile__prices");

    public void waitProductsTitle() {
        wait.until(ExpectedConditions.presenceOfElementLocated(mobilePhoneTitle));
    }

    public void waitProductPrice() {
        wait.until(presenceOfElementLocated(priceList));
    }

    private List<WebElement> getAlMobileNamesFromTitle() {
        return webDriver.findElements(mobilePhoneTitle);
    }

    private List<WebElement> getAllPricesFromTitle() {
        return webDriver.findElements(productPriceActual);
    }

    public boolean comparePhoneNamesWithSelected(String productName1, String productName2, String productName3) throws Exception {
        List<WebElement> phoneNames = getAlMobileNamesFromTitle();
        for (WebElement element : phoneNames) {
            String phoneTitle = element.getText();
            if (phoneTitle.contains(productName1) || phoneTitle.contains(productName2) || phoneTitle.contains(productName3)) {
                continue;
            } else {
                throw new Exception("Manufactures Samsung, Apple, Huawei are not present on the page");
            }
        }
        return true;
    }

    public boolean findSelectedRamSizeInTitle(String ramSize) throws Exception {
        List<WebElement> mobileRamSizeInTitle = getAlMobileNamesFromTitle();
        for (WebElement element : mobileRamSizeInTitle) {
            if (element.getText().contains(ramSize)) {
                continue;
            } else {
                throw new Exception("Some products don't have RAM size in title");
            }
        }
        return true;
    }

    public boolean compareProductPriceWithSelected(int minPrice, int maxPrice) throws Exception {
        List<WebElement> mobilePrices = getAllPricesFromTitle();
        int maxPriceBorder = maxPrice + 1;
        String maxPriceBorderString = String.valueOf(maxPriceBorder);
        int minPriceBorder = minPrice - 1;
        String minPriceBorderString = String.valueOf(minPriceBorder);

        for (WebElement element : mobilePrices) {
            String mobilesList = element.getText().replaceAll("[^0-9]", "");
            Integer.parseInt(mobilesList);

            if (mobilesList.startsWith(maxPriceBorderString) && mobilesList.endsWith(minPriceBorderString)) {
                throw new Exception("Some mobile phones have price < " + minPrice + " and > " + maxPrice);
            }
        }
        return true;
    }

    public void findMonitor(String price) throws Exception {
        List<WebElement> webList = webDriver.findElements(By.cssSelector("span.goods-tile__price-value"));
        for (int i = 0; i <= webList.size(); i++) {
            String monitorsListAll = webList.get(i).getText().replaceAll(" ", "");
            if (i == webList.size()) {
                throw new Exception("There is no such monitor");
            }
            if (Integer.parseInt(price) > Integer.parseInt(monitorsListAll)) {
                List<WebElement> monitorsListFiltered = webDriver.findElements(By.xpath("//a[@class='goods-tile__picture']"));
                monitorsListFiltered.get(i).click();
                break;
            }
        }
    }
}
