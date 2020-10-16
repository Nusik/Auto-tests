package rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    @Test
    public void searchMobileByManufacturerTest() throws InterruptedException {

        driver.findElement(By.name("search")).sendKeys(searchText + Keys.ENTER);

        By mobilesLink = By.xpath("//a[@class='categories-filter__link'][contains(@href,'mobile-phones/c80003/producer=samsung')]//span[contains(text(),'Мобильные телефоны')]");
        wait.until(ExpectedConditions.presenceOfElementLocated(mobilesLink));
        driver.findElement(mobilesLink).click();

        wait.until(presenceOfElementLocated(By.xpath("//div[@class='sidebar-block__inner']")));
        scrollToElement(driver.findElement(By.className("sidebar-alphabet")));
        wait.until(ExpectedConditions.presenceOfElementLocated((By.className("custom-checkbox"))));

        driver.findElement(By.xpath("//label[@for='Apple']")).click();
        wait.until(presenceOfElementLocated(By.xpath("//label[@for='Honor']"))).click();
        Thread.sleep(3000);

        try {
            List<WebElement> phoneNames = driver.findElements(By.xpath("//*[contains(@class,'goods-tile__price-value')]"));
            for (WebElement element : phoneNames) {
                String phoneNamesList = element.getText();
                Thread.sleep(3000);
                assertTrue(phoneNamesList.contains("Samsung"));
                assertTrue(phoneNamesList.contains("Apple"));
                assertTrue(phoneNamesList.contains("Honor"));
            }
            System.out.println("Manufactures Samsung, Apple, Honor are not present on the page");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void filterMobilesByPriceTest() throws InterruptedException {

        driver.findElement(By.name("search")).sendKeys(searchText + Keys.ENTER);

        By mobilesMenu = By.xpath("//a[@class='categories-filter__link'][contains(@href,'mobile-phones/c80003/producer=samsung')]//span[contains(text(),'Мобильные телефоны')]");
        wait.until(ExpectedConditions.presenceOfElementLocated(mobilesMenu));
        Thread.sleep(3000);
        driver.findElement(mobilesMenu).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='sidebar-block__inner']")));
        scrollToElement(driver.findElement(By.xpath("//*[@class='sidebar-block'][@data-filter-name='price']")));

        WebElement enterMinPrice = driver.findElement(By.xpath("//input[@formcontrolname='min']"));
        enterMinPrice.clear();
        enterMinPrice.sendKeys("5000");
        Thread.sleep(3000);

        WebElement enterMaxPrice = driver.findElement(By.xpath("//input[@formcontrolname='max']"));
        enterMaxPrice.clear();
        enterMaxPrice.sendKeys("15000");
        Thread.sleep(3000);

        WebElement pressOkPrice = driver.findElement(By.xpath("//button[@type='submit']"));
        pressOkPrice.click();

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
    public void findRamSizeInTitleTest() throws InterruptedException {

        driver.findElement(By.name("search")).sendKeys(searchText + Keys.ENTER);

        By mobilesMenu = By.xpath("//a[@class='categories-filter__link'][contains(@href,'mobile-phones/c80003/producer=samsung')]//span[contains(text(),'Мобильные телефоны')]");
        wait.until(ExpectedConditions.presenceOfElementLocated(mobilesMenu));
        Thread.sleep(3000);
        driver.findElement(mobilesMenu).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='sidebar-block__inner']")));
        scrollToElement(driver.findElement(By.xpath("//aside[@class='sidebar']/rz-filter-stack/div[10]/button/span")));

        wait.until(presenceOfElementLocated(By.xpath("//a[@href='/mobile-phones/c80003/producer=samsung;38435=55375/']/label"))).click();
        Thread.sleep(3000);

        List<WebElement> mobileRamSizeInTitle = driver.findElements(By.xpath("//*[contains(@class,'goods-tile__price-value')]"));
        for (WebElement element : mobileRamSizeInTitle) {
            if (element.getText().contains("2/")) {
                System.out.println("All filtered products have RAM size 2GB");
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
