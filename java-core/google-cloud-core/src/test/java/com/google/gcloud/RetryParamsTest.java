/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.gcloud;

import static com.google.gcloud.RetryParams.DEFAULT_INITIAL_RETRY_DELAY_MILLIS;
import static com.google.gcloud.RetryParams.DEFAULT_MAX_RETRY_DELAY_MILLIS;
import static com.google.gcloud.RetryParams.DEFAULT_RETRY_DELAY_BACKOFF_FACTOR;
import static com.google.gcloud.RetryParams.DEFAULT_RETRY_MAX_ATTEMPTS;
import static com.google.gcloud.RetryParams.DEFAULT_RETRY_MIN_ATTEMPTS;
import static com.google.gcloud.RetryParams.DEFAULT_TOTAL_RETRY_PERIOD_MILLIS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.google.gcloud.RetryParams.Builder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

/**
 * Tests for {@link RetryParams}.
 */
@RunWith(JUnit4.class)
public class RetryParamsTest {

  @Test
  public void testDefaults() {
    RetryParams params1 = RetryParams.defaultInstance();
    RetryParams params2 = RetryParams.builder().build();
    for (RetryParams params : Arrays.asList(params1, params2)) {
      assertEquals(DEFAULT_INITIAL_RETRY_DELAY_MILLIS, params.initialRetryDelayMillis());
      assertEquals(DEFAULT_MAX_RETRY_DELAY_MILLIS, params.maxRetryDelayMillis());
      assertEquals(DEFAULT_RETRY_DELAY_BACKOFF_FACTOR, params.retryDelayBackoffFactor(), 0);
      assertEquals(DEFAULT_RETRY_MAX_ATTEMPTS, params.retryMaxAttempts());
      assertEquals(DEFAULT_RETRY_MIN_ATTEMPTS, params.retryMinAttempts());
      assertEquals(DEFAULT_TOTAL_RETRY_PERIOD_MILLIS, params.totalRetryPeriodMillis());
    }
  }

  @Test
  public void testSetAndCopy() {
    RetryParams.Builder builder = RetryParams.builder();
    builder.initialRetryDelayMillis(101);
    builder.maxRetryDelayMillis(102);
    builder.retryDelayBackoffFactor(103);
    builder.retryMinAttempts(107);
    builder.retryMaxAttempts(108);
    builder.totalRetryPeriodMillis(109);
    RetryParams params1 = builder.build();
    RetryParams params2 = new RetryParams.Builder(params1).build();
    for (RetryParams params : Arrays.asList(params1, params2)) {
      assertEquals(101, params.initialRetryDelayMillis());
      assertEquals(102, params.maxRetryDelayMillis());
      assertEquals(103, params.retryDelayBackoffFactor(), 0);
      assertEquals(107, params.retryMinAttempts());
      assertEquals(108, params.retryMaxAttempts());
      assertEquals(109, params.totalRetryPeriodMillis());
    }
  }

  @Test
  public void testBadSettings() {
    RetryParams.Builder builder = RetryParams.builder();
    builder.initialRetryDelayMillis(-1);
    builder = assertFailure(builder);
    builder.maxRetryDelayMillis(RetryParams.defaultInstance().initialRetryDelayMillis() - 1);
    builder = assertFailure(builder);
    builder.retryDelayBackoffFactor(-1);
    builder = assertFailure(builder);
    builder.retryMinAttempts(-1);
    builder = assertFailure(builder);
    builder.retryMaxAttempts(RetryParams.defaultInstance().retryMinAttempts() - 1);
    builder = assertFailure(builder);
    builder.totalRetryPeriodMillis(-1);
    builder = assertFailure(builder);
    // verify that it is OK for min and max to be equal
    builder.retryMaxAttempts(RetryParams.defaultInstance().retryMinAttempts());
    builder.maxRetryDelayMillis(RetryParams.defaultInstance().initialRetryDelayMillis());
    builder.build();
  }

  private static Builder assertFailure(Builder builder) {
    try {
      builder.build();
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      // expected
    }
    return RetryParams.builder();
  }
}
