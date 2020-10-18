package guru99;

import SeleniumTests.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Set;

import static org.testng.Assert.assertEquals;
//Write automation test which handle cookies: Login to http://demo.guru99.com/Agile_Project/Agi_V1/index.php
// Print out all cookies (all data); - Clear all cookies; - Refresh page, verify session of authorization still exists

public class CookiesTest extends BaseUiTest {

    String loginUrl = "http://demo.guru99.com/Agile_Project/Agi_V1/index.php";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(loginUrl);
    }

    @Test
    public void printAndClearCookies() {
        loginToSite();
        try {
            Set<Cookie> cookies = driver.manage().getCookies();
            System.out.println("Size: " + cookies.size());

            Iterator<Cookie> cookieIterator = cookies.iterator();
            while (cookieIterator.hasNext()) {
                Cookie cookie = cookieIterator.next();
                System.out.println("Name: " + cookie.getName() + ", Value: " + cookie.getValue()
                        + ", Domain: " + cookie.getDomain() + ", Path: " + cookie.getPath()
                        + ", Expiry: " + cookie.getExpiry() + ", isSecured: " + cookie.isSecure()
                        + ", isHttpOnly: " + cookie.isHttpOnly());

                driver.manage().deleteCookie(cookie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.navigate().refresh();
        assertEquals(driver.findElement(By.linkText("Log out")).getText(), "Log out", "Logout button isn't present");
    }

    private void loginToSite() {
        String login = "1303";
        String password = "Guru99";
        driver.findElement(By.name("uid")).sendKeys(login);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@name='btnLogin']")).click();
    }
}