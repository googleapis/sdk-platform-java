/*
 * Copyright 2024 Google LLC
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google LLC nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
