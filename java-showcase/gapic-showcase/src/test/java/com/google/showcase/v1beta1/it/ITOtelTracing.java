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

package com.google.showcase.v1beta1.it;

import static com.google.common.truth.Truth.assertThat;

import com.google.api.gax.tracing.OpenTelemetryTracingRecorder;
import com.google.api.gax.tracing.TracingTracer;
import com.google.api.gax.tracing.TracingTracerFactory;
import com.google.common.collect.ImmutableMap;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.testing.exporter.InMemorySpanExporter;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ITOtelTracing {
  private static final String SHOWCASE_SERVER_ADDRESS = "localhost";

  private InMemorySpanExporter spanExporter;
  private OpenTelemetrySdk openTelemetrySdk;

  @BeforeEach
  void setup() {
    spanExporter = InMemorySpanExporter.create();

    SdkTracerProvider tracerProvider =
        SdkTracerProvider.builder()
            .addSpanProcessor(SimpleSpanProcessor.create(spanExporter))
            .build();

    openTelemetrySdk =
        OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).buildAndRegisterGlobal();
  }

  @AfterEach
  void tearDown() {
    if (openTelemetrySdk != null) {
      openTelemetrySdk.close();
    }
    GlobalOpenTelemetry.resetForTest();
  }

  @Test
  void testTracing_successfulEcho_grpc() throws Exception {
    TracingTracerFactory tracingFactory =
        new TracingTracerFactory(new OpenTelemetryTracingRecorder(openTelemetrySdk));

    try (EchoClient client =
        TestClientInitializer.createGrpcEchoClientOpentelemetry(tracingFactory)) {

      client.echo(EchoRequest.newBuilder().setContent("tracing-test").build());

      List<SpanData> spans = spanExporter.getFinishedSpanItems();
      assertThat(spans).isNotEmpty();

      SpanData attemptSpan =
          spans.stream()
              .filter(span -> span.getName().equals("Echo/Echo/attempt"))
              .findFirst()
              .orElseThrow(() -> new AssertionError("Attempt span 'Echo/Echo/attempt' not found"));
      assertThat(
              attemptSpan
                  .getAttributes()
                  .get(AttributeKey.stringKey(TracingTracer.LANGUAGE_ATTRIBUTE)))
          .isEqualTo(TracingTracer.DEFAULT_LANGUAGE);
      assertThat(
              attemptSpan
                  .getAttributes()
                  .get(AttributeKey.stringKey(TracingTracer.SERVER_ADDRESS_ATTRIBUTE)))
          .isEqualTo(SHOWCASE_SERVER_ADDRESS);
    }
  }

  @Test
  void testTracing_successfulEcho_httpjson() throws Exception {
    TracingTracerFactory tracingFactory =
        new TracingTracerFactory(new OpenTelemetryTracingRecorder(openTelemetrySdk));

    try (EchoClient client =
        TestClientInitializer.createHttpJsonEchoClientOpentelemetry(tracingFactory)) {

      client.echo(EchoRequest.newBuilder().setContent("tracing-test").build());

      List<SpanData> spans = spanExporter.getFinishedSpanItems();
      assertThat(spans).isNotEmpty();

      SpanData attemptSpan =
          spans.stream()
              .filter(span -> span.getName().equals("google.showcase.v1beta1/Echo/Echo/attempt"))
              .findFirst()
              .orElseThrow(() -> new AssertionError("Attempt span 'Echo/Echo/attempt' not found"));
      assertThat(
              attemptSpan
                  .getAttributes()
                  .get(AttributeKey.stringKey(TracingTracer.SERVER_ADDRESS_ATTRIBUTE)))
          .isEqualTo(SHOWCASE_SERVER_ADDRESS);
    }
  }

  @Test
  void testTracing_withCustomAttributes() throws Exception {
    Map<String, String> opAttributes = ImmutableMap.of("op-key", "op-value");
    Map<String, String> atAttributes = ImmutableMap.of("at-key", "at-value");
    TracingTracerFactory tracingFactory =
        new TracingTracerFactory(
            new OpenTelemetryTracingRecorder(openTelemetrySdk), opAttributes, atAttributes);

    try (EchoClient client =
        TestClientInitializer.createGrpcEchoClientOpentelemetry(tracingFactory)) {

      client.echo(EchoRequest.newBuilder().setContent("attr-test").build());

      openTelemetrySdk.getSdkTracerProvider().forceFlush();
      List<SpanData> spans = spanExporter.getFinishedSpanItems();

      SpanData operationSpan =
          spans.stream()
              .filter(span -> span.getName().equals("Echo.Echo/operation"))
              .findFirst()
              .orElseThrow(
                  () -> new AssertionError("Operation span 'Echo/Echo/operation' not found"));
      assertThat(operationSpan.getAttributes().get(AttributeKey.stringKey("op-key")))
          .isEqualTo("op-value");
      SpanData attemptSpan =
          spans.stream()
              .filter(span -> span.getName().equals("Echo/Echo/attempt"))
              .findFirst()
              .orElseThrow(() -> new AssertionError("Attempt span 'Echo/Echo/attempt' not found"));
      assertThat(attemptSpan.getAttributes().get(AttributeKey.stringKey("at-key")))
          .isEqualTo("at-value");
    }
  }
}
