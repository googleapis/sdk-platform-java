/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import io.opentelemetry.sdk.metrics.data.HistogramData;
import io.opentelemetry.sdk.metrics.data.LongPointData;
import io.opentelemetry.sdk.metrics.data.MetricData;
import io.opentelemetry.sdk.metrics.data.PointData;
import io.opentelemetry.sdk.metrics.data.SumData;
import io.opentelemetry.sdk.resources.Resource;
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
  private InMemoryMetricReader inMemoryMetricReader;
  private EchoClient grpcClient;
  private EchoClient httpClient;

  private OpentelemetryMetricsRecorder createOtelMetricsRecorder(
      InMemoryMetricReader inMemoryMetricReader) {

    Resource resource = Resource.getDefault();
    SdkMeterProvider sdkMeterProvider =
        SdkMeterProvider.builder()
            .setResource(resource)
            .registerMetricReader(inMemoryMetricReader)
            .build();

    OpenTelemetry openTelemetry =
        OpenTelemetrySdk.builder().setMeterProvider(sdkMeterProvider).build();

    // Meter Creation
    Meter meter =
        openTelemetry
            .meterBuilder("gax")
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

  private void verifyLongSumCount(MetricData metricData, long count) {
    SumData<LongPointData> longSumData = metricData.getLongSumData();
    List<LongPointData> points = new ArrayList<>(longSumData.getPoints());
    Truth.assertThat(points.size()).isEqualTo(1);
    LongPointData pointData = points.get(0);
    Truth.assertThat(pointData.getValue()).isEqualTo(count);
  }

  private void verifyMetricData(
      List<MetricData> metricDataList, int operationCount, int attemptCount) {
    for (MetricData metricData : metricDataList) {
      switch (metricData.getName()) {
        case OPERATION_COUNT:
          verifyLongSumCount(metricData, operationCount);
          break;
        case ATTEMPT_COUNT:
          verifyLongSumCount(metricData, attemptCount);
          break;
        default:
          // It is difficult to verify latency (operation or attempt) without flaky behavior
          break;
      }
    }
  }

  /**
   * Extract the attributes from the Metric Data and ensure that the attributes recorded match the
   * keys and values stored inside the attributeMapping
   */
  private void verifyMetricAttributes(MetricData metricData, Map<String, String> attributeMapping) {
    List<PointData> pointDataList = extractPointData(metricData);
    Truth.assertThat(pointDataList.size()).isEqualTo(1);
    PointData pointData = pointDataList.get(0);
    Attributes attributes = pointData.getAttributes();
    for (Map.Entry<AttributeKey<?>, Object> entrySet : attributes.asMap().entrySet()) {
      if (attributeMapping.containsKey(entrySet.getKey().getKey())) {
        String key = entrySet.getKey().getKey();
        Truth.assertThat(entrySet.getValue()).isEqualTo(attributeMapping.get(key));
      }
    }
  }

  private static List<PointData> extractPointData(MetricData metricData) {
    List<PointData> pointDataList;
    switch (metricData.getType()) {
      case HISTOGRAM:
        HistogramData histogramData = metricData.getHistogramData();
        pointDataList = new ArrayList<>(histogramData.getPoints());
        break;
      case LONG_SUM:
        SumData<LongPointData> longSumData = metricData.getLongSumData();
        pointDataList = new ArrayList<>(longSumData.getPoints());
        break;
      default:
        pointDataList = new ArrayList<>();
        break;
    }
    return pointDataList;
  }

  @Test
  public void testGrpc_operationSucceeded_recordsMetrics() {
    int operationCount = 1;
    int attemptCount = 1;
    grpcClient.echo(EchoRequest.newBuilder().setContent("test_grpc_operation_succeeded").build());

    inMemoryMetricReader.forceFlush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of("method_name", "Echo.Echo", "status", "OK", "language", "Java");
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }

  @Test
  public void testHttpJson_operationSucceeded_recordsMetrics() {
    int operationCount = 1;
    int attemptCount = 1;
    httpClient.echo(EchoRequest.newBuilder().setContent("test_http_operation_succeeded").build());

    inMemoryMetricReader.forceFlush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name", "google.showcase.v1beta1.Echo/Echo", "status", "OK", "language", "Java");
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }

  @Test
  public void testGrpc_operationCancelled_recordsMetrics() throws Exception {
    int operationCount = 1;
    int attemptCount = 1;
    BlockRequest blockRequest =
        BlockRequest.newBuilder().setResponseDelay(Duration.newBuilder().setSeconds(5)).build();

    UnaryCallable<BlockRequest, BlockResponse> blockCallable = grpcClient.blockCallable();
    ApiFuture<BlockResponse> blockResponseApiFuture = blockCallable.futureCall(blockRequest);
    Thread.sleep(1000);
    blockResponseApiFuture.cancel(true);

    inMemoryMetricReader.forceFlush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of("method_name", "Echo.Block", "status", "CANCELLED", "language", "Java");
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }

  @Test
  public void testHttpJson_operationCancelled_recordsMetrics() throws Exception {
    int operationCount = 1;
    int attemptCount = 1;
    BlockRequest blockRequest =
        BlockRequest.newBuilder().setResponseDelay(Duration.newBuilder().setSeconds(5)).build();

    UnaryCallable<BlockRequest, BlockResponse> blockCallable = httpClient.blockCallable();
    ApiFuture<BlockResponse> blockResponseApiFuture = blockCallable.futureCall(blockRequest);
    Thread.sleep(1000);
    blockResponseApiFuture.cancel(true);

    inMemoryMetricReader.forceFlush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name",
            "google.showcase.v1beta1.Echo/Block",
            "status",
            "CANCELLED",
            "language",
            "Java");
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }

  @Test
  public void testGrpc_operationFailed_recordsMetrics() {
    int operationCount = 1;
    int attemptCount = 1;
    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setResponseDelay(Duration.newBuilder().setSeconds(2))
            .setError(Status.newBuilder().setCode(Code.INVALID_ARGUMENT.ordinal()))
            .build();

    UnaryCallable<BlockRequest, BlockResponse> blockCallable = grpcClient.blockCallable();
    ApiFuture<BlockResponse> blockResponseApiFuture = blockCallable.futureCall(blockRequest);
    assertThrows(ExecutionException.class, blockResponseApiFuture::get);

    inMemoryMetricReader.forceFlush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name", "Echo.Block", "status", "INVALID_ARGUMENT", "language", "Java");
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }

  @Test
  public void testHttpJson_operationFailed_recordsMetrics() {
    int operationCount = 1;
    int attemptCount = 1;
    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setResponseDelay(Duration.newBuilder().setSeconds(2))
            .setError(Status.newBuilder().setCode(Code.INVALID_ARGUMENT.ordinal()))
            .build();

    UnaryCallable<BlockRequest, BlockResponse> blockCallable = httpClient.blockCallable();
    ApiFuture<BlockResponse> blockResponseApiFuture = blockCallable.futureCall(blockRequest);
    assertThrows(ExecutionException.class, blockResponseApiFuture::get);

    inMemoryMetricReader.forceFlush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name",
            "google.showcase.v1beta1.Echo/Block",
            "status",
            "UNKNOWN",
            "language",
            "Java");
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }

  @Test
  public void testGrpc_attemptFailedRetriesExhausted_recordsMetrics() throws Exception {
    int operationCount = 1;
    int attemptCount = 3;
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
        .setRetryableCodes(ImmutableSet.of(Code.UNAVAILABLE));
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
            .setError(Status.newBuilder().setCode(Code.UNAVAILABLE.ordinal()).build())
            .build();

    assertThrows(UnavailableException.class, () -> grpcClient.echo(echoRequest));

    inMemoryMetricReader.forceFlush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of("method_name", "Echo.Echo", "status", "UNAVAILABLE", "language", "Java");
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
    grpcClient.close();
    grpcClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Ignore("Temp ignore while investigating this failure")
  @Test
  public void testHttpJson_attemptFailedRetriesExhausted_recordsMetrics() throws Exception {
    int operationCount = 1;
    int attemptCount = 3;
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
        .setRetryableCodes(ImmutableSet.of(Code.UNAVAILABLE));
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
            .setError(Status.newBuilder().setCode(Code.UNAVAILABLE.ordinal()).build())
            .build();

    assertThrows(UnavailableException.class, () -> httpClient.echo(echoRequest));

    inMemoryMetricReader.forceFlush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name",
            "google.showcase.v1beta1.Echo/Echo",
            "status",
            "UNAVAILABLE",
            "language",
            "Java");
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
    httpClient.close();
    httpClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testGrpc_attemptPermanentFailure_recordsMetrics() throws Exception {
    int operationCount = 1;
    int attemptCount = 1;
    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setResponseDelay(Duration.newBuilder().setSeconds(2).build())
            .setError(Status.newBuilder().setCode(Code.INVALID_ARGUMENT.ordinal()).build())
            .build();

    assertThrows(InvalidArgumentException.class, () -> grpcClient.block(blockRequest));

    inMemoryMetricReader.forceFlush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name", "Echo.Block", "status", "INVALID_ARGUMENT", "language", "Java");
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }

  @Test
  public void testHttpJson_attemptPermanentFailure_recordsMetrics() throws Exception {
    int operationCount = 1;
    int attemptCount = 1;
    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setResponseDelay(Duration.newBuilder().setSeconds(2).build())
            .setError(Status.newBuilder().setCode(Code.INVALID_ARGUMENT.ordinal()).build())
            .build();

    assertThrows(InvalidArgumentException.class, () -> httpClient.block(blockRequest));

    inMemoryMetricReader.forceFlush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
    verifyMetricData(metricDataList, operationCount, attemptCount);

    ImmutableMap<String, String> attributeMapping =
        ImmutableMap.of(
            "method_name",
            "google.showcase.v1beta1.Echo/Block",
            "status",
            "UNKNOWN",
            "language",
            "Java");
    verifyMetricAttributes(metricDataList.get(0), attributeMapping);
  }
}
