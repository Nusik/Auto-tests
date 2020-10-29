package UI.guru99;

import UI.SeleniumTests.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
//4. Write automation test for drag&drop: Navigate to http://demo.guru99.com/test/drag_drop.html
//Drag and drop all possible webelements to their placeholders

public class DragAndDropTest extends BaseUiTest {

    @Test
    public void positiveDragAndDrop() {

        driver.get("http://demo.guru99.com/test/drag_drop.html");

        WebElement FromBankItem = driver.findElement(By.xpath("//*[@id='credit2']/a"));
        WebElement toDtAccount = driver.findElement(By.xpath("//*[@id='bank']/li"));
        WebElement FromSalesItem = driver.findElement(By.xpath("//*[@id='credit1']/a"));
        WebElement toCtAccount = driver.findElement(By.xpath("//*[@id='loan']/li"));
        WebElement FromAmount1 = driver.findElement(By.xpath("//*[@id='fourth']/a"));
        WebElement toDtAmount = driver.findElement(By.xpath("//*[@id='amt7']/li"));
        WebElement FromAmount2 = driver.findElement(By.xpath("//*[@id='fourth']/a"));
        WebElement toCtAmount = driver.findElement(By.xpath("//*[@id='amt8']/li"));

        Actions act = new Actions(driver);
        act.dragAndDrop(FromBankItem, toDtAccount).build().perform();
        act.dragAndDrop(FromSalesItem, toCtAccount).build().perform();
        act.dragAndDrop(FromAmount1, toDtAmount).build().perform();
        act.dragAndDrop(FromAmount2, toCtAmount).build().perform();

        assertTrue(driver.findElement(By.cssSelector("div.table4_result>a.button.button-green")).isDisplayed());
    }
}
