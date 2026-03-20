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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpanTracerTest {
  @Mock private Tracer tracer;
  @Mock private SpanBuilder spanBuilder;
  @Mock private Span span;
  private SpanTracer spanTracer;
  private static final String ATTEMPT_SPAN_NAME = "Service/Method/attempt";

  @BeforeEach
  void setUp() {
    when(tracer.spanBuilder(anyString())).thenReturn(spanBuilder);
    when(spanBuilder.setSpanKind(any(SpanKind.class))).thenReturn(spanBuilder);
    when(spanBuilder.setAllAttributes(any(Attributes.class))).thenReturn(spanBuilder);
    when(spanBuilder.startSpan()).thenReturn(span);
    spanTracer = new SpanTracer(tracer, ApiTracerContext.empty(), ATTEMPT_SPAN_NAME);
  }

  @Test
  void testAttemptLifecycle_startsAndEndsAttemptSpan() {
    spanTracer.attemptStarted(new Object(), 1);
    spanTracer.attemptSucceeded();

    verify(tracer).spanBuilder(ATTEMPT_SPAN_NAME);
    verify(spanBuilder).setSpanKind(SpanKind.CLIENT);
    verify(span).end();
  }

  @Test
  void testAttemptStarted_includesLanguageAttribute() {
    spanTracer.attemptStarted(new Object(), 1);

    ArgumentCaptor<Attributes> attributesCaptor = ArgumentCaptor.forClass(Attributes.class);
    verify(spanBuilder).setAllAttributes(attributesCaptor.capture());

    assertThat(attributesCaptor.getValue().asMap())
        .containsEntry(
            io.opentelemetry.api.common.AttributeKey.stringKey(SpanTracer.LANGUAGE_ATTRIBUTE),
            SpanTracer.DEFAULT_LANGUAGE);
  }

  @Test
  void testAttemptSucceeded_grpc() {
    ApiTracerContext context =
        ApiTracerContext.newBuilder()
            .setLibraryMetadata(com.google.api.gax.rpc.LibraryMetadata.empty())
            .setTransport(ApiTracerContext.Transport.GRPC)
            .build();
    tracer = new SpanTracer(recorder, context, ATTEMPT_SPAN_NAME);
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);
    tracer.attemptSucceeded();

    ArgumentCaptor<Map<String, Object>> attrsCaptor = ArgumentCaptor.forClass(Map.class);
    verify(attemptHandle).addAttributes(attrsCaptor.capture());
    verify(attemptHandle).end();

    assertThat(attrsCaptor.getValue())
        .containsEntry(ObservabilityAttributes.RPC_RESPONSE_STATUS_ATTRIBUTE, "OK");
  }

  @Test
  void testAttemptSucceeded_http() {
    ApiTracerContext context =
        ApiTracerContext.newBuilder()
            .setLibraryMetadata(com.google.api.gax.rpc.LibraryMetadata.empty())
            .setTransport(ApiTracerContext.Transport.HTTP)
            .build();
    tracer = new SpanTracer(recorder, context, ATTEMPT_SPAN_NAME);
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);
    tracer.attemptSucceeded();

    ArgumentCaptor<Map<String, Object>> attrsCaptor = ArgumentCaptor.forClass(Map.class);
    verify(attemptHandle).addAttributes(attrsCaptor.capture());
    verify(attemptHandle).end();

    assertThat(attrsCaptor.getValue())
        .containsEntry(ObservabilityAttributes.HTTP_RESPONSE_STATUS_ATTRIBUTE, 200L);
  }

  @Test
  void testAttemptFailed_grpc() {
    ApiTracerContext context =
        ApiTracerContext.newBuilder()
            .setLibraryMetadata(com.google.api.gax.rpc.LibraryMetadata.empty())
            .setTransport(ApiTracerContext.Transport.GRPC)
            .build();
    tracer = new SpanTracer(recorder, context, ATTEMPT_SPAN_NAME);
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    com.google.api.gax.rpc.ApiException exception =
        new com.google.api.gax.rpc.ApiException(
            "error",
            null,
            new com.google.api.gax.rpc.StatusCode() {
              @Override
              public Code getCode() {
                return Code.NOT_FOUND;
              }

              @Override
              public Object getTransportCode() {
                return null;
              }
            },
            false);

    tracer.attemptStarted(new Object(), 1);
    tracer.attemptFailedRetriesExhausted(exception);

    ArgumentCaptor<Map<String, Object>> attrsCaptor = ArgumentCaptor.forClass(Map.class);
    verify(attemptHandle).addAttributes(attrsCaptor.capture());
    verify(attemptHandle).end();

    assertThat(attrsCaptor.getValue())
        .containsEntry(ObservabilityAttributes.RPC_RESPONSE_STATUS_ATTRIBUTE, "NOT_FOUND");
  }

  @Test
  void testAttemptFailed_http() {
    ApiTracerContext context =
        ApiTracerContext.newBuilder()
            .setLibraryMetadata(com.google.api.gax.rpc.LibraryMetadata.empty())
            .setTransport(ApiTracerContext.Transport.HTTP)
            .build();
    tracer = new SpanTracer(recorder, context, ATTEMPT_SPAN_NAME);
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    com.google.api.gax.rpc.ApiException exception =
        new com.google.api.gax.rpc.ApiException(
            "error",
            null,
            new com.google.api.gax.rpc.StatusCode() {
              @Override
              public Code getCode() {
                return Code.NOT_FOUND;
              }

              @Override
              public Object getTransportCode() {
                return 404;
              }
            },
            false);

    tracer.attemptStarted(new Object(), 1);
    tracer.attemptFailedRetriesExhausted(exception);

    ArgumentCaptor<Map<String, Object>> attrsCaptor = ArgumentCaptor.forClass(Map.class);
    verify(attemptHandle).addAttributes(attrsCaptor.capture());
    verify(attemptHandle).end();

    assertThat(attrsCaptor.getValue())
        .containsEntry(ObservabilityAttributes.HTTP_RESPONSE_STATUS_ATTRIBUTE, 404L);
  }
}
