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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.function.Function;
import java.util.function.Supplier;

public class TimeConversionTestUtils {

  public static <Target> void testInstantMethod(
      Long testValue,
      Supplier<Target> javaTimeTargetSupplier,
      Supplier<Target> threetenTargetSupplier,
      Function<Target, java.time.Instant> javaTimeGetter,
      Function<Target, org.threeten.bp.Instant> threetenGetter) {
    Function<java.time.Instant, Long> javaTimeTester = value -> value.toEpochMilli();
    Function<org.threeten.bp.Instant, Long> threetenTester = value -> value.toEpochMilli();
    testTimeObjectMethod(
        testValue,
        javaTimeTargetSupplier,
        javaTimeGetter,
        threetenGetter,
        javaTimeTester,
        threetenTester);
    testTimeObjectMethod(
        testValue,
        threetenTargetSupplier,
        javaTimeGetter,
        threetenGetter,
        javaTimeTester,
        threetenTester);
  }

  public static <Target> void testDurationMethod(
      Long testValue,
      Supplier<Target> javaTimeTargetSupplier,
      Supplier<Target> threetenTargetSupplier,
      Function<Target, java.time.Duration> javaTimeGetter,
      Function<Target, org.threeten.bp.Duration> threetenGetter) {
    Function<java.time.Duration, Long> javaTimeTester = value -> value.toMillis();
    Function<org.threeten.bp.Duration, Long> threetenTester = value -> value.toMillis();
    testTimeObjectMethod(
        testValue,
        javaTimeTargetSupplier,
        javaTimeGetter,
        threetenGetter,
        javaTimeTester,
        threetenTester);
    testTimeObjectMethod(
        testValue,
        threetenTargetSupplier,
        javaTimeGetter,
        threetenGetter,
        javaTimeTester,
        threetenTester);
  }

  private static <Target, Threeten, JavaTime> void testTimeObjectMethod(
      Long testValue,
      Supplier<Target> targetSupplier,
      Function<Target, JavaTime> javaTimeGetter,
      Function<Target, Threeten> threetenGetter,
      Function<JavaTime, Long> javaTimeTester,
      Function<Threeten, Long> threetenTester) {
    Target target = targetSupplier.get();
    JavaTime javaTimeValue = javaTimeGetter.apply(target);
    Threeten threetenValue = threetenGetter.apply(target);
    if (testValue == null) {
      assertNull(javaTimeValue);
      assertNull(threetenValue);
    } else {
      assertEquals(testValue.longValue(), javaTimeTester.apply(javaTimeValue).longValue());
      assertEquals(testValue.longValue(), threetenTester.apply(threetenValue).longValue());
    }
  }
}
