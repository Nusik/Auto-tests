package pagesRozetka.RozetkaFactoryPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class SidebarInResultPageFactory {

    WebDriver webDriver;
    WebDriverWait wait;

    public SidebarInResultPageFactory(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, 6);
        PageFactory.initElements(webDriver, this);
    }

    By sidebarShow = By.xpath("//div[@class='sidebar-block__inner']");

    //Price Section
    @FindBy(xpath = "//*[@class='sidebar-block'][@data-filter-name='price']")
    private WebElement sidebarPriceSection;
    @FindBy(xpath = "//input[@formcontrolname='min']")
    private WebElement minPrice;
    @FindBy(xpath = "//input[@formcontrolname='max']")
    private WebElement maxPrice;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement okButtonPriceSection;

    //Manufacturer Section
    @FindBy(className = "sidebar-alphabet")
    private WebElement sidebarManufacturerName;
    @FindBy(className = "custom-checkbox")
    private WebElement checkboxManufacturerSection;
    @FindBy(xpath = "//label[@for='Apple']")
    private WebElement manufacturerApple;
    @FindBy(xpath = "//label[@for='Huawei']")
    private WebElement manufacturerHuawei;
    @FindBy(xpath = "//aside[@class='sidebar']/rz-filter-stack/div[10]/button/span")
    private WebElement sidebarRamSize;
    @FindBy(xpath = "//a[@href='/mobile-phones/c80003/producer=samsung;38435=55375/']/label")
    private WebElement checkboxRam2Gb;

    public void waitSidebarAppear() {
        wait.until(presenceOfElementLocated(sidebarShow));
    }

    public void waitCheckboxManufacturerAppear() {
        wait.until(ExpectedConditions.visibilityOf((checkboxManufacturerSection)));
    }

    public void scrollToManufacturerNameSection() {
        scrollToElement(sidebarManufacturerName);
    }

    public void tickAppleCheckbox() {
        manufacturerApple.click();
    }

    public void tickHuaweiCheckbox() {
        wait.until(ExpectedConditions.visibilityOf(manufacturerHuawei)).click();
    }

    public void scrollToRamSizeSection() {
        scrollToElement(sidebarRamSize);
    }

    public void tickRam2Gb() {
        wait.until(ExpectedConditions.visibilityOf(checkboxRam2Gb)).click();
    }

    public void scrollToPriceSection() {
        scrollToElement(sidebarPriceSection);
    }

    public void setMinPrice(int priceMin) {
        String priceString = String.valueOf(priceMin);
        WebElement enterMinPrice = minPrice;
        enterMinPrice.clear();
        enterMinPrice.sendKeys(priceString);
    }

    public void setMaxPrice(int priceMax) {
        String priceString = String.valueOf(priceMax);
        WebElement enterMaxPrice = wait.until(ExpectedConditions.elementToBeClickable(maxPrice));
        enterMaxPrice.clear();
        enterMaxPrice.sendKeys(priceString);
    }

    public void submitPriceSelection() {
        WebElement pressOkPrice = wait.until(ExpectedConditions.elementToBeClickable(okButtonPriceSection));
        pressOkPrice.click();
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
