package UI.guru99;

import UI.SeleniumTests.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import static org.testng.Assert.assertTrue;

//1. Write automation test for mail.goolge.com:
//- Login to mail.UI.google.com with existing account;
//- Create new email (with TO=*the same email*, subject, email text), attach file from your local machine, click send;
//- Go to inbox, verify email came, verify subject, content of email, verify file is attached

public class GmailTest extends BaseUiTest {

    String loginUrl = "https://mail.google.com/";
    private String email = "testerjo91@gmail.com";
    private String password = "jotest_2020";
    public String textSubject = "Test message";
    public String textInMessage = "This is a text in a test message";

    @BeforeMethod
    public void navigateToUrl() {
        driver.get(loginUrl);
    }

    @Test
    public void positiveVerifyInboxEmail() throws InterruptedException, AWTException {

        By composeButton = By.xpath("//div[7]/div[3]/div/div[2]/div/div/div/div/div/div/div/div/div[@role='button']");
        By senderEmail = By.xpath("//tbody/tr[1]/td[4]/div[2]/span[1]/span[1][@email='testerjo91@gmail.com']");
        By subjectLastMessage = By.xpath("//tbody/tr[1]/td[5]/div[1]/div[1]/div[1]/span[1]/span[text()='" + textSubject + "'][1]");
        By textAreaInMessage = By.xpath("//div[@role='listitem']/div[1]/div/div/div/div/div[2]/div[3]/div[3]/div");
        By textInOpenedMessage = By.xpath("//div[@role='listitem']/div[1]/div/div/div/div/div[2]/div[3]/div[3]/div/div[1][contains(text(),'" + textInMessage + "')]");

        //login to mail
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys(email + Keys.ENTER);
        Thread.sleep(1000);     //smart waiter doesn't work here in this case
        WebElement passwordField = (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='password']")));
        passwordField.sendKeys(password + Keys.ENTER);

        //Create new message
        wait.until(ExpectedConditions.presenceOfElementLocated(composeButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@aria-label='New Message']")));
        driver.findElement(By.xpath("//textarea[@aria-label='To']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@name='subjectbox']")).sendKeys(textSubject);
        driver.findElement(By.xpath("//div[@aria-label='Message Body']")).sendKeys(textInMessage);
        driver.findElement(By.xpath("//div[@aria-label='Attach files']/div/div")).click();
        Thread.sleep(1000);   //smart waiter doesn't work here in this case
        attachFile();
        driver.findElement(By.xpath("//table[@role='group']/tbody/tr/td/div/div[2]/div[1][@role='button']")).click();

        //Check email in received message
        WebElement firstEmail = (new WebDriverWait(driver, 2))
                .until(ExpectedConditions.presenceOfElementLocated(senderEmail));
        firstEmail.isDisplayed();

        assertTrue(driver.findElement(subjectLastMessage).isDisplayed(), "No new messages with expected subject");

        //Open message and verify email, subject, text into it
        driver.findElement(By.xpath("//div[@role='tabpanel']/div[3]/div/table[1]/tbody/tr[1]")).click();
        WebElement message = (new WebDriverWait(driver, 2))
                .until(ExpectedConditions.presenceOfElementLocated(textAreaInMessage));

        assertTrue(driver.findElement(textInOpenedMessage).getText().contains(textInMessage), "Wrong text in message");
        assertTrue(driver.findElement(By.xpath("//tbody/tr[1]/td[2]/div[1]/span[1]/img[@alt='Attachments']")).isDisplayed(),
                "No attachments");
        assertTrue(driver.findElement(By.xpath("//h3/span[1]/span[@email='" + email + "']")).isDisplayed(), "Wrong e-mail");
    }

    public static void attachFile() throws AWTException {
        StringSelection attachment = new StringSelection("C:\\Users\\INNA\\Desktop\\Work\\test ddata\\husky.jpg");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(attachment, null);

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
}
