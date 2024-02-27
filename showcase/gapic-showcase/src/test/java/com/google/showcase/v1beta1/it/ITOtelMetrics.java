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

package com.google.showcase.v1beta1.it;

import static org.junit.Assert.assertThrows;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.core.ApiFuture;
import com.google.api.gax.core.GaxProperties;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.api.gax.rpc.StatusCode.Code;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.api.gax.rpc.UnavailableException;
import com.google.api.gax.tracing.MetricsTracerFactory;
import com.google.api.gax.tracing.OpentelemetryMetricsRecorder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.truth.Truth;
import com.google.protobuf.Duration;
import com.google.rpc.Status;
import com.google.showcase.v1beta1.BlockRequest;
import com.google.showcase.v1beta1.BlockResponse;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import io.grpc.ManagedChannelBuilder;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.data.Data;
import io.opentelemetry.sdk.metrics.data.HistogramPointData;
import io.opentelemetry.sdk.metrics.data.LongPointData;
import io.opentelemetry.sdk.metrics.data.MetricData;
import io.opentelemetry.sdk.metrics.data.PointData;
import io.opentelemetry.sdk.testing.exporter.InMemoryMetricReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Showcase Test to confirm that metrics are being collected and that the correct metrics are being
 * recorded. Utilizes an in-memory metric reader to collect the data.
 */
public class ITOtelMetrics {
  private static final String ATTEMPT_COUNT = "attempt_count";
  private static final String OPERATION_COUNT = "operation_count";
  private static final String ATTEMPT_LATENCY = "attempt_latency";
  private static final String OPERATION_LATENCY = "operation_latency";
  private static final String DEFAULT_LANGUAGE = "Java";
  private InMemoryMetricReader inMemoryMetricReader;
  private EchoClient grpcClient;
  private EchoClient httpClient;

  private OpentelemetryMetricsRecorder createOtelMetricsRecorder(
      InMemoryMetricReader inMemoryMetricReader) {
    SdkMeterProvider sdkMeterProvider =
        SdkMeterProvider.builder().registerMetricReader(inMemoryMetricReader).build();

    OpenTelemetry openTelemetry =
        OpenTelemetrySdk.builder().setMeterProvider(sdkMeterProvider).build();

    // Meter Creation
    Meter meter =
        openTelemetry
            .meterBuilder("ShowcaseOtelMetrics")
            .setInstrumentationVersion(GaxProperties.getGaxVersion())
            .build();
    // OpenTelemetry Metrics Recorder
    return new OpentelemetryMetricsRecorder(meter);
  }

  @Before
  public void setup() throws Exception {
    inMemoryMetricReader = InMemoryMetricReader.create();
    OpentelemetryMetricsRecorder otelMetricsRecorder =
        createOtelMetricsRecorder(inMemoryMetricReader);
    grpcClient =
        TestClientInitializer.createGrpcEchoClientOpentelemetry(
            new MetricsTracerFactory(otelMetricsRecorder));
    httpClient =
        TestClientInitializer.createHttpJsonEchoClientOpentelemetry(
            new MetricsTracerFactory(otelMetricsRecorder));
  }

  @After
  public void cleanup() throws InterruptedException {
    inMemoryMetricReader.shutdown();

    grpcClient.close();
    httpClient.close();

    grpcClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
    httpClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  private void verifyMetricData(
      List<MetricData> metricDataList, int operationCount, int attemptCount) {
    for (MetricData metricData : metricDataList) {
      Data<?> data = metricData.getData();
      List<PointData> points = new ArrayList<>(data.getPoints());
      Truth.assertThat(points.size()).isEqualTo(1);
      PointData pointData = points.get(0);
      switch (metricData.getName()) {
        case OPERATION_COUNT:
          Truth.assertThat(((LongPointData) pointData).getValue()).isEqualTo(operationCount);
          break;
        case ATTEMPT_COUNT:
          Truth.assertThat(((LongPointData) pointData).getValue()).isEqualTo(attemptCount);
          break;
        case OPERATION_LATENCY:
          // It is difficult to verify the actual latency values (operation or attempt)
          // without flaky behavior. Test that the number of data points recorded matches.
          Truth.assertThat(((HistogramPointData) pointData).getCount()).isEqualTo(operationCount);
          break;
        case ATTEMPT_LATENCY:
          // It is difficult to verify the actual latency values (operation or attempt)
          // without flaky behavior. Test that the number of data points recorded matches.
          Truth.assertThat(((HistogramPointData) pointData).getCount()).isEqualTo(attemptCount);
          break;
        default:
          break;
      }
    }
  }

  /**
   * Extract the attributes from the Metric Data and ensure that the attributes recorded match the
   * keys and values stored inside the attributeMapping.
   */
  private void verifyMetricAttributes(MetricData metricData, Map<String, String> attributeMapping) {
    List<PointData> pointDataList = (List<PointData>) metricData.getData().getPoints();
    Truth.assertThat(pointDataList.size()).isEqualTo(1);
    Attributes attributes = pointDataList.get(0).getAttributes();
    Map<AttributeKey<?>, Object> attributesMap = attributes.asMap();
    for (Map.Entry<AttributeKey<?>, Object> entrySet : attributesMap.entrySet()) {
      String key = entrySet.getKey().getKey();
      String value = entrySet.getValue().toString();
      Truth.assertThat(attributeMapping.containsKey(key)).isTrue();
      Truth.assertThat(attributeMapping.get(key)).isEqualTo(value);
    }
  }

  @Test
  public void testGrpc_operationSucceeded_recordsMetrics() {
    int operationCount = 1;
    int attemptCount = 1;
    Code statusCode = Code.OK;
    EchoRequest echoRequest =
        EchoRequest.newBuilder().setContent("test_grpc_operation_succeeded").build();
    grpcClient.echo(echoRequest);

    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name", "Echo.Echo", "status", statusCode.name(), "language", DEFAULT_LANGUAGE);
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }

  @Test
  public void testHttpJson_operationSucceeded_recordsMetrics() {
    int operationCount = 1;
    int attemptCount = 1;
    Code statusCode = Code.OK;
    EchoRequest echoRequest =
        EchoRequest.newBuilder().setContent("test_http_operation_succeeded").build();
    httpClient.echo(echoRequest);

    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name",
            "google.showcase.v1beta1.Echo/Echo",
            "status",
            statusCode.name(),
            "language",
            DEFAULT_LANGUAGE);
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }

  @Test
  public void testGrpc_operationCancelled_recordsMetrics() throws Exception {
    int operationCount = 1;
    int attemptCount = 1;
    Code statusCode = Code.CANCELLED;
    BlockRequest blockRequest =
        BlockRequest.newBuilder().setResponseDelay(Duration.newBuilder().setSeconds(5)).build();

    UnaryCallable<BlockRequest, BlockResponse> blockCallable = grpcClient.blockCallable();
    ApiFuture<BlockResponse> blockResponseApiFuture = blockCallable.futureCall(blockRequest);
    // Sleep 1s before cancelling to let the request go through
    Thread.sleep(1000);
    blockResponseApiFuture.cancel(true);

    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name", "Echo.Block", "status", statusCode.name(), "language", DEFAULT_LANGUAGE);
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }

  @Test
  public void testHttpJson_operationCancelled_recordsMetrics() throws Exception {
    int operationCount = 1;
    int attemptCount = 1;
    Code statusCode = Code.CANCELLED;
    BlockRequest blockRequest =
        BlockRequest.newBuilder().setResponseDelay(Duration.newBuilder().setSeconds(5)).build();

    UnaryCallable<BlockRequest, BlockResponse> blockCallable = httpClient.blockCallable();
    ApiFuture<BlockResponse> blockResponseApiFuture = blockCallable.futureCall(blockRequest);
    // Sleep 1s before cancelling to let the request go through
    Thread.sleep(1000);
    blockResponseApiFuture.cancel(true);

    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name",
            "google.showcase.v1beta1.Echo/Block",
            "status",
            statusCode.name(),
            "language",
            DEFAULT_LANGUAGE);
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }

  @Test
  public void testGrpc_operationFailed_recordsMetrics() {
    int operationCount = 1;
    int attemptCount = 1;
    Code statusCode = Code.INVALID_ARGUMENT;
    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setResponseDelay(Duration.newBuilder().setSeconds(2))
            .setError(Status.newBuilder().setCode(statusCode.ordinal()))
            .build();

    UnaryCallable<BlockRequest, BlockResponse> blockCallable = grpcClient.blockCallable();
    ApiFuture<BlockResponse> blockResponseApiFuture = blockCallable.futureCall(blockRequest);
    assertThrows(ExecutionException.class, blockResponseApiFuture::get);

    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name", "Echo.Block", "status", statusCode.name(), "language", DEFAULT_LANGUAGE);
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }

  @Ignore("https://github.com/googleapis/sdk-platform-java/issues/2503")
  @Test
  public void testHttpJson_operationFailed_recordsMetrics() {
    int operationCount = 1;
    int attemptCount = 1;
    Code statusCode = Code.INVALID_ARGUMENT;
    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setResponseDelay(Duration.newBuilder().setSeconds(2))
            .setError(Status.newBuilder().setCode(statusCode.ordinal()))
            .build();

    UnaryCallable<BlockRequest, BlockResponse> blockCallable = httpClient.blockCallable();
    ApiFuture<BlockResponse> blockResponseApiFuture = blockCallable.futureCall(blockRequest);
    assertThrows(ExecutionException.class, blockResponseApiFuture::get);

    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name",
            "google.showcase.v1beta1.Echo/Block",
            "status",
            statusCode.name(),
            "language",
            DEFAULT_LANGUAGE);
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }

  @Test
  public void testGrpc_attemptFailedRetriesExhausted_recordsMetrics() throws Exception {
    int operationCount = 1;
    int attemptCount = 3;
    Code statusCode = Code.UNAVAILABLE;
    // A custom EchoClient is used in this test because retries have jitter, and we cannot
    // predict the number of attempts that are scheduled for an RPC invocation otherwise.
    // The custom retrySettings limit to a set number of attempts before the call gives up.
    RetrySettings retrySettings =
        RetrySettings.newBuilder()
            .setTotalTimeout(org.threeten.bp.Duration.ofMillis(5000L))
            .setMaxAttempts(3)
            .build();

    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    grpcEchoSettingsBuilder
        .echoSettings()
        .setRetrySettings(retrySettings)
        .setRetryableCodes(ImmutableSet.of(statusCode));
    EchoSettings grpcEchoSettings = EchoSettings.create(grpcEchoSettingsBuilder.build());
    grpcEchoSettings =
        grpcEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTracerFactory(
                new MetricsTracerFactory(createOtelMetricsRecorder(inMemoryMetricReader)))
            .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .setEndpoint("localhost:7469")
            .build();
    EchoClient grpcClient = EchoClient.create(grpcEchoSettings);
    EchoRequest echoRequest =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(statusCode.ordinal()).build())
            .build();

    assertThrows(UnavailableException.class, () -> grpcClient.echo(echoRequest));

    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name", "Echo.Echo", "status", statusCode.name(), "language", DEFAULT_LANGUAGE);
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
    grpcClient.close();
    grpcClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Ignore("https://github.com/googleapis/sdk-platform-java/issues/2503")
  @Test
  public void testHttpJson_attemptFailedRetriesExhausted_recordsMetrics() throws Exception {
    int operationCount = 1;
    int attemptCount = 3;
    Code statusCode = Code.UNAVAILABLE;
    // A custom EchoClient is used in this test because retries have jitter, and we cannot
    // predict the number of attempts that are scheduled for an RPC invocation otherwise.
    // The custom retrySettings limit to a set number of attempts before the call gives up.
    RetrySettings retrySettings =
        RetrySettings.newBuilder()
            .setTotalTimeout(org.threeten.bp.Duration.ofMillis(5000L))
            .setMaxAttempts(3)
            .build();

    EchoStubSettings.Builder httpJsonEchoSettingsBuilder = EchoStubSettings.newHttpJsonBuilder();
    httpJsonEchoSettingsBuilder
        .echoSettings()
        .setRetrySettings(retrySettings)
        .setRetryableCodes(ImmutableSet.of(statusCode));
    EchoSettings httpJsonEchoSettings = EchoSettings.create(httpJsonEchoSettingsBuilder.build());
    httpJsonEchoSettings =
        httpJsonEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTracerFactory(
                new MetricsTracerFactory(createOtelMetricsRecorder(inMemoryMetricReader)))
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();

    EchoClient httpClient = EchoClient.create(httpJsonEchoSettings);

    EchoRequest echoRequest =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(statusCode.ordinal()).build())
            .build();

    assertThrows(UnavailableException.class, () -> httpClient.echo(echoRequest));

    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name",
            "google.showcase.v1beta1.Echo/Echo",
            "status",
            statusCode.name(),
            "language",
            DEFAULT_LANGUAGE);
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
    httpClient.close();
    httpClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testGrpc_attemptPermanentFailure_recordsMetrics() {
    int operationCount = 1;
    int attemptCount = 1;
    Code statusCode = Code.INVALID_ARGUMENT;
    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setResponseDelay(Duration.newBuilder().setSeconds(2).build())
            .setError(Status.newBuilder().setCode(statusCode.ordinal()).build())
            .build();

    assertThrows(InvalidArgumentException.class, () -> grpcClient.block(blockRequest));

    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name", "Echo.Block", "status", statusCode.name(), "language", DEFAULT_LANGUAGE);
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }

  @Ignore("https://github.com/googleapis/sdk-platform-java/issues/2503")
  @Test
  public void testHttpJson_attemptPermanentFailure_recordsMetrics() {
    int operationCount = 1;
    int attemptCount = 1;
    Code statusCode = Code.INVALID_ARGUMENT;
    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setResponseDelay(Duration.newBuilder().setSeconds(2).build())
            .setError(Status.newBuilder().setCode(statusCode.ordinal()).build())
            .build();

    assertThrows(InvalidArgumentException.class, () -> httpClient.block(blockRequest));

    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name",
            "google.showcase.v1beta1.Echo/Block",
            "status",
            statusCode.name(),
            "language",
            DEFAULT_LANGUAGE);
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }
}
