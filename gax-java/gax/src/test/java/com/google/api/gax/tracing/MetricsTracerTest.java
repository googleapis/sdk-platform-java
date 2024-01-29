/*
 * Copyright 2019 Google LLC
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
package com.google.api.gax.tracing;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.DeadlineExceededException;
import com.google.api.gax.rpc.NotFoundException;
import com.google.api.gax.rpc.StatusCode.Code;
import com.google.api.gax.rpc.testing.FakeStatusCode;
import com.google.common.collect.ImmutableMap;
import com.google.common.truth.Truth;
import java.util.Map;
import java.util.concurrent.CancellationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.threeten.bp.Duration;

@RunWith(JUnit4.class)
public class MetricsTracerTest {
  @Rule
  public final MockitoRule mockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

  private MetricsTracer metricsTracer;
  @Mock private MetricsRecorder metricsRecorder;

  @Before
  public void setUp() {
    metricsTracer =
        new MetricsTracer(MethodName.of("fake_service", "fake_method"), metricsRecorder);
  }

  @Test
  public void testOperationSucceeded_recordsAttributes() {

    metricsTracer.operationSucceeded();

    Map<String, String> attributes =
        ImmutableMap.of(
            "status", "OK",
            "method_name", "fake_service.fake_method");

    verify(metricsRecorder).recordOperationCount(1, attributes);
    verify(metricsRecorder).recordOperationLatency(anyDouble(), eq(attributes));

    verifyNoMoreInteractions(metricsRecorder);
  }

  @Test
  public void testOperationFailed_recordsAttributes() {

    ApiException error0 =
        new NotFoundException(
            "invalid argument", null, new FakeStatusCode(Code.INVALID_ARGUMENT), false);
    metricsTracer.operationFailed(error0);

    Map<String, String> attributes =
        ImmutableMap.of(
            "status", "INVALID_ARGUMENT",
            "method_name", "fake_service.fake_method");

    verify(metricsRecorder).recordOperationCount(1, attributes);
    verify(metricsRecorder).recordOperationLatency(anyDouble(), eq(attributes));

    verifyNoMoreInteractions(metricsRecorder);
  }

  @Test
  public void testOperationCancelled_recordsAttributes() {

    metricsTracer.operationCancelled();

    Map<String, String> attributes =
        ImmutableMap.of(
            "status", "CANCELLED",
            "method_name", "fake_service.fake_method");

    verify(metricsRecorder).recordOperationCount(1, attributes);
    verify(metricsRecorder).recordOperationLatency(anyDouble(), eq(attributes));

    verifyNoMoreInteractions(metricsRecorder);
  }

  @Test
  public void testAttemptSucceeded_recordsAttributes() {
    // initialize mock-request
    Object mockSuccessfulRequest = new Object();

    // Attempt #1
    metricsTracer.attemptStarted(mockSuccessfulRequest, 0);
    metricsTracer.attemptSucceeded();

    Map<String, String> attributes =
        ImmutableMap.of(
            "status", "OK",
            "method_name", "fake_service.fake_method");

    verify(metricsRecorder).recordAttemptCount(1, attributes);
    verify(metricsRecorder).recordAttemptLatency(anyDouble(), eq(attributes));

    verifyNoMoreInteractions(metricsRecorder);
  }

  @Test
  public void testAttemptFailed_recordsAttributes() {
    // initialize mock-request
    Object mockFailedRequest = new Object();

    // Attempt #1
    metricsTracer.attemptStarted(mockFailedRequest, 0);
    ApiException error0 =
        new NotFoundException(
            "invalid argument", null, new FakeStatusCode(Code.INVALID_ARGUMENT), false);
    metricsTracer.attemptFailed(error0, Duration.ofMillis(2));

    Map<String, String> attributes =
        ImmutableMap.of(
            "status", "INVALID_ARGUMENT",
            "method_name", "fake_service.fake_method");

    verify(metricsRecorder).recordAttemptCount(1, attributes);
    verify(metricsRecorder).recordAttemptLatency(anyDouble(), eq(attributes));

    verifyNoMoreInteractions(metricsRecorder);
  }

  @Test
  public void testAttemptCancelled_recordsAttributes() {
    // initialize mock-request
    Object mockCancelledRequest = new Object();
    // Attempt #1
    metricsTracer.attemptStarted(mockCancelledRequest, 0);
    metricsTracer.attemptCancelled();

    Map<String, String> attributes =
        ImmutableMap.of(
            "status", "CANCELLED",
            "method_name", "fake_service.fake_method");

    verify(metricsRecorder).recordAttemptCount(1, attributes);
    verify(metricsRecorder).recordAttemptLatency(anyDouble(), eq(attributes));

    verifyNoMoreInteractions(metricsRecorder);
  }

  @Test
  public void testAttemptFailedRetriesExhausted_recordsAttributes() {
    // initialize mock-request
    Object mockRequest = new Object();
    // Attempt #1
    metricsTracer.attemptStarted(mockRequest, 0);
    ApiException error0 =
        new DeadlineExceededException(
            "deadline exceeded", null, new FakeStatusCode(Code.DEADLINE_EXCEEDED), false);
    metricsTracer.attemptFailedRetriesExhausted(error0);

    Map<String, String> attributes =
        ImmutableMap.of(
            "status", "DEADLINE_EXCEEDED",
            "method_name", "fake_service.fake_method");

    verify(metricsRecorder).recordAttemptCount(1, attributes);
    verify(metricsRecorder).recordAttemptLatency(anyDouble(), eq(attributes));

    verifyNoMoreInteractions(metricsRecorder);
  }

  @Test
  public void testAttemptPermanentFailure_recordsAttributes() {

    // initialize mock-request
    Object mockRequest = new Object();
    // Attempt #1
    metricsTracer.attemptStarted(mockRequest, 0);
    ApiException error0 =
        new NotFoundException("not found", null, new FakeStatusCode(Code.NOT_FOUND), false);
    metricsTracer.attemptFailedRetriesExhausted(error0);

    Map<String, String> attributes =
        ImmutableMap.of(
            "status", "NOT_FOUND",
            "method_name", "fake_service.fake_method");

    verify(metricsRecorder).recordAttemptCount(1, attributes);
    verify(metricsRecorder).recordAttemptLatency(anyDouble(), eq(attributes));

    verifyNoMoreInteractions(metricsRecorder);
  }

  @Test
  public void testAddAttributes_recordsAttributes() {

    metricsTracer.addAttributes("FakeTableId", "12345");
    Truth.assertThat(metricsTracer.getAttributes().get("FakeTableId").equals("12345"));
  }

  @Test
  public void testExtractStatus_errorConversion() {

    // test all cases of ApiExceptions
    for (Code code : Code.values()) {
      ApiException error = new ApiException("fake_error", null, new FakeStatusCode(code), false);
      String errorCode = metricsTracer.extractStatus(error);
      assertThat(errorCode).isEqualTo(code.toString());
    }

    // test CancellationException
    CancellationException cancellationException = new CancellationException();
    String errorCode = metricsTracer.extractStatus(cancellationException);
    assertThat(errorCode).isEqualTo("CANCELLED");

    // test "UNKNOWN"
    Throwable unknownException = new RuntimeException();
    String errorCode2 = metricsTracer.extractStatus(unknownException);
    assertThat(errorCode2).isEqualTo("UNKNOWN");

    // test "OK"
    String successCode = metricsTracer.extractStatus(null);
    assertThat(successCode).isEqualTo("OK");
  }
}
