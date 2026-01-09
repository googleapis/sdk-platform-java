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

import com.google.api.gax.tracing.ApiTracer;
import com.google.api.gax.tracing.ApiTracerFactory;
import com.google.api.gax.tracing.BaseApiTracer;
import com.google.api.gax.tracing.OpenTelemetryTracingRecorder;
import com.google.api.gax.tracing.SpanName;
import com.google.api.gax.tracing.TracingTracer;
import com.google.api.gax.tracing.TracingTracerFactory;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.testing.exporter.InMemorySpanExporter;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ITOtelTracing {
  private static final String SERVICE_NAME = "ShowcaseTracingTest";
  private InMemorySpanExporter spanExporter;
  private OpenTelemetryTracingRecorder tracingRecorder;

  @BeforeEach
  void setup() {
    spanExporter = InMemorySpanExporter.create();
    SdkTracerProvider tracerProvider =
        SdkTracerProvider.builder()
            .addSpanProcessor(SimpleSpanProcessor.create(spanExporter))
            .build();
    OpenTelemetry openTelemetry =
        OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).build();
    tracingRecorder = new OpenTelemetryTracingRecorder(openTelemetry, SERVICE_NAME);
  }

  @AfterEach
  void tearDown() {
    System.clearProperty("GOOGLE_CLOUD_ENABLE_TRACING");
  }

  @Test
  void testTracingFeatureFlag() {
    System.setProperty("GOOGLE_CLOUD_ENABLE_TRACING", "false");
    TracingTracerFactory factory = new TracingTracerFactory(tracingRecorder);
    ApiTracer tracer = factory.newTracer(BaseApiTracer.getInstance(), SpanName.of("EchoClient", "Echo"), ApiTracerFactory.OperationType.Unary);
    assertThat(tracer).isNotInstanceOf(TracingTracer.class);

    System.setProperty("GOOGLE_CLOUD_ENABLE_TRACING", "true");
    tracer = factory.newTracer(BaseApiTracer.getInstance(), SpanName.of("EchoClient", "Echo"), ApiTracerFactory.OperationType.Unary);
    assertThat(tracer).isInstanceOf(TracingTracer.class);
  }

  @Test
  void testTracingTracer_recordsLowLevelNetworkSpan() throws Exception {
    System.setProperty("GOOGLE_CLOUD_ENABLE_TRACING", "true");
    TracingTracerFactory factory = new TracingTracerFactory(tracingRecorder);
    try (EchoClient client = TestClientInitializer.createGrpcEchoClientOpentelemetry(factory)) {
      client.echo(EchoRequest.newBuilder().setContent("test").build());

      List<SpanData> spans = spanExporter.getFinishedSpanItems();
      assertThat(spans).isNotEmpty();

      // Verify that the low-level network span was recorded.
      // The OpenTelemetryTracingRecorder typically names this span using the service name prefix.
      boolean foundLowLevelSpan =
          spans.stream()
              .anyMatch(span -> span.getName().equals(SERVICE_NAME + "/low-level-network-span"));
      assertThat(foundLowLevelSpan).isTrue();
    }
  }
}