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

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.GaxProperties;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.StatusCode.Code;
import com.google.api.gax.tracing.MetricsTracerFactory;
import com.google.api.gax.tracing.OpentelemetryMetricsRecorder;
import com.google.common.collect.ImmutableSet;
import com.google.common.truth.Truth;
import com.google.rpc.Status;
import com.google.showcase.v1beta1.BlockRequest;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import io.grpc.ManagedChannelBuilder;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.data.HistogramData;
import io.opentelemetry.sdk.metrics.data.HistogramPointData;
import io.opentelemetry.sdk.metrics.data.MetricData;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.testing.exporter.InMemoryMetricExporter;
import io.opentelemetry.sdk.testing.exporter.InMemoryMetricReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ITOtelMetrics {

  @Test
  public void testHttpJson_OperationSucceded_recordsMetrics() throws Exception {

    InMemoryMetricReader inMemoryMetricReader = InMemoryMetricReader.create();

    OpentelemetryMetricsRecorder otelMetricsRecorder =
        createOtelMetricsRecorder(inMemoryMetricReader);

    EchoClient client =
        TestClientInitializer.createHttpJsonEchoClientOpentelemetry(
            new MetricsTracerFactory(otelMetricsRecorder));

    client.echo(EchoRequest.newBuilder().setContent("test_http_operation_succeeded").build());

    Thread.sleep(1000);
    inMemoryMetricReader.flush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());

    for (MetricData metricData : metricDataList) {
      HistogramData histogramData = metricData.getHistogramData();
      if (!histogramData.getPoints().isEmpty()) {
        for (HistogramPointData pointData : histogramData.getPoints()) {
          String method = pointData.getAttributes().get(AttributeKey.stringKey("method_name"));
          String status = pointData.getAttributes().get(AttributeKey.stringKey("status"));
          Truth.assertThat(method).isEqualTo("google.showcase.v1beta1.Echo/Echo");
          Truth.assertThat(status).isEqualTo("OK");
        }
      }
    }
  }

  @Test
  public void testGrpc_OperationSucceded_recordsMetrics() throws Exception {

    InMemoryMetricReader inMemoryMetricReader = InMemoryMetricReader.create();
    InMemoryMetricExporter exporter = InMemoryMetricExporter.create();

    OpentelemetryMetricsRecorder otelMetricsRecorder =
        createOtelMetricsRecorder(inMemoryMetricReader);

    EchoClient client =
        TestClientInitializer.createGrpcEchoClientOpentelemetry(
            new MetricsTracerFactory(otelMetricsRecorder));

    client.echo(EchoRequest.newBuilder().setContent("test_grpc_operation_succeeded").build());
    inMemoryMetricReader.flush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());

    for (MetricData metricData : metricDataList) {
      HistogramData histogramData = metricData.getHistogramData();
      if (!histogramData.getPoints().isEmpty()) {
        for (HistogramPointData pointData : histogramData.getPoints()) {
          String method = pointData.getAttributes().get(AttributeKey.stringKey("method_name"));
          String status = pointData.getAttributes().get(AttributeKey.stringKey("status"));
          Truth.assertThat(method).isEqualTo("Echo.Echo");
          Truth.assertThat(status).isEqualTo("OK");
        }
      }
    }
  }

  @Test
  public void testHttpJson_OperationCancelled_recordsMetrics() throws Exception {

    InMemoryMetricReader inMemoryMetricReader = InMemoryMetricReader.create();

    OpentelemetryMetricsRecorder otelMetricsRecorder =
        createOtelMetricsRecorder(inMemoryMetricReader);

    EchoClient client =
        TestClientInitializer.createHttpJsonEchoClientOpentelemetry(
            new MetricsTracerFactory(otelMetricsRecorder));

    client
        .echoCallable()
        .futureCall(EchoRequest.newBuilder().setContent("test_http_operation_cancelled").build())
        .cancel(true);

    inMemoryMetricReader.flush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());

    for (MetricData metricData : metricDataList) {
      HistogramData histogramData = metricData.getHistogramData();
      if (!histogramData.getPoints().isEmpty()) {
        for (HistogramPointData pointData : histogramData.getPoints()) {
          String method = pointData.getAttributes().get(AttributeKey.stringKey("method_name"));
          String status = pointData.getAttributes().get(AttributeKey.stringKey("status"));
          Truth.assertThat(method).isEqualTo("google.showcase.v1beta1.Echo/Echo");
          Truth.assertThat(status).isEqualTo("CANCELLED");
        }
      }
    }
  }

  @Test
  public void testGrpc_OperationCancelled_recordsMetrics() throws Exception {

    InMemoryMetricReader inMemoryMetricReader = InMemoryMetricReader.create();
    OpentelemetryMetricsRecorder otelMetricsRecorder =
        createOtelMetricsRecorder(inMemoryMetricReader);
    EchoClient client =
        TestClientInitializer.createGrpcEchoClientOpentelemetry(
            new MetricsTracerFactory(otelMetricsRecorder));

    EchoRequest requestWithNoError =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.OK.ordinal()).build())
            .build();

    client.echoCallable().futureCall(requestWithNoError).cancel(true);
    inMemoryMetricReader.flush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());

    for (MetricData metricData : metricDataList) {
      HistogramData histogramData = metricData.getHistogramData();
      if (!histogramData.getPoints().isEmpty()) {
        for (HistogramPointData pointData : histogramData.getPoints()) {
          String method = pointData.getAttributes().get(AttributeKey.stringKey("method_name"));
          String status = pointData.getAttributes().get(AttributeKey.stringKey("status"));
          Truth.assertThat(method).isEqualTo("Echo.Echo");
          Truth.assertThat(status).isEqualTo("CANCELLED");
        }
      }
    }
  }

  @Test
  public void testHttpJson_OperationFailed_recordsMetrics() throws Exception {

    InMemoryMetricReader inMemoryMetricReader = InMemoryMetricReader.create();
    OpentelemetryMetricsRecorder otelMetricsRecorder =
        createOtelMetricsRecorder(inMemoryMetricReader);
    EchoClient client =
        TestClientInitializer.createHttpJsonEchoClientOpentelemetry(
            new MetricsTracerFactory(otelMetricsRecorder));

    EchoRequest requestWithNoError =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.UNKNOWN.ordinal()).build())
            .build();

    client.echoCallable().futureCall(requestWithNoError);
    Thread.sleep(1000);
    inMemoryMetricReader.flush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());

    for (MetricData metricData : metricDataList) {
      HistogramData histogramData = metricData.getHistogramData();
      if (!histogramData.getPoints().isEmpty()) {
        for (HistogramPointData pointData : histogramData.getPoints()) {
          String method = pointData.getAttributes().get(AttributeKey.stringKey("method_name"));
          String status = pointData.getAttributes().get(AttributeKey.stringKey("status"));
          Truth.assertThat(method).isEqualTo("google.showcase.v1beta1.Echo/Echo");
          Truth.assertThat(status).isEqualTo("UNKNOWN");
        }
      }
    }
  }

  @Test
  public void testGrpc_OperationFailed_recordsMetrics() throws Exception {

    InMemoryMetricReader inMemoryMetricReader = InMemoryMetricReader.create();
    OpentelemetryMetricsRecorder otelMetricsRecorder =
        createOtelMetricsRecorder(inMemoryMetricReader);
    EchoClient client =
        TestClientInitializer.createGrpcEchoClientOpentelemetry(
            new MetricsTracerFactory(otelMetricsRecorder));

    EchoRequest requestWithNoError =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.INVALID_ARGUMENT.ordinal()).build())
            .build();

    client.echoCallable().futureCall(requestWithNoError);
    Thread.sleep(1000);

    inMemoryMetricReader.flush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());

    for (MetricData metricData : metricDataList) {
      HistogramData histogramData = metricData.getHistogramData();
      if (!histogramData.getPoints().isEmpty()) {
        for (HistogramPointData pointData : histogramData.getPoints()) {
          String method = pointData.getAttributes().get(AttributeKey.stringKey("method_name"));
          String status = pointData.getAttributes().get(AttributeKey.stringKey("status"));
          Truth.assertThat(method).isEqualTo("Echo.Echo");
          Truth.assertThat(status).isEqualTo("INVALID_ARGUMENT");
        }
      }
    }
  }

  @Test
  public void testGrpc_attemptFailedRetriesExhausted_recordsMetrics() throws Exception {

    InMemoryMetricReader inMemoryMetricReader = InMemoryMetricReader.create();
    OpentelemetryMetricsRecorder otelMetricsRecorder =
        createOtelMetricsRecorder(inMemoryMetricReader);

    RetrySettings retrySettings = RetrySettings.newBuilder().setMaxAttempts(7).build();

    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    grpcEchoSettingsBuilder
        .blockSettings()
        .setRetrySettings(retrySettings)
        .setRetryableCodes(ImmutableSet.of(Code.INVALID_ARGUMENT));

    EchoSettings grpcEchoSettings = EchoSettings.create(grpcEchoSettingsBuilder.build());
    grpcEchoSettings =
        grpcEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTracerFactory(new MetricsTracerFactory(otelMetricsRecorder))
            .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .setEndpoint("localhost:7469")
            .build();

    EchoClient grpcClientWithRetrySetting = EchoClient.create(grpcEchoSettings);

    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.INVALID_ARGUMENT.ordinal()).build())
            .build();

    grpcClientWithRetrySetting.blockCallable().futureCall(blockRequest).isDone();

    Thread.sleep(1000);

    inMemoryMetricReader.flush();
    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());

    for (MetricData metricData : metricDataList) {
      HistogramData histogramData = metricData.getHistogramData();
      if (!histogramData.getPoints().isEmpty()) {
        for (HistogramPointData pointData : histogramData.getPoints()) {
          String method = pointData.getAttributes().get(AttributeKey.stringKey("method_name"));
          String status = pointData.getAttributes().get(AttributeKey.stringKey("status"));

          System.out.println(pointData);

          // add a comment why I am doing this
          double max = pointData.getMax();
          double min = pointData.getMin();
          if (max != min) {
            Truth.assertThat(pointData.getCount()).isEqualTo(7);
          }
          Truth.assertThat(method).isEqualTo("Echo.Block");
          Truth.assertThat(status).isEqualTo("INVALID_ARGUMENT");
        }
      }
    }
  }

  @Test
  public void testHttpjson_attemptFailedRetriesExhausted_recordsMetrics() throws Exception {

    RetrySettings retrySettings = RetrySettings.newBuilder().setMaxAttempts(5).build();

    InMemoryMetricReader inMemoryMetricReader = InMemoryMetricReader.create();
    OpentelemetryMetricsRecorder otelMetricsRecorder =
        createOtelMetricsRecorder(inMemoryMetricReader);

    EchoStubSettings.Builder httpJsonEchoSettingsBuilder = EchoStubSettings.newHttpJsonBuilder();
    httpJsonEchoSettingsBuilder
        .blockSettings()
        .setRetrySettings(retrySettings)
        .setRetryableCodes(ImmutableSet.of(Code.INVALID_ARGUMENT));

    EchoSettings httpJsonEchoSettings = EchoSettings.create(httpJsonEchoSettingsBuilder.build());
    httpJsonEchoSettings =
        httpJsonEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTracerFactory(new MetricsTracerFactory(otelMetricsRecorder))
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();

    EchoClient httpJsonClientWithRetrySetting = EchoClient.create(httpJsonEchoSettings);

    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.INVALID_ARGUMENT.ordinal()).build())
            .build();

    httpJsonClientWithRetrySetting.blockCallable().futureCall(blockRequest);

    Thread.sleep(1000);
    inMemoryMetricReader.flush();

    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());

    for (MetricData metricData : metricDataList) {
      HistogramData histogramData = metricData.getHistogramData();
      if (!histogramData.getPoints().isEmpty()) {
        for (HistogramPointData pointData : histogramData.getPoints()) {
          String method = pointData.getAttributes().get(AttributeKey.stringKey("method_name"));
          String language = pointData.getAttributes().get(AttributeKey.stringKey("language"));
          String status = pointData.getAttributes().get(AttributeKey.stringKey("status"));
          Truth.assertThat(method).isEqualTo("google.showcase.v1beta1.Echo/Block");
          Truth.assertThat(language).isEqualTo("Java");
          Truth.assertThat(status).isEqualTo("UNKNOWN");
          Truth.assertThat(pointData.getCount()).isEqualTo(5);
        }
      }
    }
  }

  @Test
  public void testGrpc_attemptPermanentFailure_recordsMetrics() throws Exception {

    InMemoryMetricReader inMemoryMetricReader = InMemoryMetricReader.create();
    OpentelemetryMetricsRecorder otelMetricsRecorder =
        createOtelMetricsRecorder(inMemoryMetricReader);

    RetrySettings retrySettings = RetrySettings.newBuilder().setMaxAttempts(3).build();

    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    grpcEchoSettingsBuilder
        .blockSettings()
        .setRetrySettings(retrySettings)
        .setRetryableCodes(ImmutableSet.of(Code.PERMISSION_DENIED));

    EchoSettings grpcEchoSettings = EchoSettings.create(grpcEchoSettingsBuilder.build());
    grpcEchoSettings =
        grpcEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTracerFactory(new MetricsTracerFactory(otelMetricsRecorder))
            .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .setEndpoint("localhost:7469")
            .build();

    EchoClient grpcClientWithRetrySetting = EchoClient.create(grpcEchoSettings);

    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.INVALID_ARGUMENT.ordinal()).build())
            .build();

    grpcClientWithRetrySetting.blockCallable().futureCall(blockRequest).isDone();

    Thread.sleep(1000);
    inMemoryMetricReader.flush();

    List<MetricData> metricDataList = new ArrayList<>(inMemoryMetricReader.collectAllMetrics());

    for (MetricData metricData : metricDataList) {
      HistogramData histogramData = metricData.getHistogramData();
      if (!histogramData.getPoints().isEmpty()) {
        for (HistogramPointData pointData : histogramData.getPoints()) {
          String method = pointData.getAttributes().get(AttributeKey.stringKey("method_name"));
          String language = pointData.getAttributes().get(AttributeKey.stringKey("language"));
          String status = pointData.getAttributes().get(AttributeKey.stringKey("status"));
          Truth.assertThat(method).isEqualTo("Echo.Block");
          Truth.assertThat(language).isEqualTo("Java");
          Truth.assertThat(status).isEqualTo("INVALID_ARGUMENT");
          Truth.assertThat(pointData.getCount()).isEqualTo(1);
        }
      }
    }
  }

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
}
