package pagesRozetka.RozetkaFactoryPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SearchResultPageFactory {

    WebDriver webDriver;
    WebDriverWait wait;

    public SearchResultPageFactory(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, 6);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(css = "span.goods-tile__title")
    private List<WebElement> mobilePhoneTitle;
    @FindBy(xpath = "//*[contains(@class,'goods-tile__price-value')]")
    private List<WebElement> productPriceActual;
    @FindBy(css = "div.goods-tile__prices")
    private List<WebElement> priceList;
    @FindBy(css = "span.goods-tile__price-value")
    private List<WebElement> goodsPriceValue;
    @FindBy(xpath = "//a[@class='goods-tile__picture']")
    private List<WebElement> goodsPicture;

    public void waitProductsTitle() {
        wait.until(ExpectedConditions.visibilityOfAllElements(mobilePhoneTitle));
    }

    public void waitProductPrice() {
        wait.until(ExpectedConditions.visibilityOfAllElements(priceList));
    }

    private List<WebElement> getAlMobileNamesFromTitle() {
        return mobilePhoneTitle;
    }

    private List<WebElement> getAllPricesFromTitle() {
        return productPriceActual;
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
        List<WebElement> webList = goodsPriceValue;
        for (int i = 0; i <= webList.size(); i++) {
            String monitorsListAll = webList.get(i).getText().replaceAll(" ", "");
            if (i == webList.size()) {
                throw new Exception("There is no such monitor");
            }
            if (Integer.parseInt(price) > Integer.parseInt(monitorsListAll)) {
                List<WebElement> monitorsListFiltered = goodsPicture;
                monitorsListFiltered.get(i).click();
                break;
            }
        }
    }
}
