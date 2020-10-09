package TestNgDemoClasses;

import TestNgDemoClasses.utils.PropertiesReaderClassLoader;

public class PropertiesUsage {

  public static void main(String[] args) {
    String timeoutValue = PropertiesReaderClassLoader.getInstance().getValueFromProperty("defaultTimeout");
    String baseUrlValue = PropertiesReaderClassLoader.getInstance().getValueFromProperty("baseUrl");

    System.out.println("timeoutValue" + timeoutValue);
    System.out.println("baseUrlValue" + baseUrlValue);
  }

}
