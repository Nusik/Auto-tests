package pagesRozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class SidebarInResultPageObject {

    WebDriver webDriver;
    WebDriverWait wait;

    public SidebarInResultPageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, 5);
    }

    By sidebarShow = By.xpath("//div[@class='sidebar-block__inner']");

    //Price Section
    By sidebarPriceSection = By.xpath("//*[@class='sidebar-block'][@data-filter-name='price']");
    By minPrice = By.xpath("//input[@formcontrolname='min']");
    By maxPrice = By.xpath("//input[@formcontrolname='max']");
    By okButtonPriceSection = By.xpath("//button[@type='submit']");

    //Manufacturer Section
    By sidebarManufacturerName = By.className("sidebar-alphabet");
    By checkboxManufacturerSection = By.className("custom-checkbox");
    By manufacturerApple = By.xpath("//label[@for='Apple']");
    By manufacturerHuawei = By.xpath("//label[@for='Huawei']");
    By sidebarRamSize = By.xpath("//aside[@class='sidebar']/rz-filter-stack/div[10]/button/span");
    By checkboxRam2Gb = By.xpath("//a[@href='/mobile-phones/c80003/producer=samsung;38435=55375/']/label");

    public void waitSidebarAppear() {
        wait.until(presenceOfElementLocated(sidebarShow));
    }

    public void waitCheckboxManufacturerAppear() {
        wait.until(ExpectedConditions.presenceOfElementLocated((checkboxManufacturerSection)));
    }

    public void scrollToManufacturerNameSection() {
        scrollToElement(webDriver.findElement(sidebarManufacturerName));
    }

    public void tickAppleCheckbox() {
        webDriver.findElement(manufacturerApple).click();
    }

    public void tickHuaweiCheckbox() {
            wait.until(presenceOfElementLocated(manufacturerHuawei)).click();
    }

    public void scrollToRamSizeSection() {
        scrollToElement(webDriver.findElement(sidebarRamSize));
    }

    public void tickRam2Gb() {
        wait.until(presenceOfElementLocated(checkboxRam2Gb)).click();
    }

    public void scrollToPriceSection() {
        webDriver.findElement(sidebarPriceSection);
    }

    public void setMinPrice(int priceMin) {
        String priceString = String.valueOf(priceMin);
        WebElement enterMinPrice = webDriver.findElement(minPrice);
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
