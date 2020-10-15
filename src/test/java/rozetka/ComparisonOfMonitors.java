package rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SourceType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertEquals;

public class ComparisonOfMonitors extends BaseTestRozetka {

    String url = "https://rozetka.com.ua/";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(url);
    }

    @Test
    public void comparisonOfMonitorsTest() throws InterruptedException {
        driver.manage().window().maximize();
        Actions action = new Actions(driver);

        By pcAndNotebooksMenu = By.xpath("//a[contains(@href, 'computers-notebooks')][@class='menu-categories__link']");
        WebElement pcAndNotebooksShowMenu = driver.findElement(pcAndNotebooksMenu);
        action.moveToElement(pcAndNotebooksShowMenu).perform();

        By monitorElement = By.xpath("//a[@class='menu__link'][contains(@href,'monitors/c80089')][contains(text(),'Мониторы')]");
        wait.until(presenceOfElementLocated(monitorElement));
        WebElement selectElement = driver.findElement(monitorElement);
        Thread.sleep(7000);
        selectElement.click();


//        System.out.println(driver.findElements(By.xpath("//*[contains(@class,'goods-tile__price-value')]")).size());
//        assertEquals(driver.findElements(By.xpath("//*[contains(@class,'goods-tile__price-value')]")).size(), 60);
//   scrollToElement(driver.findElement(By.cssSelector("button.sidebar-block__toggle > span.sidebar-block__toggle-title  span[text='Цена']")));


//        scrollToElement(driver.findElement(By.xpath("//*[contains(@class='sidebar-block__toggle-title')][contains(text(),'Цена']")));
//        WebElement enterMaxPrice = driver.findElement(By.xpath("//*[contains(@class='slider-filter__input')][@formcontrolname='max']"));
//        enterMaxPrice.sendKeys("3000");
//        WebElement pressOkPrice = driver.findElement(By.xpath("//aside//div//button[contains(@class='button_size_small')][@type='submit']"));
//        pressOkPrice.click();
//        wait.until(presenceOfElementLocated(By.cssSelector("div.goods-tile__prices")));

        wait.until(presenceOfElementLocated(By.cssSelector("div.goods-tile__prices")));
        List<WebElement> monitorPrice = driver.findElements(By.xpath("//*[contains(@class,'goods-tile__price-value')]"));
        WebElement monitorLessPrice = monitorPrice
                .stream()
                .filter(e -> Integer.parseInt(e.getText().replaceAll("[^0-9]", "")) < 3000)
                .findFirst()
                .get();

        List<Integer> prices = new ArrayList<>();
        for (WebElement element : monitorPrice) {
            prices.add(Integer.parseInt(element.getText().replaceAll("[^0-9]", "")));
        }
        int num = IntStream
                .range(0, prices.size())
                .filter(e -> e < 3000)
                .findFirst()
                .getAsInt();

        Integer[] arr = prices.toArray()[];
        ArrayList<Integer> in = IntStream
                .range(0, prices.toArray().length)
                .filter(e -> e < 3000)
                .mapToObj(index -> index + ":" + prices[index])
                .collect(Collectors.toList());

        monitorLessPrice.findElement(By.xpath("(//*[@class='goods-tile__picture'])[" + num + "]")).click();


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


