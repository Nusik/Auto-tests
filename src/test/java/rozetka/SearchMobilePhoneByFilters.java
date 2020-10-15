package rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class SearchMobilePhoneByFilters extends BaseTestRozetka {

    String url = "https://rozetka.com.ua/";
    String searchText = "samsung";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(url);
    }

    @Test
    public void searchMobileByManufacturerTest() {
        driver.manage().window().maximize();
        driver.findElement(By.name("search")).sendKeys(searchText + Keys.ENTER);

        By mobilesLink = By.xpath("//a[@class='categories-filter__link'][contains(@href,'mobile-phones/c80003/producer=samsung')]//span[contains(text(),'Мобильные телефоны')]");
        wait.until(presenceOfElementLocated(mobilesLink));
        WebElement selectMobileLink = driver.findElement(mobilesLink);
        selectMobileLink.click();

        wait.until(presenceOfElementLocated(By.xpath("//div[@class='sidebar-block__inner']")));
        scrollToElement(driver.findElement(By.className("sidebar-alphabet")));
        wait.until(presenceOfElementLocated(By.className("custom-checkbox")));

        WebElement tickApple = driver.findElement(By.xpath("//a[@label[contains('Apple')]]"));
        if (!tickApple.isSelected()) {
            tickApple.click();
        }
        WebElement tickHonor = driver.findElement(By.id("Honor"));
        if (!tickHonor.isSelected()) {
            tickHonor.click();
        }

        List<WebElement> phoneNames = driver.findElements(By.xpath("//*[contains(@class,'goods-tile__price-value')]"));
        List<String> namesList = new ArrayList<>();
        for (WebElement element : phoneNames) {
            namesList.add(element.getText());
            if (namesList.contains("Samsung") || namesList.contains("Apple") || namesList.contains("Honor"))
                System.out.println("Mobile phones on the page contains at least one of manufacturer: Samsung, Apple, Honor");
            else {
                System.out.println("No mobile phones on the page with manufacturer: Samsung, Apple, Honor");
            }
        }
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
