package lesson7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestClass {
  private static Method suite = null;

  public static void start(Class vClass)
  {
    Map<Method,Integer> tests = new HashMap<>();

    // Выбираем из класса-теста все методы с аннотацией @Test
    for (Method m: vClass.getDeclaredMethods())
    {
      if (m.isAnnotationPresent(Test.class))
      {
        Test a = (Test)m.getAnnotation(Test.class);
        tests.put(m,a.priority());
      }
    }

    // Выбираем из класса-теста метод @BeforeSuite и, если он есть, запускаем его.
    // Если таких методов больше одного, бросаем исключение RuntimeException
    int beforeCount = 0;
    for (Method m: vClass.getDeclaredMethods())
    {
      if (m.isAnnotationPresent(BeforeSuite.class))
      {
        suite = m;
        beforeCount++;
      }
    }
    if (beforeCount > 1) throw new RuntimeException();
    else if (beforeCount == 1) {
      try {
        suite.invoke(vClass.newInstance());
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (InstantiationException e) {
        e.printStackTrace();
      }
    }

    // Для всех выбранных методов @Test находим список аргуентов и запускаем их
    // в порядке возрастания приоритета.
    // Поскольку в условии не сказано, с какими параметрами запускать методы,
    // будем считать, что методы без параметров
    for (int i = 1; i < 11; i++) {
      for (Method m : tests.keySet()) {
        try {
          if (tests.get(m) == i)
          {
            m.invoke(vClass.newInstance());
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        } catch (InstantiationException e) {
          e.printStackTrace();
        }
      }
    }

    // Выбираем из класса-теста метод @AfterSuite и, если он есть, запускаем его.
    // Если таких методов больше одного, бросаем исключение RuntimeException
    int afterCount = 0;
    for (Method m: vClass.getDeclaredMethods())
    {
      if (m.isAnnotationPresent(AfterSuite.class))
      {
        suite = m;
        afterCount++;
      }
    }
    if (afterCount > 1) throw new RuntimeException();
    else if (afterCount == 1) {
      try {
        suite.invoke(vClass.newInstance());
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (InstantiationException e) {
        e.printStackTrace();
      }
    }
  }
}
