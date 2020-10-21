package pagesRozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPageObject {

    WebDriver webDriver;
    WebDriverWait wait;

    public MainPageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, 4);
    }

    By searchField = By.name("search");
    By mobilesMenu = By.xpath("//a[@class='categories-filter__link'][contains(@href,'mobile-phones/c80003/producer=samsung')]//span[contains(text(),'Мобильные телефоны')]");
    By pcAndNotebooksMenu = By.xpath("//a[contains(@href, 'computers-notebooks')][@class='menu-categories__link']");
    By monitorMenu = By.xpath("//a[@class='menu__link'][contains(@href,'monitors/c80089')][contains(text(),'Мониторы')]");

    public void openMenuMobilesBySearchText(String text) {

        webDriver.findElement(searchField).sendKeys(text + Keys.ENTER);
        WebElement phones = (new WebDriverWait(webDriver, 4))
                .until(ExpectedConditions.elementToBeClickable(mobilesMenu));
        phones.click();
    }

    public void hoverMenu() {
        Actions action = new Actions(webDriver);
        WebElement pcAndNotebooksShowMenu = webDriver.findElement(pcAndNotebooksMenu);
        action.moveToElement(pcAndNotebooksShowMenu).perform();
    }

    public void openSubMenuMonitors() {
        wait.until(ExpectedConditions.elementToBeClickable(monitorMenu)).click();
    }
}
