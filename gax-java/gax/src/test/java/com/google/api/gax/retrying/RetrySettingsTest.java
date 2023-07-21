/*
 * Copyright 2021 Google LLC
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

import com.google.common.truth.Truth;
import org.junit.Test;
import org.threeten.bp.Duration;

public class RetrySettingsTest {

  @Test
  public void retrySettingsSetLogicalTimeout() {
    Duration timeout = Duration.ofMillis(60000);
    RetrySettings retrySettings = RetrySettings.newBuilder().setLogicalTimeout(timeout).build();

    Truth.assertThat(retrySettings.getRpcTimeoutMultiplier()).isEqualTo(1);
    Truth.assertThat(retrySettings.getInitialRpcTimeout()).isEqualTo(timeout);
    Truth.assertThat(retrySettings.getMaxRpcTimeout()).isEqualTo(timeout);
    Truth.assertThat(retrySettings.getTotalTimeout()).isEqualTo(timeout);
  }

  @Test
  public void retrySettingsMerge() {
    RetrySettings.Builder builder =
        RetrySettings.newBuilder()
            .setTotalTimeout(Duration.ofMillis(45000))
            .setInitialRpcTimeout(Duration.ofMillis(2000))
            .setRpcTimeoutMultiplier(1.5)
            .setMaxRpcTimeout(Duration.ofMillis(30000))
            .setInitialRetryDelay(Duration.ofMillis(100))
            .setRetryDelayMultiplier(1.2)
            .setMaxRetryDelay(Duration.ofMillis(1000));
    RetrySettings.Builder mergedBuilder = RetrySettings.newBuilder();
    mergedBuilder.merge(builder);

    RetrySettings settingsA = builder.build();
    RetrySettings settingsB = mergedBuilder.build();

    Truth.assertThat(settingsA.getTotalTimeout()).isEqualTo(settingsB.getTotalTimeout());
    Truth.assertThat(settingsA.getInitialRetryDelay()).isEqualTo(settingsB.getInitialRetryDelay());
    Truth.assertThat(settingsA.getRpcTimeoutMultiplier())
        .isWithin(0)
        .of(settingsB.getRpcTimeoutMultiplier());
    Truth.assertThat(settingsA.getMaxRpcTimeout()).isEqualTo(settingsB.getMaxRpcTimeout());
    Truth.assertThat(settingsA.getInitialRetryDelay()).isEqualTo(settingsB.getInitialRetryDelay());
    Truth.assertThat(settingsA.getRetryDelayMultiplier())
        .isWithin(0)
        .of(settingsB.getRetryDelayMultiplier());
    Truth.assertThat(settingsA.getMaxRetryDelay()).isEqualTo(settingsB.getMaxRetryDelay());
  }

  @Test
  public void testThreetenDeprecationPerformance() {
    final long ITERATIONS = 100_000_000;
    final long NANOS = 123_456_789l;
    final java.time.Duration javaTimeDuration = java.time.Duration.ofNanos(NANOS);
    final org.threeten.bp.Duration threetenDuration = org.threeten.bp.Duration.ofNanos(NANOS);

    java.time.Instant start = java.time.Instant.now();
    java.time.Instant end = null;
    for (long i = 0; i < ITERATIONS; i++) {
      RetrySettings.newBuilder()
              .setTotalTimeout(javaTimeDuration)
              .build();
    }
    end = java.time.Instant.now();
    System.out.println(String.format("setTotalTimeout(java.time.Duration): %s ms",
            java.time.temporal.ChronoUnit.MILLIS.between(start, end)));


    start = java.time.Instant.now();
    for (long i = 0; i < ITERATIONS; i++) {
      RetrySettings.newBuilder()
              .setTotalTimeout(threetenDuration)
              .build();
    }
    end = java.time.Instant.now();
    System.out.println(String.format("setTotalTimeout(org.threeten.bp.Duration): %s ms",
            java.time.temporal.ChronoUnit.MILLIS.between(start, end)));

    start = java.time.Instant.now();
    for (long i = 0; i < ITERATIONS; i++) {
      RetrySettings.newBuilder()
              .setTotalTimeoutNanos(NANOS)
              .build();
    }
    end = java.time.Instant.now();
    System.out.println(String.format("setTotalTimeoutNanos(long): %s ms",
            java.time.temporal.ChronoUnit.MILLIS.between(start, end)));

    RetrySettings settings = RetrySettings.newBuilder()
            .setTotalTimeout(javaTimeDuration)
            .build();

    start = java.time.Instant.now();
    for (long i = 0; i < ITERATIONS; i++) {
      settings.getTotalTimeout();
    }
    end = java.time.Instant.now();
    System.out.println(String.format("getTotalTimeout(): %s ms",
            java.time.temporal.ChronoUnit.MILLIS.between(start, end)));

    start = java.time.Instant.now();
    for (long i = 0; i < ITERATIONS; i++) {
      settings.getTotalTimeoutDuration();
    }
    end = java.time.Instant.now();
    System.out.println(String.format("getTotalTimeoutDuration(): %s ms",
            java.time.temporal.ChronoUnit.MILLIS.between(start, end)));

    start = java.time.Instant.now();
    for (long i = 0; i < ITERATIONS; i++) {
      settings.getTotalTimeoutNanos();
    }
    end = java.time.Instant.now();
    System.out.println(String.format("getTotalTimeoutNanos(): %s ms",
            java.time.temporal.ChronoUnit.MILLIS.between(start, end)));
  }
}
