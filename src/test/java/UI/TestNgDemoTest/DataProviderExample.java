package UI.TestNgDemoTest;

import UI.TestNgDemoTest.dataProviders.DataProviderSource;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderExample {

  @Test(dataProvider="getData")
  public void instanceDbProvider(int p1, String p2) {
    System.out.println("Instance DataProvider Example: Data(" + p1 + ", " + p2 + ")");
  }

  @DataProvider
  public Object[][] getData() {
    return new Object[][]{
        {5, "five"},
        {6, "six"},
        {44, "sefse"}
    };
  }

  @Test(dataProvider="client1", dataProviderClass= DataProviderSource.class)
  public void client1Test(int p, String r) {
    System.out.println(p + r);
  }



}
