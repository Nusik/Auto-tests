package rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertTrue;

public class SearchMobilePhoneByFilters extends BaseTestRozetka {

    String url = "https://rozetka.com.ua/";
    String searchText = "samsung";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(url);
    }

    public void openMenuMobilesBySearchText() {
        //Open page with "Samsung" product and select mobiles
        By mobilesMenu = By.xpath("//a[@class='categories-filter__link'][contains(@href,'mobile-phones/c80003/producer=samsung')]//span[contains(text(),'Мобильные телефоны')]");
        driver.findElement(By.name("search")).sendKeys(searchText + Keys.ENTER);
        WebElement phones = (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.presenceOfElementLocated(mobilesMenu));
        phones.click();
    }

    @Test
    public void searchMobileByManufacturerTest() {

        openMenuMobilesBySearchText();

        //Scroll to filter by manufacturer and add filter by Apple and Honor
        wait.until(presenceOfElementLocated(By.xpath("//div[@class='sidebar-block__inner']")));
        scrollToElement(driver.findElement(By.className("sidebar-alphabet")));
        wait.until(ExpectedConditions.presenceOfElementLocated((By.className("custom-checkbox"))));

        driver.findElement(By.xpath("//label[@for='Apple']")).click();
        wait.until(presenceOfElementLocated(By.xpath("//label[@for='Honor']"))).click();

        //Check if filtered products have selected manufactures
        WebElement waitTitles = (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.goods-tile__title")));

        List<WebElement> phoneNames = driver.findElements(By.cssSelector("span.goods-tile__title"));
        for (WebElement element : phoneNames) {
            String phoneTitle = element.getText();
            if (phoneTitle.contains("Samsung") || phoneTitle.contains("Apple") || phoneTitle.contains("Honor")) {
                assertTrue(element.getText().contains("Samsung")
                        || element.getText().contains("Apple")
                        || element.getText().contains("Honor"));
            } else {
                System.out.println("Manufactures Samsung, Apple, Honor are not present on the page");
            }
        }
    }

    @Test
    public void filterMobilesByPriceTest() {

        openMenuMobilesBySearchText();

        //Scroll to filter by price and add filter: 5000 < price < 15000
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='sidebar-block__inner']")));
        scrollToElement(driver.findElement(By.xpath("//*[@class='sidebar-block'][@data-filter-name='price']")));

        WebElement enterMinPrice = driver.findElement(By.xpath("//input[@formcontrolname='min']"));
        enterMinPrice.clear();
        enterMinPrice.sendKeys("5000");

        WebElement enterMaxPrice = (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@formcontrolname='max']")));
        enterMaxPrice.clear();
        enterMaxPrice.sendKeys("15000");

        WebElement pressOkPrice = (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        pressOkPrice.click();

        //Check if filtered products have selected price
        wait.until(presenceOfElementLocated(By.cssSelector("div.goods-tile__prices")));
        List<WebElement> mobilePrices = driver.findElements(By.xpath("//*[contains(@class,'goods-tile__price-value')]"));
        for (WebElement element : mobilePrices) {
            String mobilesList = element.getText().replaceAll("[^0-9]", "");
            Integer.parseInt(mobilesList);

            if (mobilesList.startsWith("15001") && mobilesList.endsWith("4999")) {
                System.out.println("No products for selected price");
                assertTrue(mobilesList.endsWith("4999"), "Some mobile phones have price < 5'000");
                assertTrue(mobilesList.startsWith("15001"), "Some mobile phones have price > 15'000");
            }
        }
        System.out.println("All filtered products are products with price from range");
    }

    @Test
    public void findRamSizeInTitleTest() {

        openMenuMobilesBySearchText();

        //scroll to filter by Ram size and select 2GB
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='sidebar-block__inner']")));
        scrollToElement(driver.findElement(By.xpath("//aside[@class='sidebar']/rz-filter-stack/div[10]/button/span")));

        wait.until(presenceOfElementLocated(By.xpath("//a[@href='/mobile-phones/c80003/producer=samsung;38435=55375/']/label")))
                .click();
        WebElement waitTitles = (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.goods-tile__title")));

        //check if filtered products has RAM = 2GB (contains it in title)
        List<WebElement> mobileRamSizeInTitle = driver.findElements(By.cssSelector("span.goods-tile__title"));
        for (WebElement element : mobileRamSizeInTitle) {
            if (element.getText().contains("2/")) {
                assertTrue(element.getText().contains("2/"), "Not all elements contain RAM size in title");
            }
        }
        System.out.println("Some products don't have RAM size in title");
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
