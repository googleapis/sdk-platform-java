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
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.api.gax.rpc.StatusCode.Code;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.api.gax.rpc.UnavailableException;
import com.google.api.gax.tracing.MetricsTracer;
import com.google.api.gax.tracing.MetricsTracerFactory;
import com.google.api.gax.tracing.OpenTelemetryMetricsRecorder;
import com.google.common.collect.ImmutableList;
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
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Showcase Test to confirm that metrics are being collected and that the correct metrics are being
 * recorded. Utilizes an in-memory metric reader to collect the data.
 *
 * <p>Every test flows through the same way and runs through the same assertions. First, all th
 * metrics are pulled in via {@link #getMetricDataList()} which are polled until all the metrics are
 * collected. Then the test will attempt check that reader collected the correct number of data
 * points in {@link #verifyPointDataSum(List, int)}. Then, check that the attributes to be collected
 * via {@link #verifyStatusAttribute(List, List)}. Finally, check that the status for each attempt
 * is correct.
 */
public class ITOtelMetrics {
  private static final int DEFAULT_OPERATION_COUNT = 1;
  private static final String SERVICE_NAME = "ShowcaseTest";
  private static final String ATTEMPT_COUNT = SERVICE_NAME + "/attempt_count";
  private static final String OPERATION_COUNT = SERVICE_NAME + "/operation_count";
  private static final String ATTEMPT_LATENCY = SERVICE_NAME+ "/attempt_latency";
  private static final String OPERATION_LATENCY = SERVICE_NAME + "/operation_latency";
  private static final int NUM_METRICS = 4;
  private static final int NUM_COLLECTION_FLUSH_ATTEMPTS = 10;
  private InMemoryMetricReader inMemoryMetricReader;
  private EchoClient grpcClient;
  private EchoClient httpClient;

  /**
   * Internal class in the Otel Showcases test used to assert that number of status codes recorded.
   */
  private static class StatusCount {
    private final Code statusCode;
    private final int count;

    public StatusCount(Code statusCode) {
      this(statusCode, 1);
    }

    public StatusCount(Code statusCode, int count) {
      this.statusCode = statusCode;
      this.count = count;
    }

    public Code getStatusCode() {
      return statusCode;
    }

    public int getCount() {
      return count;
    }
  }

  private OpenTelemetryMetricsRecorder createOtelMetricsRecorder(
      InMemoryMetricReader inMemoryMetricReader) {
    SdkMeterProvider sdkMeterProvider =
        SdkMeterProvider.builder().registerMetricReader(inMemoryMetricReader).build();

    OpenTelemetry openTelemetry =
        OpenTelemetrySdk.builder().setMeterProvider(sdkMeterProvider).build();
    return new OpenTelemetryMetricsRecorder(openTelemetry, SERVICE_NAME);
  }

  @Before
  public void setup() throws Exception {
    inMemoryMetricReader = InMemoryMetricReader.create();
    OpenTelemetryMetricsRecorder otelMetricsRecorder =
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

  /**
   * Iterate through all MetricData elements and check that the number of PointData values matches
   * the expected value. A PointData element may have multiple values/ counts inside, so this
   * extracts the value/ count from the PointData before summing.
   *
   * <p>The expected sum for an operation is `1`. Expected sum for an attempt may be 1+.
   */
  private void verifyPointDataSum(List<MetricData> metricDataList, int attemptCount) {
    for (MetricData metricData : metricDataList) {
      Data<?> data = metricData.getData();
      List<PointData> points = new ArrayList<>(data.getPoints());
      switch (metricData.getName()) {
        case OPERATION_COUNT:
          long operationCountSum =
              points.stream().map(x -> ((LongPointData) x).getValue()).reduce(0L, Long::sum);
          Truth.assertThat(operationCountSum).isEqualTo(DEFAULT_OPERATION_COUNT);
          break;
        case ATTEMPT_COUNT:
          long attemptCountSum =
              points.stream().map(x -> ((LongPointData) x).getValue()).reduce(0L, Long::sum);
          Truth.assertThat(attemptCountSum).isEqualTo(attemptCount);
          break;
        case OPERATION_LATENCY:
          long operationLatencyCountSum =
              points.stream().map(x -> ((HistogramPointData) x).getCount()).reduce(0L, Long::sum);
          // It is difficult to verify the actual latency values (operation or attempt)
          // without flaky behavior. Test that the number of data points recorded matches.
          Truth.assertThat(operationLatencyCountSum).isEqualTo(DEFAULT_OPERATION_COUNT);
          break;
        case ATTEMPT_LATENCY:
          long attemptLatencyCountSum =
              points.stream().map(x -> ((HistogramPointData) x).getCount()).reduce(0L, Long::sum);
          // It is difficult to verify the actual latency values (operation or attempt)
          // without flaky behavior. Test that the number of data points recorded matches.
          Truth.assertThat(attemptLatencyCountSum).isEqualTo(attemptCount);
          break;
        default:
          break;
      }
    }
  }

  /**
   * Extract the attributes from MetricData and ensures that default attributes are recorded. Uses
   * the `OPERATION_COUNT` MetricData to test the attributes. The `OPERATION_COUNT` is only recorded
   * once and should only have one element of PointData.
   *
   * <p>Although the Status attribute is recorded by default on every operation, this helper method
   * does not verify it. This is because every individual attempt (retry) may have a different
   * status. {@link #verifyStatusAttribute(List, List)} is used to verify the statuses for every
   * attempt.
   */
  private void verifyDefaultMetricsAttributes(
      List<MetricData> metricDataList, Map<String, String> defaultAttributeMapping) {
    Optional<MetricData> metricDataOptional =
        metricDataList.stream().filter(x -> x.getName().equals(OPERATION_COUNT)).findAny();
    Truth.assertThat(metricDataOptional.isPresent()).isTrue();
    MetricData operationCountMetricData = metricDataOptional.get();

    List<PointData> pointDataList = new ArrayList<>(operationCountMetricData.getData().getPoints());
    // Operation Metrics should only have a 1 data point
    Truth.assertThat(pointDataList.size()).isEqualTo(1);
    Attributes recordedAttributes = pointDataList.get(0).getAttributes();
    Map<AttributeKey<?>, Object> recordedAttributesMap = recordedAttributes.asMap();
    for (Map.Entry<String, String> entrySet : defaultAttributeMapping.entrySet()) {
      String key = entrySet.getKey();
      String value = entrySet.getValue();
      AttributeKey<String> stringAttributeKey = AttributeKey.stringKey(key);
      Truth.assertThat(recordedAttributesMap.containsKey(stringAttributeKey)).isTrue();
      Truth.assertThat(recordedAttributesMap.get(stringAttributeKey)).isEqualTo(value);
    }
  }

  /**
   * Extract the attributes from MetricData and ensures that default attributes are recorded. Uses
   * the `ATTEMPT_COUNT` MetricData to test the attributes. The `ATTEMPT_COUNT` is recorded for
   * every retry attempt and should record the status received that each attempt made.
   */
  private void verifyStatusAttribute(
      List<MetricData> metricDataList, List<StatusCount> statusCountList) {
    Optional<MetricData> metricDataOptional =
        metricDataList.stream().filter(x -> x.getName().equals(ATTEMPT_COUNT)).findAny();
    Truth.assertThat(metricDataOptional.isPresent()).isTrue();
    MetricData attemptCountMetricData = metricDataOptional.get();

    List<PointData> pointDataList = new ArrayList<>(attemptCountMetricData.getData().getPoints());
    Truth.assertThat(pointDataList.size()).isEqualTo(statusCountList.size());

    // The data for attempt count may not be ordered (i.e. the last data point recorded may be the
    // first element in the PointData list). Search for the expected StatusCode from the
    // statusCountList
    // and match with the data inside the pointDataList
    for (StatusCount statusCount : statusCountList) {
      Code statusCode = statusCount.getStatusCode();
      Predicate<PointData> pointDataPredicate =
          x ->
              x.getAttributes()
                  .get(AttributeKey.stringKey(MetricsTracer.STATUS_ATTRIBUTE))
                  .equals(statusCode.toString());
      Optional<PointData> pointDataOptional =
          pointDataList.stream().filter(pointDataPredicate).findFirst();
      Truth.assertThat(pointDataOptional.isPresent()).isTrue();
      LongPointData longPointData = (LongPointData) pointDataOptional.get();
      Truth.assertThat(longPointData.getValue()).isEqualTo(statusCount.getCount());
    }
  }

  /**
   * Attempts to retrieve the metrics from the InMemoryMetricsReader. Sleep every second for at most
   * 10s to try and retrieve all the metrics available. If it is unable to retrieve all the metrics,
   * fail the test.
   */
  private List<MetricData> getMetricDataList() throws InterruptedException {
    for (int i = 0; i < NUM_COLLECTION_FLUSH_ATTEMPTS; i++) {
      Thread.sleep(1000L);
      List<MetricData> metricData = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());
      if (metricData.size() == NUM_METRICS) {
        return metricData;
      }
    }
    Assert.fail("Unable to collect all the metrics required for the test");
    return new ArrayList<>();
  }

  @Test
  public void testGrpc_operationSucceeded_recordsMetrics() throws InterruptedException {
    int attemptCount = 1;
    EchoRequest echoRequest =
        EchoRequest.newBuilder().setContent("test_grpc_operation_succeeded").build();
    grpcClient.echo(echoRequest);

    List<MetricData> metricDataList = getMetricDataList();
    verifyPointDataSum(metricDataList, attemptCount);

    Map<String, String> attributeMapping =
        ImmutableMap.of(
            MetricsTracer.METHOD_NAME_ATTRIBUTE,
            "Echo.Echo",
            MetricsTracer.LANGUAGE_ATTRIBUTE,
            MetricsTracer.DEFAULT_LANGUAGE);
    verifyDefaultMetricsAttributes(metricDataList, attributeMapping);

    List<StatusCount> statusCountList = ImmutableList.of(new StatusCount(Code.OK));
    verifyStatusAttribute(metricDataList, statusCountList);
  }

  @Ignore("https://github.com/googleapis/sdk-platform-java/issues/2503")
  @Test
  public void testHttpJson_operationSucceeded_recordsMetrics() throws InterruptedException {
    int attemptCount = 1;
    EchoRequest echoRequest =
        EchoRequest.newBuilder().setContent("test_http_operation_succeeded").build();
    httpClient.echo(echoRequest);

    List<MetricData> metricDataList = getMetricDataList();
    verifyPointDataSum(metricDataList, attemptCount);

    Map<String, String> attributeMapping =
        ImmutableMap.of(
            MetricsTracer.METHOD_NAME_ATTRIBUTE,
            "google.showcase.v1beta1.Echo/Echo",
            MetricsTracer.LANGUAGE_ATTRIBUTE,
            MetricsTracer.DEFAULT_LANGUAGE);
    verifyDefaultMetricsAttributes(metricDataList, attributeMapping);

    List<StatusCount> statusCountList = ImmutableList.of(new StatusCount(Code.OK));
    verifyStatusAttribute(metricDataList, statusCountList);
  }

  @Test
  public void testGrpc_operationCancelled_recordsMetrics() throws Exception {
    int attemptCount = 1;
    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setResponseDelay(Duration.newBuilder().setSeconds(5))
            .setSuccess(BlockResponse.newBuilder().setContent("grpc_operationCancelled"))
            .build();

    UnaryCallable<BlockRequest, BlockResponse> blockCallable = grpcClient.blockCallable();
    ApiFuture<BlockResponse> blockResponseApiFuture = blockCallable.futureCall(blockRequest);
    // Sleep 1s before cancelling to let the request go through
    Thread.sleep(1000);
    blockResponseApiFuture.cancel(true);

    List<MetricData> metricDataList = getMetricDataList();
    verifyPointDataSum(metricDataList, attemptCount);

    Map<String, String> attributeMapping =
        ImmutableMap.of(
            MetricsTracer.METHOD_NAME_ATTRIBUTE,
            "Echo.Block",
            MetricsTracer.LANGUAGE_ATTRIBUTE,
            MetricsTracer.DEFAULT_LANGUAGE);
    verifyDefaultMetricsAttributes(metricDataList, attributeMapping);

    List<StatusCount> statusCountList = ImmutableList.of(new StatusCount(Code.CANCELLED));
    verifyStatusAttribute(metricDataList, statusCountList);
  }

  @Ignore("https://github.com/googleapis/sdk-platform-java/issues/2503")
  @Test
  public void testHttpJson_operationCancelled_recordsMetrics() throws Exception {
    int attemptCount = 1;
    BlockRequest blockRequest =
        BlockRequest.newBuilder().setResponseDelay(Duration.newBuilder().setSeconds(5)).build();

    UnaryCallable<BlockRequest, BlockResponse> blockCallable = httpClient.blockCallable();
    ApiFuture<BlockResponse> blockResponseApiFuture = blockCallable.futureCall(blockRequest);
    // Sleep 1s before cancelling to let the request go through
    Thread.sleep(1000);
    blockResponseApiFuture.cancel(true);

    List<MetricData> metricDataList = getMetricDataList();
    verifyPointDataSum(metricDataList, attemptCount);

    Map<String, String> attributeMapping =
        ImmutableMap.of(
            MetricsTracer.METHOD_NAME_ATTRIBUTE,
            "google.showcase.v1beta1.Echo/Block",
            MetricsTracer.LANGUAGE_ATTRIBUTE,
            MetricsTracer.DEFAULT_LANGUAGE);
    verifyDefaultMetricsAttributes(metricDataList, attributeMapping);

    List<StatusCount> statusCountList = ImmutableList.of(new StatusCount(Code.CANCELLED));
    verifyStatusAttribute(metricDataList, statusCountList);
  }

  @Test
  public void testGrpc_operationFailed_recordsMetrics() throws InterruptedException {
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

    List<MetricData> metricDataList = getMetricDataList();
    verifyPointDataSum(metricDataList, attemptCount);

    Map<String, String> attributeMapping =
        ImmutableMap.of(
            MetricsTracer.METHOD_NAME_ATTRIBUTE,
            "Echo.Block",
            MetricsTracer.LANGUAGE_ATTRIBUTE,
            MetricsTracer.DEFAULT_LANGUAGE);
    verifyDefaultMetricsAttributes(metricDataList, attributeMapping);

    List<StatusCount> statusCountList = ImmutableList.of(new StatusCount(statusCode));
    verifyStatusAttribute(metricDataList, statusCountList);
  }

  @Ignore("https://github.com/googleapis/sdk-platform-java/issues/2503")
  @Test
  public void testHttpJson_operationFailed_recordsMetrics() throws InterruptedException {
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

    List<MetricData> metricDataList = getMetricDataList();
    verifyPointDataSum(metricDataList, attemptCount);

    Map<String, String> attributeMapping =
        ImmutableMap.of(
            MetricsTracer.METHOD_NAME_ATTRIBUTE,
            "google.showcase.v1beta1.Echo/Block",
            MetricsTracer.LANGUAGE_ATTRIBUTE,
            MetricsTracer.DEFAULT_LANGUAGE);
    verifyDefaultMetricsAttributes(metricDataList, attributeMapping);

    List<StatusCount> statusCountList = ImmutableList.of(new StatusCount(statusCode));
    verifyStatusAttribute(metricDataList, statusCountList);
  }

  @Test
  public void testGrpc_attemptFailedRetriesExhausted_recordsMetrics() throws Exception {
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

    List<MetricData> metricDataList = getMetricDataList();
    verifyPointDataSum(metricDataList, attemptCount);

    Map<String, String> attributeMapping =
        ImmutableMap.of(
            MetricsTracer.METHOD_NAME_ATTRIBUTE,
            "Echo.Echo",
            MetricsTracer.LANGUAGE_ATTRIBUTE,
            MetricsTracer.DEFAULT_LANGUAGE);
    verifyDefaultMetricsAttributes(metricDataList, attributeMapping);

    List<StatusCount> statusCountList = ImmutableList.of(new StatusCount(statusCode, 3));
    verifyStatusAttribute(metricDataList, statusCountList);

    grpcClient.close();
    grpcClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Ignore("https://github.com/googleapis/sdk-platform-java/issues/2503")
  @Test
  public void testHttpJson_attemptFailedRetriesExhausted_recordsMetrics() throws Exception {
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

    List<MetricData> metricDataList = getMetricDataList();
    verifyPointDataSum(metricDataList, attemptCount);

    Map<String, String> attributeMapping =
        ImmutableMap.of(
            MetricsTracer.METHOD_NAME_ATTRIBUTE,
            "google.showcase.v1beta1.Echo/Echo",
            MetricsTracer.LANGUAGE_ATTRIBUTE,
            MetricsTracer.DEFAULT_LANGUAGE);
    verifyDefaultMetricsAttributes(metricDataList, attributeMapping);

    List<StatusCount> statusCountList = ImmutableList.of(new StatusCount(statusCode, 3));
    verifyStatusAttribute(metricDataList, statusCountList);

    httpClient.close();
    httpClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testGrpc_attemptPermanentFailure_recordsMetrics() throws InterruptedException {
    int attemptCount = 1;
    Code statusCode = Code.INVALID_ARGUMENT;
    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setResponseDelay(Duration.newBuilder().setSeconds(2).build())
            .setError(Status.newBuilder().setCode(statusCode.ordinal()).build())
            .build();

    assertThrows(InvalidArgumentException.class, () -> grpcClient.block(blockRequest));

    List<MetricData> metricDataList = getMetricDataList();
    verifyPointDataSum(metricDataList, attemptCount);

    Map<String, String> attributeMapping =
        ImmutableMap.of(
            MetricsTracer.METHOD_NAME_ATTRIBUTE,
            "Echo.Block",
            MetricsTracer.LANGUAGE_ATTRIBUTE,
            MetricsTracer.DEFAULT_LANGUAGE);
    verifyDefaultMetricsAttributes(metricDataList, attributeMapping);

    List<StatusCount> statusCountList = ImmutableList.of(new StatusCount(statusCode));
    verifyStatusAttribute(metricDataList, statusCountList);
  }

  @Ignore("https://github.com/googleapis/sdk-platform-java/issues/2503")
  @Test
  public void testHttpJson_attemptPermanentFailure_recordsMetrics() throws InterruptedException {
    int attemptCount = 1;
    Code statusCode = Code.INVALID_ARGUMENT;
    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setResponseDelay(Duration.newBuilder().setSeconds(2).build())
            .setError(Status.newBuilder().setCode(statusCode.ordinal()).build())
            .build();

    assertThrows(InvalidArgumentException.class, () -> httpClient.block(blockRequest));

    List<MetricData> metricDataList = getMetricDataList();
    verifyPointDataSum(metricDataList, attemptCount);

    Map<String, String> attributeMapping =
        ImmutableMap.of(
            MetricsTracer.METHOD_NAME_ATTRIBUTE,
            "google.showcase.v1beta1.Echo/Block",
            MetricsTracer.LANGUAGE_ATTRIBUTE,
            MetricsTracer.DEFAULT_LANGUAGE);
    verifyDefaultMetricsAttributes(metricDataList, attributeMapping);

    List<StatusCount> statusCountList = ImmutableList.of(new StatusCount(statusCode));
    verifyStatusAttribute(metricDataList, statusCountList);
  }

  @Test
  public void testGrpc_multipleFailedAttempts_successfulOperation() throws Exception {
    int attemptCount = 3;
    // Disable Jitter on this test to try and ensure that the there are 3 attempts made
    // for test. The first two calls should result in a DEADLINE_EXCEEDED exception as
    // 0.5s and 1s are too short for the 1s blocking call (1s still requires time for
    // the showcase server to respond back to the client). The 3rd and final call (2s)
    // should result in an OK Status Code.
    RetrySettings retrySettings =
        RetrySettings.newBuilder()
            .setInitialRpcTimeout(org.threeten.bp.Duration.ofMillis(500L))
            .setRpcTimeoutMultiplier(2.0)
            .setMaxRpcTimeout(org.threeten.bp.Duration.ofMillis(2000L))
            .setTotalTimeout(org.threeten.bp.Duration.ofMillis(6000L))
            .setJittered(false)
            .build();

    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    grpcEchoSettingsBuilder
        .blockSettings()
        .setRetrySettings(retrySettings)
        .setRetryableCodes(ImmutableSet.of(Code.DEADLINE_EXCEEDED));
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

    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setResponseDelay(Duration.newBuilder().setSeconds(1))
            .setSuccess(BlockResponse.newBuilder().setContent("grpcBlockResponse"))
            .build();

    grpcClient.block(blockRequest);

    List<MetricData> metricDataList = getMetricDataList();
    verifyPointDataSum(metricDataList, attemptCount);

    Map<String, String> attributeMapping =
        ImmutableMap.of(
            MetricsTracer.METHOD_NAME_ATTRIBUTE,
            "Echo.Block",
            MetricsTracer.LANGUAGE_ATTRIBUTE,
            MetricsTracer.DEFAULT_LANGUAGE);
    verifyDefaultMetricsAttributes(metricDataList, attributeMapping);

    List<StatusCount> statusCountList =
        ImmutableList.of(new StatusCount(Code.DEADLINE_EXCEEDED, 2), new StatusCount(Code.OK));
    verifyStatusAttribute(metricDataList, statusCountList);

    grpcClient.close();
    grpcClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Ignore("https://github.com/googleapis/sdk-platform-java/issues/2503")
  @Test
  public void testHttpJson_multipleFailedAttempts_successfulOperation() throws Exception {
    int attemptCount = 3;
    RetrySettings retrySettings =
        RetrySettings.newBuilder()
            .setInitialRpcTimeout(org.threeten.bp.Duration.ofMillis(500L))
            .setRpcTimeoutMultiplier(2.0)
            .setMaxRpcTimeout(org.threeten.bp.Duration.ofMillis(2000L))
            .setTotalTimeout(org.threeten.bp.Duration.ofMillis(6000L))
            .setJittered(false)
            .build();

    EchoStubSettings.Builder httpJsonEchoSettingsBuilder = EchoStubSettings.newHttpJsonBuilder();
    httpJsonEchoSettingsBuilder
        .echoSettings()
        .setRetrySettings(retrySettings)
        .setRetryableCodes(ImmutableSet.of(Code.DEADLINE_EXCEEDED));
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

    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setResponseDelay(Duration.newBuilder().setSeconds(1))
            .setSuccess(BlockResponse.newBuilder().setContent("httpjsonBlockResponse"))
            .build();

    grpcClient.block(blockRequest);

    List<MetricData> metricDataList = getMetricDataList();
    verifyPointDataSum(metricDataList, attemptCount);

    Map<String, String> attributeMapping =
        ImmutableMap.of(
            MetricsTracer.METHOD_NAME_ATTRIBUTE,
            "google.showcase.v1beta1.Echo/Block",
            MetricsTracer.LANGUAGE_ATTRIBUTE,
            MetricsTracer.DEFAULT_LANGUAGE);
    verifyDefaultMetricsAttributes(metricDataList, attributeMapping);

    httpClient.close();
    httpClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }
}
