/*
 * Copyright 2018 Google LLC
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
package com.google.api.gax.retrying;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.api.gax.core.FakeApiClock;
import com.google.api.gax.rpc.testing.FakeCallContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ExponentialRetryAlgorithmTest {
  private final FakeApiClock clock = new FakeApiClock(0L);
  private final RetrySettings retrySettings =
      RetrySettings.newBuilder()
          .setMaxAttempts(6)
          .setInitialRetryDelay(java.time.Duration.ofMillis(1L))
          .setRetryDelayMultiplier(2.0)
          .setMaxRetryDelay(java.time.Duration.ofMillis(8L))
          .setInitialRpcTimeout(java.time.Duration.ofMillis(1L))
          .setRpcTimeoutMultiplier(2.0)
          .setMaxRpcTimeout(java.time.Duration.ofMillis(8L))
          .setTotalTimeout(java.time.Duration.ofMillis(200L))
          .build();
  private final ExponentialRetryAlgorithm algorithm =
      new ExponentialRetryAlgorithm(retrySettings, clock);
  private final RetrySettings retrySettingsOverride =
      RetrySettings.newBuilder()
          .setMaxAttempts(3)
          .setInitialRetryDelay(java.time.Duration.ofMillis(2L))
          .setRetryDelayMultiplier(3.0)
          .setMaxRetryDelay(java.time.Duration.ofMillis(18L))
          .setInitialRpcTimeout(java.time.Duration.ofMillis(2L))
          .setRpcTimeoutMultiplier(3.0)
          .setMaxRpcTimeout(java.time.Duration.ofMillis(18L))
          .setTotalTimeout(java.time.Duration.ofMillis(300L))
          .build();
  private final RetryingContext retryingContext =
      FakeCallContext.createDefault().withRetrySettings(retrySettingsOverride);

  @Test
  public void testCreateFirstAttempt() {
    TimedAttemptSettings attempt = algorithm.createFirstAttempt();

    // Checking only the most core values, to not make this test too implementation specific.
    assertEquals(0, attempt.getAttemptCount());
    assertEquals(0, attempt.getOverallAttemptCount());
    assertEquals(java.time.Duration.ZERO, attempt.getRetryDelayDuration());
    assertEquals(java.time.Duration.ZERO, attempt.getRandomizedRetryDelayDuration());
    assertEquals(java.time.Duration.ofMillis(1L), attempt.getRpcTimeoutDuration());
    assertEquals(java.time.Duration.ZERO, attempt.getRetryDelayDuration());
  }

  @Test
  public void testCreateFirstAttemptOverride() {
    TimedAttemptSettings attempt = algorithm.createFirstAttempt(retryingContext);

    // Checking only the most core values, to not make this test too implementation specific.
    assertEquals(0, attempt.getAttemptCount());
    assertEquals(0, attempt.getOverallAttemptCount());
    assertEquals(java.time.Duration.ZERO, attempt.getRetryDelayDuration());
    assertEquals(java.time.Duration.ZERO, attempt.getRandomizedRetryDelayDuration());
    assertEquals(
        retrySettingsOverride.getInitialRpcTimeoutDuration(), attempt.getRpcTimeoutDuration());
    assertEquals(java.time.Duration.ZERO, attempt.getRetryDelayDuration());
  }

  @Test
  public void testCreateNextAttempt() {
    TimedAttemptSettings firstAttempt = algorithm.createFirstAttempt();
    TimedAttemptSettings secondAttempt = algorithm.createNextAttempt(firstAttempt);

    // Checking only the most core values, to not make this test too implementation specific.
    assertEquals(1, secondAttempt.getAttemptCount());
    assertEquals(1, secondAttempt.getOverallAttemptCount());
    assertEquals(java.time.Duration.ofMillis(1L), secondAttempt.getRetryDelayDuration());
    assertEquals(java.time.Duration.ofMillis(2L), secondAttempt.getRpcTimeoutDuration());

    TimedAttemptSettings thirdAttempt = algorithm.createNextAttempt(secondAttempt);
    assertEquals(2, thirdAttempt.getAttemptCount());
    assertEquals(java.time.Duration.ofMillis(2L), thirdAttempt.getRetryDelayDuration());
    assertEquals(java.time.Duration.ofMillis(4L), thirdAttempt.getRpcTimeoutDuration());
  }

  @Test
  public void testCreateNextAttemptOverride() {
    TimedAttemptSettings firstAttempt = algorithm.createFirstAttempt(retryingContext);
    TimedAttemptSettings secondAttempt = algorithm.createNextAttempt(firstAttempt);

    // Checking only the most core values, to not make this test too implementation specific.
    assertEquals(1, secondAttempt.getAttemptCount());
    assertEquals(1, secondAttempt.getOverallAttemptCount());
    assertEquals(java.time.Duration.ofMillis(2L), secondAttempt.getRetryDelayDuration());
    assertEquals(java.time.Duration.ofMillis(6L), secondAttempt.getRpcTimeoutDuration());

    TimedAttemptSettings thirdAttempt = algorithm.createNextAttempt(secondAttempt);
    assertEquals(2, thirdAttempt.getAttemptCount());
    assertEquals(java.time.Duration.ofMillis(6L), thirdAttempt.getRetryDelayDuration());
    assertEquals(java.time.Duration.ofMillis(18L), thirdAttempt.getRpcTimeoutDuration());
  }

  @Test
  public void testTruncateToTotalTimeout() {
    RetrySettings timeoutSettings =
        retrySettings
            .toBuilder()
            .setInitialRpcTimeout(java.time.Duration.ofSeconds(4L))
            .setMaxRpcTimeout(java.time.Duration.ofSeconds(4L))
            .setTotalTimeout(java.time.Duration.ofSeconds(4L))
            .build();
    ExponentialRetryAlgorithm timeoutAlg = new ExponentialRetryAlgorithm(timeoutSettings, clock);

    TimedAttemptSettings firstAttempt = timeoutAlg.createFirstAttempt();
    TimedAttemptSettings secondAttempt = timeoutAlg.createNextAttempt(firstAttempt);
    assertThat(secondAttempt.getRpcTimeout()).isAtLeast(firstAttempt.getRpcTimeout());
    assertThat(secondAttempt.getRpcTimeoutDuration()).isAtMost(java.time.Duration.ofSeconds(4L));

    TimedAttemptSettings thirdAttempt = timeoutAlg.createNextAttempt(secondAttempt);
    assertThat(thirdAttempt.getRpcTimeoutDuration()).isAtMost(java.time.Duration.ofSeconds(4L));
  }

  @Test
  public void testShouldRetryTrue() {
    TimedAttemptSettings attempt = algorithm.createFirstAttempt();
    for (int i = 0; i < 2; i++) {
      attempt = algorithm.createNextAttempt(attempt);
    }

    assertTrue(algorithm.shouldRetry(attempt));
  }

  @Test
  public void testShouldRetryFalseOnMaxAttempts() {
    TimedAttemptSettings attempt = algorithm.createFirstAttempt();
    for (int i = 0; i < 6; i++) {
      assertTrue(algorithm.shouldRetry(attempt));
      attempt = algorithm.createNextAttempt(attempt);
    }

    assertFalse(algorithm.shouldRetry(attempt));
  }

  // First attempt runs at 0ms
  // Second attempt runs at 60ms if shouldRetry is true
  // - RPC timeout is 2ms and Time Left is 140ms (shouldRetry == true)
  // Third attempt runs at 60ms if shouldRetry is true
  // - RPC timeout is 4ms and Time Left is 120ms (shouldRetry == true)
  // Fourth attempt runs at 60ms if shouldRetry is true
  // - RPC timeout is 8ms and Time Left is 20ms (shouldRetry == true)
  // Fifth attempt runs at 60ms if shouldRetry is true
  // - RPC timeout is 8ms and Time Left is -40ms (shouldRetry == false)
  @Test
  public void testShouldRetryFalseOnMaxTimeout() {
    // Simulate each attempt with 60ms of clock time.
    // "attempt" = RPC Timeout + createNextAttempt() and shouldRetry()
    TimedAttemptSettings attempt = algorithm.createFirstAttempt();
    clock.incrementNanoTime(java.time.Duration.ofMillis(60L).toNanos());
    for (int i = 0; i < 3; i++) {
      assertTrue(algorithm.shouldRetry(attempt));
      attempt = algorithm.createNextAttempt(attempt);
      clock.incrementNanoTime(java.time.Duration.ofMillis(60L).toNanos());
    }
    assertFalse(algorithm.shouldRetry(attempt));
  }
}
