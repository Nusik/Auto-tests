package UI.google;

import UI.SeleniumTests.BaseUiTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pagesGoogle.GoogleSearchPageObject;
import pagesGoogle.GoogleSearchResultPageObject;

public class GoogleSearchPageObjectTest extends BaseUiTest {

    String url = "https://google.com/ncr";
    String textForSearchResult = "iphone kyiv buy";
    String expectedText = "stylus.ua";
    int maxPageNumber = 5;

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(url);
    }

    @Test
    public void searchLinkInResultCorrectTest() throws Exception {
        GoogleSearchPageObject googleSearchPageObject = new GoogleSearchPageObject(driver);
        GoogleSearchResultPageObject googleSearchResultPageObject = new GoogleSearchResultPageObject(driver);

        googleSearchPageObject.performSearchRequest(textForSearchResult);
        googleSearchResultPageObject.waitForStatisticsAppear();
        googleSearchResultPageObject.findTextOnFirstNPages(maxPageNumber, expectedText);
    }
}