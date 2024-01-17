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
package com.google.api.gax.rpc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.retrying.TimedAttemptSettings;
import com.google.api.gax.rpc.testing.FakeStatusCode;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.Any;
import com.google.rpc.RetryInfo;
import org.junit.Test;
import org.threeten.bp.Duration;

public class RetryInfoRetryAlgorithmTest {

  private static final int RETRY_DELAY_SECOND = 3;
  private static final ErrorDetails ERROR_DETAILS_WITH_RETRY_INFO =
      ErrorDetails.builder()
          .setRawErrorMessages(
              ImmutableList.of(
                  Any.pack(
                      RetryInfo.newBuilder()
                          .setRetryDelay(
                              com.google.protobuf.Duration.newBuilder()
                                  .setSeconds(RETRY_DELAY_SECOND)
                                  .build())
                          .build())))
          .build();

  @Test
  public void testShouldRetryNoContext() {
    ApiException nonRetryable =
        new ApiException(
            null,
            new FakeStatusCode(StatusCode.Code.INTERNAL),
            /* retryable = */ false,
            ERROR_DETAILS_WITH_RETRY_INFO);
    ApiException retryable =
        new ApiException(
            null,
            new FakeStatusCode(StatusCode.Code.UNAVAILABLE),
            /* retryable = */ true,
            ERROR_DETAILS_WITH_RETRY_INFO);

    RetryInfoRetryAlgorithm<String> algorithm = new RetryInfoRetryAlgorithm<>();
    assertTrue(algorithm.shouldRetry(nonRetryable, null));
    assertTrue(algorithm.shouldRetry(retryable, null));

    ApiException nonRetryableNoRetryInfo =
        new ApiException(
            null, new FakeStatusCode(StatusCode.Code.INTERNAL), /* retryable = */ false);

    assertFalse(algorithm.shouldRetry(nonRetryableNoRetryInfo, null));
  }

  @Test
  public void testShouldRetryWithContext() {
    ApiCallContext context = mock(ApiCallContext.class);
    when(context.getRetryableCodes()).thenReturn(null);

    ApiException nonRetryable =
        new ApiException(
            null,
            new FakeStatusCode(StatusCode.Code.INTERNAL),
            /* retryable = */ false,
            ERROR_DETAILS_WITH_RETRY_INFO);
    ApiException retryable =
        new ApiException(
            null,
            new FakeStatusCode(StatusCode.Code.UNAVAILABLE),
            /* retryable = */ true,
            ERROR_DETAILS_WITH_RETRY_INFO);

    RetryInfoRetryAlgorithm<String> algorithm = new RetryInfoRetryAlgorithm<>();
    assertTrue(algorithm.shouldRetry(nonRetryable, null));
    assertTrue(algorithm.shouldRetry(retryable, null));

    ApiException nonRetryableNoRetryInfo =
        new ApiException(
            null, new FakeStatusCode(StatusCode.Code.INTERNAL), /* retryable = */ false);

    assertFalse(algorithm.shouldRetry(nonRetryableNoRetryInfo, null));
  }

  @Test
  public void testNextAttemptSettings() {
    ApiException nonRetryable =
        new ApiException(
            null,
            new FakeStatusCode(StatusCode.Code.INTERNAL),
            /* retryable = */ false,
            ERROR_DETAILS_WITH_RETRY_INFO);

    RetryInfoRetryAlgorithm<String> algorithm = new RetryInfoRetryAlgorithm<>();

    int prevAttempt = 1;
    TimedAttemptSettings prevSettings =
        TimedAttemptSettings.newBuilder()
            .setOverallAttemptCount(prevAttempt)
            .setAttemptCount(prevAttempt)
            .setRetryDelay(Duration.ofMillis(10))
            .setRpcTimeout(Duration.ofSeconds(1))
            .setRandomizedRetryDelay(Duration.ofMillis(20))
            .setFirstAttemptStartTimeNanos(12345)
            .setGlobalSettings(RetrySettings.newBuilder().build())
            .build();
    TimedAttemptSettings nextSettings =
        algorithm.createNextAttempt(nonRetryable, null, prevSettings);
    assertEquals(RETRY_DELAY_SECOND, nextSettings.getRandomizedRetryDelay().toSecondsPart());
    assertEquals(prevAttempt + 1, nextSettings.getAttemptCount());
    assertEquals(prevAttempt + 1, nextSettings.getOverallAttemptCount());
  }
}
