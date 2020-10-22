package pagesRozetka.RozetkaFactoryPages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.By.name;

public class MainPageFactory {

    WebDriver webDriver;
    WebDriverWait wait;

    public MainPageFactory(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, 4);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(name = "search")
    private WebElement searchField;
    @FindBy(xpath = "//a[@class='categories-filter__link'][contains(@href,'mobile-phones/c80003/producer=samsung')]//span[contains(text(),'Мобильные телефоны')]")
    private WebElement mobilesMenu;
    @FindBy (xpath = "//a[contains(@href, 'computers-notebooks')][@class='menu-categories__link']")
    private WebElement pcAndNotebooksMenu;
    @FindBy(xpath = "//a[@class='menu__link'][contains(@href,'monitors/c80089')][contains(text(),'Мониторы')]")
    private WebElement monitorMenu;

    public void openMenuMobilesBySearchText(String text) {

        searchField.sendKeys(text + Keys.ENTER);
        WebElement phones = (new WebDriverWait(webDriver, 4))
                .until(ExpectedConditions.elementToBeClickable(mobilesMenu));
        phones.click();
    }

    public void hoverMenu() {
        Actions action = new Actions(webDriver);
        WebElement pcAndNotebooksShowMenu = pcAndNotebooksMenu;
        action.moveToElement(pcAndNotebooksShowMenu).perform();
    }

    public void openSubMenuMonitors() {
        wait.until(ExpectedConditions.elementToBeClickable(monitorMenu)).click();
    }
}
