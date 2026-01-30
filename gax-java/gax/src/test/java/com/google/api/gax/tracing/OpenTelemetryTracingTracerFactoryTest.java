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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class OpenTelemetryTracingTracerFactoryTest {

  @Test
  void testNewTracer_createsOpenTelemetryTracingTracer() {
    TracingRecorder recorder = mock(TracingRecorder.class);
    when(recorder.startSpan(anyString(), anyMap()))
        .thenReturn(mock(TracingRecorder.SpanHandle.class));

    OpenTelemetryTracingTracerFactory factory = new OpenTelemetryTracingTracerFactory(recorder);
    ApiTracer tracer =
        factory.newTracer(
            null, SpanName.of("service", "method"), ApiTracerFactory.OperationType.Unary);
    assertThat(tracer).isInstanceOf(OpenTelemetryTracingTracer.class);
  }

  @Test
  void testNewTracer_addsAttributes() {
    TracingRecorder recorder = mock(TracingRecorder.class);
    TracingRecorder.SpanHandle operationHandle = mock(TracingRecorder.SpanHandle.class);
    when(recorder.startSpan(anyString(), anyMap())).thenReturn(operationHandle);

    OpenTelemetryTracingTracerFactory factory =
        new OpenTelemetryTracingTracerFactory(
            recorder, ImmutableMap.of(), ImmutableMap.of("server.port", "443"));
    ApiTracer tracer =
        factory.newTracer(
            null, SpanName.of("service", "method"), ApiTracerFactory.OperationType.Unary);

    tracer.attemptStarted(null, 1);

    ArgumentCaptor<Map<String, String>> attributesCaptor = ArgumentCaptor.forClass(Map.class);
    verify(recorder, atLeastOnce())
        .startSpan(anyString(), attributesCaptor.capture(), eq(operationHandle));

    Map<String, String> attemptAttributes = attributesCaptor.getValue();
    assertThat(attemptAttributes).containsEntry("server.port", "443");
    // Verify repo attribute from gapic.properties
    assertThat(attemptAttributes).containsEntry(OpenTelemetryTracingTracer.REPO_ATTRIBUTE, "googleapis/sdk-platform-java");
  }

  @Test
  void testWithAttributes_returnsNewFactoryWithMergedAttributes() {
    TracingRecorder recorder = mock(TracingRecorder.class);
    TracingRecorder.SpanHandle operationHandle = mock(TracingRecorder.SpanHandle.class);
    when(recorder.startSpan(anyString(), anyMap())).thenReturn(operationHandle);

    OpenTelemetryTracingTracerFactory factory =
        new OpenTelemetryTracingTracerFactory(
            recorder, ImmutableMap.of("op1", "v1"), ImmutableMap.of("at1", "v1"));

    ApiTracerFactory factoryWithAttrs =
        factory.withAttributes(ImmutableMap.of("op2", "v2"), ImmutableMap.of("at2", "v2"));

    assertThat(factoryWithAttrs).isInstanceOf(OpenTelemetryTracingTracerFactory.class);

    ApiTracer tracer =
        factoryWithAttrs.newTracer(
            null, SpanName.of("service", "method"), ApiTracerFactory.OperationType.Unary);

    tracer.attemptStarted(null, 1);

    ArgumentCaptor<Map<String, String>> attributesCaptor = ArgumentCaptor.forClass(Map.class);
    verify(recorder, atLeastOnce())
        .startSpan(anyString(), attributesCaptor.capture(), eq(operationHandle));
    assertThat(attributesCaptor.getValue()).containsEntry("at1", "v1");
    assertThat(attributesCaptor.getValue()).containsEntry("at2", "v2");
    // Verify repo attribute from gapic.properties
    assertThat(attributesCaptor.getValue()).containsEntry(OpenTelemetryTracingTracer.REPO_ATTRIBUTE, "googleapis/sdk-platform-java");
  }
}
