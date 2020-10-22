package pagesRozetka.RozetkaFactoryPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class ProductPageFactory {

    WebDriver webDriver;
    WebDriverWait wait;

    public ProductPageFactory(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, 6);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(css = "span.header-actions__button-counter")
    private WebElement compareCounter;
    @FindBy(xpath = "//button[@class='header-actions__button header-actions__button_type_compare header-actions__button_state_active']")
    private WebElement compareButtonHeader;
    @FindBy(xpath = "//*[@class='compare-button']")
    private WebElement compareButtonProductPage;
    @FindBy(className = "product__title")
    private WebElement productName;
    @FindBy(css = "p.product-prices__big.product-prices__big_color_red")
    private WebElement productPrice;

    public void waitCompareButtonAppear() {
        wait.until(ExpectedConditions.elementToBeClickable(compareButtonProductPage));
    }

    public void addProductToComparison() {
        compareButtonProductPage.click();
    }

    public void waitCompareCounter() {
        wait.until(ExpectedConditions.visibilityOf(compareCounter));
    }

    public void waitUpdatedCompareCounterAppear() {
        wait.until(ExpectedConditions.not(textToBePresentInElement(compareCounter, "1")));
    }

    public String getCompareCounter() {
        return compareButtonHeader.getText();
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getProductPrice() {
        return productPrice.getText();
    }

    public void clickCompareInHeader() {
        compareButtonHeader.click();
    }
}
