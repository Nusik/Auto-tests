package UI.TestNgDemoTest;

import TestNgDemoClasses.Calculator;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;




public class CalculatorTest extends BaseTest {

  Calculator calculator = new Calculator();

  @Test
  public void test1Sum() {
    assertEquals(calculator.sum(2, 6), 8);
  }

//  @Parameters({ "one"})
//  @Test
//  public void test2Sum(String p1) {
//    System.out.println("p1" + p1);
//    assertEquals(calculator.sum(2343, 0), 2343);
//  }

  @Test
  public void test3Sum() {
    assertEquals(calculator.sum(-4, 55), 51);
  }

  @Test(description = "Verifiaction of miltiplication of 2 numbers",
  dependsOnMethods = {"test3Sum"})
  public void test1Multiply() {
    assertEquals(calculator.multiply(3, 7), 21);
  }

  @Test(dependsOnMethods = {"test1Multiply"})
  public void test2Multiply() {
    assertEquals(calculator.multiply(0, 100), 0);
  }

}