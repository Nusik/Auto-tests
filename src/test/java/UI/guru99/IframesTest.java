package UI.guru99;

import UI.SeleniumTests.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

//3. Write automation test which works with iframes
//- Navigate to http://demo.guru99.com/Agile_Project/Agi_V1/index.php
//- Find iframe with id=primis_playerSekindoSPlayer...
//- Click on play button
//- While video is playing move cursos in and out iframe
//- Verify that when cursor is hovering iframe then stop button is visible, when cursor is not hovering - non visible

public class IframesTest extends BaseUiTest {

    @Test
    public void positivePlayIframe() throws AWTException, InterruptedException {
        driver.get("http://demo.guru99.com/Agile_Project/Agi_V1/index.php");

        Robot robot = new Robot();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        WebElement iframe = driver.findElement(By.xpath("//div[@class='primisslate']/div/div[2]"));
        By playButton = By.xpath("//div[@id='layoutContainerDiv']/div/div[5]/div");
        iframe.findElement(playButton).click();

        Point currentMousePosition = MouseInfo.getPointerInfo().getLocation();
        Point tempMousePosition = (currentMousePosition.x > 0) ? new Point(currentMousePosition.x - 60, currentMousePosition.y)
                : new Point(currentMousePosition.x + 60, currentMousePosition.y);

        By pauseButton = By.xpath("//div[@id='layoutContainerDiv']/div/div[5]/div[2]");
        robot.mouseMove(tempMousePosition.x, tempMousePosition.y);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pauseButton));
        robot.delay(4000);

        assertFalse(driver.findElement(pauseButton).isDisplayed(), "Pause button is visible");

        robot.mouseMove(currentMousePosition.x, currentMousePosition.y);
        wait.until(ExpectedConditions.presenceOfElementLocated(pauseButton));
        robot.delay(4000);

        assertTrue(driver.findElement(pauseButton).isDisplayed(), "Pause button isn't visible");

        robot.mouseMove(tempMousePosition.x, tempMousePosition.y);

        assertFalse(driver.findElement(pauseButton).isDisplayed(), "Pause button is visible");
    }
}