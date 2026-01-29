/*
 * Copyright 2026 Google LLC
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
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenTelemetryTracingTracerTest {
  @Mock private TracingRecorder recorder;
  @Mock private TracingRecorder.SpanHandle operationHandle;
  @Mock private TracingRecorder.SpanHandle attemptHandle;
  private OpenTelemetryTracingTracer tracer;
  private static final String OPERATION_SPAN_NAME = "Service.Method/operation";
  private static final String ATTEMPT_SPAN_NAME = "Service/Method/attempt";

  @BeforeEach
  void setUp() {
    when(recorder.startSpan(eq(OPERATION_SPAN_NAME), anyMap())).thenReturn(operationHandle);
    tracer = new OpenTelemetryTracingTracer(recorder, OPERATION_SPAN_NAME, ATTEMPT_SPAN_NAME);
  }

  @Test
  void testOperationSucceeded_endsSpan() {
    tracer.operationSucceeded();
    verify(operationHandle).end();
  }

  @Test
  void testOperationFailed_recordsErrorAndEndsSpan() {
    Throwable error = new RuntimeException("fail");
    tracer.operationFailed(error);
    verify(operationHandle).recordError(error);
    verify(operationHandle).end();
  }

  @Test
  void testAttemptLifecycle_startsAndEndsAttemptSpan() {
    when(recorder.startSpan(eq(ATTEMPT_SPAN_NAME), anyMap(), eq(operationHandle)))
        .thenReturn(attemptHandle);
    tracer.attemptStarted(new Object(), 1);
    tracer.attemptSucceeded();

    verify(attemptHandle).end();
  }

  @Test
  void testAddAttemptAttributes_passedToAttemptSpan() {
    tracer.addAttemptAttributes(ImmutableMap.of("attempt-key", "attempt-value"));
    tracer.addAttemptAttributes(
        ImmutableMap.of(OpenTelemetryTracingTracer.RPC_SYSTEM_ATTRIBUTE, "grpc"));

    when(recorder.startSpan(eq(ATTEMPT_SPAN_NAME), anyMap(), eq(operationHandle)))
        .thenReturn(attemptHandle);
    tracer.attemptStarted(new Object(), 1);

    ArgumentCaptor<Map<String, String>> attributesCaptor = ArgumentCaptor.forClass(Map.class);
    verify(recorder)
        .startSpan(eq(ATTEMPT_SPAN_NAME), attributesCaptor.capture(), eq(operationHandle));

    Map<String, String> capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes).containsEntry("attempt-key", "attempt-value");
    assertThat(capturedAttributes)
        .containsEntry(
            OpenTelemetryTracingTracer.LANGUAGE_ATTRIBUTE,
            OpenTelemetryTracingTracer.DEFAULT_LANGUAGE);
  }
}
