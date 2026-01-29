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

import com.google.api.gax.rpc.StatusCode;
import com.google.api.gax.tracing.OpenTelemetryTracingRecorder;
import com.google.api.gax.tracing.OpenTelemetryTracingTracer;
import com.google.api.gax.tracing.OpenTelemetryTracingTracerFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.rpc.Status;
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
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ITOtelTracing {
  private static final String SHOWCASE_SERVER_PORT = "7469";
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
    OpenTelemetryTracingTracerFactory tracingFactory =
        new OpenTelemetryTracingTracerFactory(new OpenTelemetryTracingRecorder(openTelemetrySdk));

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
                  .get(AttributeKey.stringKey(OpenTelemetryTracingTracer.LANGUAGE_ATTRIBUTE)))
          .isEqualTo(OpenTelemetryTracingTracer.DEFAULT_LANGUAGE);
      assertThat(
              attemptSpan
                  .getAttributes()
                  .get(AttributeKey.stringKey(OpenTelemetryTracingTracer.PORT_ATTRIBUTE)))
          .isEqualTo(SHOWCASE_SERVER_PORT);
      assertThat(
              attemptSpan
                  .getAttributes()
                  .get(AttributeKey.stringKey(OpenTelemetryTracingTracer.RPC_SYSTEM_ATTRIBUTE)))
          .isEqualTo("grpc");
      assertThat(
              attemptSpan
                  .getAttributes()
                  .get(AttributeKey.stringKey(OpenTelemetryTracingTracer.SERVER_ADDRESS_ATTRIBUTE)))
          .isEqualTo(SHOWCASE_SERVER_ADDRESS);
    }
  }

  @Test
  void testTracing_successfulEcho_httpjson() throws Exception {
    OpenTelemetryTracingTracerFactory tracingFactory =
        new OpenTelemetryTracingTracerFactory(new OpenTelemetryTracingRecorder(openTelemetrySdk));

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
                  .get(AttributeKey.stringKey(OpenTelemetryTracingTracer.RPC_SYSTEM_ATTRIBUTE)))
          .isEqualTo("http");
      assertThat(
              attemptSpan
                  .getAttributes()
                  .get(AttributeKey.stringKey(OpenTelemetryTracingTracer.SERVER_ADDRESS_ATTRIBUTE)))
          .isEqualTo(SHOWCASE_SERVER_ADDRESS);
    }
  }

  @Test
  void testTracing_errorRecording() throws Exception {
    List<StatusCode.Code> errorCodes =
        ImmutableList.of(
            StatusCode.Code.UNAVAILABLE,
            StatusCode.Code.INVALID_ARGUMENT,
            StatusCode.Code.NOT_FOUND,
            StatusCode.Code.DEADLINE_EXCEEDED,
            StatusCode.Code.PERMISSION_DENIED);

    OpenTelemetryTracingTracerFactory tracingFactory =
        new OpenTelemetryTracingTracerFactory(new OpenTelemetryTracingRecorder(openTelemetrySdk));

    try (EchoClient client =
        TestClientInitializer.createGrpcEchoClientOpentelemetry(tracingFactory)) {

      for (StatusCode.Code code : errorCodes) {
        spanExporter.reset();
        Assertions.assertThrows(
            Exception.class,
            () ->
                client.echo(
                    EchoRequest.newBuilder()
                        .setContent("error-test-" + code)
                        .setError(Status.newBuilder().setCode(code.ordinal()).build())
                        .build()));

        List<SpanData> spans = spanExporter.getFinishedSpanItems();
        assertThat(spans).isNotEmpty();

        SpanData attemptSpan =
            spans.stream()
                .filter(span -> span.getName().equals("Echo/Echo/attempt"))
                .findFirst()
                .orElseThrow(
                    () ->
                        new AssertionError(
                            "Attempt span 'Echo/Echo/attempt' not found for code: " + code));

        assertThat(
                attemptSpan
                    .getAttributes()
                    .get(AttributeKey.stringKey(OpenTelemetryTracingTracer.ERROR_TYPE_ATTRIBUTE)))
            .isEqualTo(code.toString());
        assertThat(attemptSpan.getStatus().getStatusCode())
            .isEqualTo(io.opentelemetry.api.trace.StatusCode.ERROR);
      }
    }
  }

  @Test
  void testTracing_resendCount_grpc() throws Exception {
    OpenTelemetryTracingTracerFactory tracingFactory =
        new OpenTelemetryTracingTracerFactory(new OpenTelemetryTracingRecorder(openTelemetrySdk));

    try (EchoClient client =
        TestClientInitializer.createGrpcEchoClientOpentelemetry(tracingFactory)) {

      // Simulate UNAVAILABLE to trigger retries
      Assertions.assertThrows(
          Exception.class,
          () ->
              client.echo(
                  EchoRequest.newBuilder()
                      .setContent("resend-test-grpc")
                      .setError(
                          Status.newBuilder()
                              .setCode(StatusCode.Code.UNAVAILABLE.ordinal())
                              .build())
                      .build()));

      List<SpanData> spans = spanExporter.getFinishedSpanItems();
      assertThat(spans).isNotEmpty();

      // Verify that subsequent attempts have gcp.grpc.resend_count
      SpanData secondAttempt =
          spans.stream()
              .filter(
                  span ->
                      span.getName().equals("Echo/Echo/attempt")
                          && Objects.equals(
                              span.getAttributes()
                                  .get(
                                      AttributeKey.stringKey(
                                          OpenTelemetryTracingTracer.GRPC_RESEND_COUNT_ATTRIBUTE)),
                              "1"))
              .findFirst()
              .orElseThrow(() -> new AssertionError("Second attempt span not found"));

      assertThat(secondAttempt.getAttributes().get(AttributeKey.stringKey("gcp.grpc.resend_count")))
          .isEqualTo("1");
    }
  }

  @Test
  void testTracing_resendCount_httpjson() throws Exception {
    OpenTelemetryTracingTracerFactory tracingFactory =
        new OpenTelemetryTracingTracerFactory(new OpenTelemetryTracingRecorder(openTelemetrySdk));

    try (EchoClient client =
        TestClientInitializer.createHttpJsonEchoClientOpentelemetry(tracingFactory)) {

      // Simulate UNAVAILABLE to trigger retries
      Assertions.assertThrows(
          Exception.class,
          () ->
              client.echo(
                  EchoRequest.newBuilder()
                      .setContent("resend-test-http")
                      .setError(
                          Status.newBuilder()
                              .setCode(StatusCode.Code.UNAVAILABLE.ordinal())
                              .build())
                      .build()));

      List<SpanData> spans = spanExporter.getFinishedSpanItems();
      assertThat(spans).isNotEmpty();

      // Verify that subsequent attempts have http.request.resend_count
      SpanData secondAttempt =
          spans.stream()
              .filter(
                  span ->
                      span.getName().equals("google.showcase.v1beta1/Echo/Echo/attempt")
                          && Objects.equals(
                              span.getAttributes()
                                  .get(
                                      AttributeKey.stringKey(
                                          OpenTelemetryTracingTracer.HTTP_RESEND_COUNT_ATTRIBUTE)),
                              "1"))
              .findFirst()
              .orElseThrow(() -> new AssertionError("Second attempt span not found"));

      assertThat(
              secondAttempt
                  .getAttributes()
                  .get(AttributeKey.stringKey("http.request.resend_count")))
          .isEqualTo("1");
    }
  }

  @Test
  void testTracing_withCustomAttributes() throws Exception {
    Map<String, String> opAttributes = ImmutableMap.of("op-key", "op-value");
    Map<String, String> atAttributes = ImmutableMap.of("at-key", "at-value");
    OpenTelemetryTracingTracerFactory tracingFactory =
        new OpenTelemetryTracingTracerFactory(
            new OpenTelemetryTracingRecorder(openTelemetrySdk), opAttributes, atAttributes);

    try (EchoClient client =
        TestClientInitializer.createGrpcEchoClientOpentelemetry(tracingFactory)) {

      client.echo(EchoRequest.newBuilder().setContent("attr-test").build());

      List<SpanData> spans = spanExporter.getFinishedSpanItems();

      SpanData attemptSpan =
          spans.stream()
              .filter(span -> span.getName().equals("Echo/Echo/attempt"))
              .findFirst()
              .orElseThrow(() -> new AssertionError("Attempt span 'Echo/Echo/attempt' not found"));
      assertThat(attemptSpan.getAttributes().get(AttributeKey.stringKey("at-key")))
          .isEqualTo("at-value");
    }
  }

  @Test
  void testTracing_customStubSettings_overridesServiceName() throws Exception {
    String customServiceName = "showcase";
    OpenTelemetryTracingTracerFactory tracingFactory =
        new OpenTelemetryTracingTracerFactory(
            new OpenTelemetryTracingRecorder(openTelemetrySdk),
            ImmutableMap.of(),
            ImmutableMap.of("gcp.client.service", customServiceName));

    try (EchoClient client =
        TestClientInitializer.createGrpcEchoClientWithCustomServiceName(
            tracingFactory, customServiceName)) {

      client.echo(EchoRequest.newBuilder().setContent("content").build());

      List<SpanData> spans = spanExporter.getFinishedSpanItems();
      assertThat(spans).isNotEmpty();

      SpanData attemptSpan =
          spans.stream()
              .filter(span -> span.getName().equals("Echo/Echo/attempt"))
              .findFirst()
              .orElseThrow(() -> new AssertionError("Attempt span 'Echo/Echo/attempt' not found"));
      assertThat(attemptSpan.getAttributes().get(AttributeKey.stringKey("gcp.client.service")))
          .isEqualTo(customServiceName);
    }
  }
}
