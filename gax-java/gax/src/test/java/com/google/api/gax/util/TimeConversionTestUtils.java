package com.google.api.gax.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.Assert;

public class TimeConversionTestUtils {

  public static void testDurationGetterAndSetter(
      org.threeten.bp.Duration inputValue,
      Object target,
      Method setter,
      Method buildFunction,
      Method getter) {
    testBuilderDurationSetter(inputValue, target, setter, buildFunction, getter);
  }

  public static void testDurationGetterAndSetter(
      org.threeten.bp.Duration inputValue, Object target, Method setter, Method getter) {
    testBuilderDurationSetter(inputValue, target, setter, null, getter);
  }

  public static void testDurationGetterAndSetter(
      java.time.Duration inputValue,
      Object target,
      Method setter,
      Method buildFunction,
      Method getter) {
    testBuilderDurationSetter(inputValue, target, setter, buildFunction, getter);
  }

  public static void testDurationGetterAndSetter(
      java.time.Duration inputValue, Object target, Method setter, Method getter) {
    testBuilderDurationSetter(inputValue, target, setter, null, getter);
  }

  private static void testBuilderDurationSetter(
      Object inputValue, Object target, Method setter, Method buildFunction, Method getter) {
    try {
      Object targetSet = setter.invoke(target, inputValue);
      Object obtainedValue;
      if (buildFunction != null) {
        Object builtTarget = buildFunction.invoke(targetSet);
        obtainedValue = getter.invoke(builtTarget);
      } else {
        obtainedValue = getter.invoke(targetSet);
      }
      long obtainedNanos = getNanosFromAnyDuration(obtainedValue);
      long providedNanos = getNanosFromAnyDuration(inputValue);
      Assert.assertEquals(providedNanos, obtainedNanos);
    } catch (IllegalAccessException e) {
      Assert.fail(e.getMessage());
    } catch (InvocationTargetException e) {
      Assert.fail(e.getMessage());
    }
  }

  private static long getNanosFromAnyDuration(Object value) {
    if (value instanceof java.time.Duration) {
      return ((java.time.Duration) value).toMillis();
    } else if (value instanceof org.threeten.bp.Duration) {
      return ((org.threeten.bp.Duration) value).toMillis();
    } else {
      throw new IllegalArgumentException("Unexpected value type: " + value.getClass().getName());
    }
  }
}
