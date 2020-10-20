package pagesGoogle;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class GoogleSearchPageObject {

    WebDriver webDriver;

    public GoogleSearchPageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private By searchInput = By.xpath("//input[@name='q']");

    public void performSearchRequest(String text) {
        webDriver.findElement(searchInput).sendKeys(text + Keys.ENTER);
    }
}
