package UI.TestNgDemoTest.dataProviders;

import org.testng.annotations.DataProvider;

public class DataProviderSource {

  @DataProvider
  public Object[][] client1() {
    return new Object[][]{
        {5, "five"},
        {6, "six"},
        {44, "sefse"}
    };
  }
}
