package guru99;

import SeleniumTests.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class IfamesTest extends BaseUiTest {

    @Test
    public void positivePlayIframe() throws AWTException, InterruptedException {
        driver.get("http://demo.guru99.com/Agile_Project/Agi_V1/index.php");

        Robot robot = new Robot();

        By playButton = By.xpath("//div[@id='layoutContainerDiv']/div/div[5]/div");
        wait.until(ExpectedConditions.presenceOfElementLocated(playButton)).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        Point currentMousePosition = MouseInfo.getPointerInfo().getLocation();
        Point tempMousePosition = (currentMousePosition.x > 0) ? new Point(currentMousePosition.x - 60, currentMousePosition.y)
                : new Point(currentMousePosition.x + 60, currentMousePosition.y);

        By pauseButton = By.xpath("//div[@id='layoutContainerDiv']/div/div[5]/div[2]");
        robot.mouseMove(tempMousePosition.x, tempMousePosition.y);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pauseButton));
        assertFalse(driver.findElement(pauseButton).isDisplayed(), "Pause button is visible");
        robot.delay(4000);

        robot.mouseMove(currentMousePosition.x, currentMousePosition.y);
        wait.until(ExpectedConditions.presenceOfElementLocated(pauseButton));
        assertTrue(driver.findElement(pauseButton).isDisplayed(), "Pause button isn't visible");
        robot.delay(4000);

        robot.mouseMove(tempMousePosition.x, tempMousePosition.y);
        assertFalse(driver.findElement(pauseButton).isDisplayed(), "Pause button is visible");
        robot.delay(4000);

    }
}