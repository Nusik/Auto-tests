package UI.TestNgDemoTest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

  @BeforeSuite
  public void beforeSuite(){
    System.out.println("Before suite");
  }

  @AfterSuite
  public void afterSuite(){
    System.out.println("After suite");
  }

  @BeforeClass
  public void beforeClass(){
    System.out.println("Before class");
  }

  @AfterClass
  public void afterClass(){
    System.out.println("After class");
  }

}
