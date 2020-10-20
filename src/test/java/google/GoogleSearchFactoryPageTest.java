package google;

import SeleniumTests.BaseUiTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pagesGoogle.factoryPages.GoogleSearchFactoryPage;
import pagesGoogle.factoryPages.GoogleSearchResultFactoryPage;

public class GoogleSearchFactoryPageTest extends BaseUiTest {

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
        GoogleSearchFactoryPage googleSearchPageObject = new GoogleSearchFactoryPage(driver);
        GoogleSearchResultFactoryPage googleSearchResultPageObject = new GoogleSearchResultFactoryPage(driver);

        googleSearchPageObject.performSearchRequest(textForSearchResult);
        googleSearchResultPageObject.waitForStatisticsAppear();
        googleSearchResultPageObject.findTextOnFirstNPages(maxPageNumber, expectedText);
    }
}