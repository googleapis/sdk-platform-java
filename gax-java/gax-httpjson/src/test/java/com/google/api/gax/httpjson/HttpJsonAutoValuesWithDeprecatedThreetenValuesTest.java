package com.google.api.gax.httpjson;

import com.google.api.gax.AutoValuesWithDeprecatedThreetenFieldsTest;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class HttpJsonAutoValuesWithDeprecatedThreetenValuesTest {

  @Test
  public void testAutoValueClassesWithThreetenMethods()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    new AutoValuesWithDeprecatedThreetenFieldsTest().testAutoValueClassesWithThreetenMethods();
  }
}
