package pagesRozetka.RozetkaFactoryPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ComparisonFactoryPage {

    WebDriver webDriver;
    WebDriverWait wait;

    public ComparisonFactoryPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, 5);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(className = "comparison-modal__link")
    private WebElement comparisonModal;
    @FindBy(css = "li.products-grid__cell")
    private List<WebElement> productCellInComparisonPage;
    @FindBy(css = "div.product__price.product__price--red")
    private List<WebElement> productPriceInComparisonPage;
    @FindBy(className = "product__heading")
    private List<WebElement> productNameInComparisonPage;

    public void waitComparisonModalAppear() {
        wait.until(ExpectedConditions.elementToBeClickable(comparisonModal));
    }

    public void clickOnCompareProductInModal() {
        comparisonModal.click();
    }

    public void waitProductAppear() {
        wait.until(ExpectedConditions.visibilityOfAllElements(productCellInComparisonPage));
    }

    public List<WebElement> getProductsList() {
        return productCellInComparisonPage;
    }

    public String getProductName(int i) {
        return productNameInComparisonPage.get(i).getText();
    }

    public String getProductPrice(int i) {
        String price = productPriceInComparisonPage.get(i).getText().replaceAll("\\s+", "");
        String[] priceSplit = price.split("₴");
        return priceSplit[1] + "₴";
    }
}
