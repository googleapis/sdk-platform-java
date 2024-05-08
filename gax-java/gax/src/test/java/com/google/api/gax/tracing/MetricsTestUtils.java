package com.google.api.gax.tracing;

import static org.junit.Assert.fail;

import java.lang.reflect.Method;

public class MetricsTestUtils {
  public static void reportFailedAttempt(ApiTracer tracer, Exception ex, Object delayValue) {
    try {
      Method attemptFailed =
          tracer
              .getClass()
              .getDeclaredMethod("attemptFailed", Throwable.class, delayValue.getClass());
      attemptFailed.invoke(tracer, ex, delayValue);
    } catch (Exception e) {
      fail();
      throw new RuntimeException(e);
    }
  }
}
