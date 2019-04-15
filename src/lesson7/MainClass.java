package lesson7;

public class MainClass {
  @Test(name = "first",description = "первый метод")
  public void methodFirst()
  {
    System.out.println("methodFirst");
  }

  @Test(name = "second",description = "второй метод",priority = 1)
  public void methodSecond()
  {
    System.out.println("methodSecond");
  }

  @Test(name = "third",description = "третий метод",priority = 10)
  public void methodThird()
  {
    System.out.println("methodThird");
  }

  @BeforeSuite
  public void methodBefore()
  {
    System.out.println("Method BeforeSuite");
  }

  @AfterSuite
  public void methodAfter()
  {
    System.out.println("Method AfterSuite");
  }

//  @BeforeSuite
//  public void methodBefore2()
//  {
//    System.out.println("Method BeforeSuite");
//  }

  public static void main(String[] args) {
    try {
      new TestClass().start(new MainClass().getClass());
    } catch (RuntimeException e)
    {
      e.printStackTrace();
    }
  }
}
