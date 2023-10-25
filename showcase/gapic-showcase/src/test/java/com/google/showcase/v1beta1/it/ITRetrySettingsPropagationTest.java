package com.google.showcase.v1beta1.it;

import static org.junit.Assert.assertEquals;

import com.google.api.gax.retrying.RetrySettings;
import org.junit.Test;

/**
 * Tests to confirm that usage of retry settings can be done regardless of whether threeten or
 * java.time is being used
 */
public class ITRetrySettingsPropagationTest {
  @Test
  public void testRetrySettings_fromJavaTimeHasEquivalentThreetenValues() {
    java.time.Duration javaTimeCommonValue = java.time.Duration.ofMillis(123l);
    org.threeten.bp.Duration threetenConvertedValue =
        org.threeten.bp.Duration.ofMillis(javaTimeCommonValue.toMillis());
    RetrySettings javaTimeRetrySettings = RetrySettings.newBuilder()
        .setInitialRetryDelay(javaTimeCommonValue)
        .setMaxRetryDelay(javaTimeCommonValue)
        .setInitialRpcTimeout(javaTimeCommonValue)
        .setMaxRpcTimeout(javaTimeCommonValue)
        .setTotalTimeout(javaTimeCommonValue)
        .build();

    assertEquals(threetenConvertedValue, javaTimeRetrySettings.getInitialRetryDelay());
    assertEquals(threetenConvertedValue, javaTimeRetrySettings.getMaxRetryDelay());
    assertEquals(threetenConvertedValue, javaTimeRetrySettings.getInitialRpcTimeout());
    assertEquals(threetenConvertedValue, javaTimeRetrySettings.getMaxRpcTimeout());
    assertEquals(threetenConvertedValue, javaTimeRetrySettings.getTotalTimeout());
  }

  @Test
  public void testRetrySettings_fromThreetenHasEquivalentJavaTimeValues() {
    java.time.Duration threetenCommonValue = java.time.Duration.ofMillis(123l);
    org.threeten.bp.Duration javaTimeConvertedValue =
        org.threeten.bp.Duration.ofMillis(threetenCommonValue.toMillis());
    RetrySettings threetenRetrySettings = RetrySettings.newBuilder()
        .setInitialRetryDelay(threetenCommonValue)
        .setMaxRetryDelay(threetenCommonValue)
        .setInitialRpcTimeout(threetenCommonValue)
        .setMaxRpcTimeout(threetenCommonValue)
        .setTotalTimeout(threetenCommonValue)
        .build();

    assertEquals(javaTimeConvertedValue, threetenRetrySettings.getInitialRetryDelay());
    assertEquals(javaTimeConvertedValue, threetenRetrySettings.getMaxRetryDelay());
    assertEquals(javaTimeConvertedValue, threetenRetrySettings.getInitialRpcTimeout());
    assertEquals(javaTimeConvertedValue, threetenRetrySettings.getMaxRpcTimeout());
    assertEquals(javaTimeConvertedValue, threetenRetrySettings.getTotalTimeout());
  }

}
