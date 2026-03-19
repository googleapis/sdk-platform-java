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
import static org.junit.Assert.assertThrows;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.DeadlineExceededException;
import com.google.api.gax.rpc.StatusCode.Code;
import com.google.api.gax.rpc.UnavailableException;
import com.google.api.gax.tracing.ObservabilityAttributes;
import com.google.api.gax.tracing.OpenTelemetryTraceManager;
import com.google.api.gax.tracing.SpanTracerFactory;
import com.google.auth.Credentials;
import com.google.common.collect.ImmutableList;
import com.google.rpc.Status;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannelBuilder;
import io.grpc.MethodDescriptor;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.testing.exporter.InMemorySpanExporter;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ITOtelErrorType {
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
  void testTracing_failedEcho_grpc_recordsErrorType() throws Exception {
    SpanTracerFactory tracingFactory =
        new SpanTracerFactory(new OpenTelemetryTraceManager(openTelemetrySdk));

    try (EchoClient client =
        TestClientInitializer.createGrpcEchoClientOpentelemetry(tracingFactory)) {

      EchoRequest echoRequest =
          EchoRequest.newBuilder()
              .setError(Status.newBuilder().setCode(Code.UNAVAILABLE.ordinal()).build())
              .build();

      assertThrows(UnavailableException.class, () -> client.echo(echoRequest));

      List<SpanData> spans = spanExporter.getFinishedSpanItems();
      assertThat(spans).isNotEmpty();

      SpanData attemptSpan =
          spans.stream()
              .filter(span -> span.getName().equals("google.showcase.v1beta1.Echo/Echo"))
              .findFirst()
              .orElseThrow(() -> new AssertionError("Incorrect span name"));

      assertThat(
              attemptSpan
                  .getAttributes()
                  .get(AttributeKey.stringKey(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE)))
          .isEqualTo("UNAVAILABLE");
    }
  }

  @Test
  void testTracing_failedEcho_httpjson_recordsErrorType() throws Exception {
    SpanTracerFactory tracingFactory =
        new SpanTracerFactory(new OpenTelemetryTraceManager(openTelemetrySdk));

    try (EchoClient client =
        TestClientInitializer.createHttpJsonEchoClientOpentelemetry(tracingFactory)) {

      EchoRequest echoRequest =
          EchoRequest.newBuilder()
              .setError(Status.newBuilder().setCode(Code.UNAVAILABLE.ordinal()).build())
              .build();

      assertThrows(UnavailableException.class, () -> client.echo(echoRequest));

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
                  .get(AttributeKey.stringKey(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE)))
          .isEqualTo("503"); // For HTTP/JSON, the transport code 503 is used for UNAVAILABLE
    }
  }

  @Test
  void testTracing_clientConnectionError_ConnectException_grpc() throws Exception {
    SpanTracerFactory tracingFactory =
        new SpanTracerFactory(new OpenTelemetryTraceManager(openTelemetrySdk));

    int port;
    try (ServerSocket socket = new ServerSocket(0)) {
      port = socket.getLocalPort();
    } // Port is now free but was recently used, likely to be refused.

    EchoSettings grpcEchoSettings =
        EchoSettings.newBuilder()
            .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .setEndpoint("localhost:" + port)
            .build();

    EchoStubSettings.Builder echoStubSettingsBuilder =
        (EchoStubSettings.Builder) grpcEchoSettings.getStubSettings().toBuilder();
    echoStubSettingsBuilder.setTracerFactory(tracingFactory);

    // Disable retries to fail fast
    echoStubSettingsBuilder
        .echoSettings()
        .setRetrySettings(
            echoStubSettingsBuilder.echoSettings().getRetrySettings().toBuilder()
                .setMaxAttempts(1)
                .build());

    try (EchoClient client = EchoClient.create(echoStubSettingsBuilder.build().createStub())) {
      assertThrows(UnavailableException.class, () -> client.echo(EchoRequest.newBuilder().setContent("test").build()));

      List<SpanData> spans = spanExporter.getFinishedSpanItems();
      assertThat(spans).isNotEmpty();

      SpanData errorSpan =
          spans.stream()
              .filter(span -> span.getAttributes().get(AttributeKey.stringKey(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE)) != null)
              .findFirst()
              .orElseThrow(() -> new AssertionError("Span with error.type not found"));

      assertThat(
              errorSpan
                  .getAttributes()
                  .get(AttributeKey.stringKey(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE)))
          .isEqualTo("CLIENT_CONNECTION_ERROR");
    }
  }

  @Test
  void testTracing_clientConnectionError_UnknownHost_grpc() throws Exception {
    SpanTracerFactory tracingFactory =
        new SpanTracerFactory(new OpenTelemetryTraceManager(openTelemetrySdk));

    EchoSettings grpcEchoSettings =
        EchoSettings.newBuilder()
            .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .setEndpoint("this.is.a.bogus.host.name:7469")
            .build();

    EchoStubSettings.Builder echoStubSettingsBuilder =
        (EchoStubSettings.Builder) grpcEchoSettings.getStubSettings().toBuilder();
    echoStubSettingsBuilder.setTracerFactory(tracingFactory);
    echoStubSettingsBuilder.echoSettings().setRetrySettings(
        echoStubSettingsBuilder.echoSettings().getRetrySettings().toBuilder()
            .setMaxAttempts(1)
            .build());

    try (EchoClient client = EchoClient.create(echoStubSettingsBuilder.build().createStub())) {
      assertThrows(UnavailableException.class, () -> client.echo(EchoRequest.newBuilder().setContent("test").build()));

      List<SpanData> spans = spanExporter.getFinishedSpanItems();
      assertThat(spans).isNotEmpty();

      SpanData errorSpan =
          spans.stream()
              .filter(span -> span.getAttributes().get(AttributeKey.stringKey(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE)) != null)
              .findFirst()
              .orElseThrow(() -> new AssertionError("Span with error.type not found"));

      assertThat(
              errorSpan
                  .getAttributes()
                  .get(AttributeKey.stringKey(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE)))
          .isEqualTo("CLIENT_CONNECTION_ERROR");
    }
  }

  @Test
  void testTracing_clientTimeout_grpc() throws Exception {
    SpanTracerFactory tracingFactory =
        new SpanTracerFactory(new OpenTelemetryTraceManager(openTelemetrySdk));

    try (ServerSocket serverSocket = new ServerSocket(0)) {
      int port = serverSocket.getLocalPort();
      // Start a thread to accept the connection but do nothing else (causing timeout)
      Thread serverThread = new Thread(() -> {
        try {
          try (Socket ignored = serverSocket.accept()) {
            Thread.sleep(1000);
          }
        } catch (Exception ignored) {}
      });
      serverThread.start();

      EchoSettings grpcEchoSettings =
          EchoSettings.newBuilder()
              .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
              .setEndpoint("localhost:" + port)
              .build();

      EchoStubSettings.Builder echoStubSettingsBuilder =
          (EchoStubSettings.Builder) grpcEchoSettings.getStubSettings().toBuilder();
      echoStubSettingsBuilder.setTracerFactory(tracingFactory);
      
      // Set a very short timeout to trigger CLIENT_TIMEOUT
      echoStubSettingsBuilder.echoSettings().setRetrySettings(
          echoStubSettingsBuilder.echoSettings().getRetrySettings().toBuilder()
              .setTotalTimeoutDuration(Duration.ofMillis(100))
              .setInitialRpcTimeoutDuration(Duration.ofMillis(100))
              .setMaxRpcTimeoutDuration(Duration.ofMillis(100))
              .setMaxAttempts(1)
              .build()
      );

      try (EchoClient client = EchoClient.create(echoStubSettingsBuilder.build().createStub())) {
        assertThrows(DeadlineExceededException.class, () -> client.echo(EchoRequest.newBuilder().setContent("test").build()));

        List<SpanData> spans = spanExporter.getFinishedSpanItems();
        assertThat(spans).isNotEmpty();

        SpanData errorSpan =
            spans.stream()
                .filter(span -> span.getAttributes().get(AttributeKey.stringKey(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE)) != null)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Span with error.type not found"));

        assertThat(
                errorSpan
                    .getAttributes()
                    .get(AttributeKey.stringKey(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE)))
            .isEqualTo("CLIENT_TIMEOUT");
      } finally {
        serverThread.join();
      }
    }
  }

  @Test
  void testTracing_clientRequestError_grpc() throws Exception {
    SpanTracerFactory tracingFactory =
        new SpanTracerFactory(new OpenTelemetryTraceManager(openTelemetrySdk));

    ClientInterceptor interceptor = new ClientInterceptor() {
        @Override
        public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
            throw new IllegalArgumentException("Mock request error");
        }
    };

    EchoSettings grpcEchoSettings =
        EchoSettings.newBuilder()
            .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .setInterceptorProvider(() -> ImmutableList.of(interceptor))
                    .build())
            .setEndpoint(TestClientInitializer.DEFAULT_GRPC_ENDPOINT)
            .build();

    EchoStubSettings.Builder echoStubSettingsBuilder =
        (EchoStubSettings.Builder) grpcEchoSettings.getStubSettings().toBuilder();
    echoStubSettingsBuilder.setTracerFactory(tracingFactory);

    try (EchoClient client = EchoClient.create(echoStubSettingsBuilder.build().createStub())) {
      assertThrows(IllegalArgumentException.class, () -> client.echo(EchoRequest.newBuilder().setContent("test").build()));

      List<SpanData> spans = spanExporter.getFinishedSpanItems();
      assertThat(spans).isNotEmpty();

      SpanData errorSpan =
          spans.stream()
              .filter(span -> span.getAttributes().get(AttributeKey.stringKey(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE)) != null)
              .findFirst()
              .orElseThrow(() -> new AssertionError("Span with error.type not found"));

      assertThat(
              errorSpan
                  .getAttributes()
                  .get(AttributeKey.stringKey(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE)))
          .isEqualTo("CLIENT_REQUEST_ERROR");
    }
  }

  @Test
  void testTracing_clientAuthenticationError_grpc() throws Exception {
    SpanTracerFactory tracingFactory =
        new SpanTracerFactory(new OpenTelemetryTraceManager(openTelemetrySdk));

    Credentials credentials = new Credentials() {
        @Override
        public String getAuthenticationType() { return "mock"; }
        @Override
        public Map<String, List<String>> getRequestMetadata(URI uri) throws IOException {
            throw new IOException("Mock auth failure", new GeneralSecurityException("Root cause"));
        }
        @Override
        public boolean hasRequestMetadata() { return true; }
        @Override
        public boolean hasRequestMetadataOnly() { return true; }
        @Override
        public void refresh() throws IOException {}
    };

    EchoSettings grpcEchoSettings =
        EchoSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
            .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .setEndpoint(TestClientInitializer.DEFAULT_GRPC_ENDPOINT)
            .build();

    EchoStubSettings.Builder echoStubSettingsBuilder =
        (EchoStubSettings.Builder) grpcEchoSettings.getStubSettings().toBuilder();
    echoStubSettingsBuilder.setTracerFactory(tracingFactory);

    try (EchoClient client = EchoClient.create(echoStubSettingsBuilder.build().createStub())) {
      assertThrows(Exception.class, () -> client.echo(EchoRequest.newBuilder().setContent("test").build()));

      List<SpanData> spans = spanExporter.getFinishedSpanItems();
      assertThat(spans).isNotEmpty();

      SpanData errorSpan =
          spans.stream()
              .filter(span -> span.getAttributes().get(AttributeKey.stringKey(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE)) != null)
              .findFirst()
              .orElseThrow(() -> new AssertionError("Span with error.type not found"));

      assertThat(
              errorSpan
                  .getAttributes()
                  .get(AttributeKey.stringKey(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE)))
          .isEqualTo("CLIENT_AUTHENTICATION_ERROR");
    }
  }
}
