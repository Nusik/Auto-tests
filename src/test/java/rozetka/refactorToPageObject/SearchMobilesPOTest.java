package rozetka.refactorToPageObject;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pagesRozetka.MainPageObject;
import pagesRozetka.SearchResultPageObject;
import pagesRozetka.SidebarInResultPageObject;
import rozetka.BaseTestRozetka;

import static org.testng.Assert.assertTrue;

public class SearchMobilesPOTest extends BaseTestRozetka {

    String url = "https://rozetka.com.ua/";
    String searchText = "samsung";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(url);
    }

    @Test
    public void searchMobileByManufacturerTest() throws Exception {
        String productName1 = "Samsung";
        String productName2 = "Apple";
        String productName3 = "Huawei";

        MainPageObject mainPageObject = new MainPageObject(driver);
        SidebarInResultPageObject sidebarInResultPageObject = new SidebarInResultPageObject(driver);
        SearchResultPageObject searchResultPageObject = new SearchResultPageObject(driver);

        //Open page with "Samsung" product and select mobiles
        mainPageObject.openMenuMobilesBySearchText(searchText);

        //Scroll to filter by manufacturer and add filter by Apple
        sidebarInResultPageObject.waitSidebarAppear();
        sidebarInResultPageObject.scrollToManufacturerNameSection();
        sidebarInResultPageObject.waitCheckboxManufacturerAppear();
        sidebarInResultPageObject.tickAppleCheckbox();
        sidebarInResultPageObject.tickHuaweiCheckbox();

        //Check if filtered products have selected manufactures
        searchResultPageObject.waitProductsTitle();
        //searchResultPageObject.comparePhoneNamesWithSelected(productName1, productName2);
        assertTrue(searchResultPageObject.comparePhoneNamesWithSelected(productName1, productName2,productName3));
    }

    @Test
    public void filterMobilesByPriceTest() throws Exception {
        int maxProductPrice = 15000;
        int minProductPrice = 5000;

        MainPageObject mainPageObject = new MainPageObject(driver);
        SidebarInResultPageObject sidebarInResultPageObject = new SidebarInResultPageObject(driver);
        SearchResultPageObject searchResultPageObject = new SearchResultPageObject(driver);

        //Open page with "Samsung" product and select mobiles
        mainPageObject.openMenuMobilesBySearchText(searchText);

        //Scroll to filter by price and add filter: 5000 < price < 15000
        sidebarInResultPageObject.waitSidebarAppear();
        sidebarInResultPageObject.scrollToPriceSection();

        sidebarInResultPageObject.setMinPrice(minProductPrice);
        sidebarInResultPageObject.setMaxPrice(maxProductPrice);
        sidebarInResultPageObject.submitPriceSelection();

        //Check if filtered products have selected price
        searchResultPageObject.waitProductPrice();
        assertTrue(searchResultPageObject.compareProductPriceWithSelected(minProductPrice, maxProductPrice));
    }

    @Test
    public void findRamSizeInTitleTest() throws Exception {
        String ramSize = "2/";
        MainPageObject mainPageObject = new MainPageObject(driver);
        SidebarInResultPageObject sidebarInResultPageObject = new SidebarInResultPageObject(driver);
        SearchResultPageObject searchResultPageObject = new SearchResultPageObject(driver);

        //Open page with "Samsung" product and select mobiles
        mainPageObject.openMenuMobilesBySearchText(searchText);

        //scroll to filter by Ram size and select 2GB
        sidebarInResultPageObject.waitSidebarAppear();
        sidebarInResultPageObject.scrollToRamSizeSection();
        sidebarInResultPageObject.tickRam2Gb();

        //check if filtered products has RAM = 2GB (contains it in title)
        searchResultPageObject.waitProductsTitle();
        assertTrue(searchResultPageObject.findSelectedRamSizeInTitle(ramSize));
    }
}
