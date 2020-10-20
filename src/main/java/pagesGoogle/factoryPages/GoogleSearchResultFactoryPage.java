package pagesGoogle.factoryPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class GoogleSearchResultFactoryPage {
    WebDriver webDriver;
    WebDriverWait wait;

    public GoogleSearchResultFactoryPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, 5);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(css = "#result-stats")
    private WebElement statistics;
    @FindBy(css = "div cite")
    private List <WebElement> searchResultLinks;
    @FindBy(css = "#pnnext")
    private WebElement nextButton;

    public void waitForStatisticsAppear() {
        wait.until(visibilityOf(statistics));
    }

    private List<WebElement> getAllLinksFromSearchResult() {
        return searchResultLinks;
    }

    private void clickOnNextPageButton() {
        nextButton.click();
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
