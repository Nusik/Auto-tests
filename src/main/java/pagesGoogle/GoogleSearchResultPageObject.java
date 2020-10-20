package pagesGoogle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class GoogleSearchResultPageObject {
    WebDriver webDriver;
    WebDriverWait wait;

    public GoogleSearchResultPageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, 5);
    }

    By statistics = By.cssSelector("#result-stats");
    private By searchResultLinks = By.cssSelector("div cite");
    private By nextButton = cssSelector("#pnnext");

    public void waitForStatisticsAppear() {
        wait.until(presenceOfElementLocated(statistics));
    }

    private List<WebElement> getAllLinksFromSearchResult() {
        return webDriver.findElements(searchResultLinks);
    }

    private void clickOnNextPageButton() {
        webDriver.findElement(nextButton).click();
    }

    public void findTextOnFirstNPages(int maxPageNumber, String expectedText) throws Exception {
        for (int i = 1; i <= maxPageNumber; i++) {
            List<WebElement> searchResults = getAllLinksFromSearchResult();
            for (WebElement element : searchResults) {
                if (element.getText().contains(expectedText)) {
                    System.out.println("[" + expectedText + "] was found on page " + i);
                    return;
                }
            }
            clickOnNextPageButton();
            if (maxPageNumber == i) {
                throw new Exception("[" + expectedText + "] was not found on " + maxPageNumber + "pagesGoogle");
            }
        }
    }
}
