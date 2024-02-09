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
import com.google.api.gax.retrying.RetryingFuture;
import com.google.api.gax.rpc.StatusCode.Code;
import com.google.api.gax.tracing.ApiTracerFactory;
import com.google.api.gax.tracing.MetricsTracerFactory;
import com.google.api.gax.tracing.OpentelemetryMetricsRecorder;
import com.google.common.collect.ImmutableSet;
import com.google.common.truth.Truth;
import com.google.rpc.Status;
import com.google.showcase.v1beta1.BlockRequest;
import com.google.showcase.v1beta1.BlockResponse;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import io.grpc.ManagedChannelBuilder;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.Test;

public class ITOtelMetrics {

  @AfterClass
  public static void cleanup_otelcol() throws Exception {
    Process process = Runtime.getRuntime().exec("../scripts/cleanup_otelcol.sh");
    process.waitFor();
  }

  @Test
  public void testHttpJson_OperationSucceded_recordsMetrics() throws Exception {

    // initialize the otel-collector
    setupOtelCollector(
        "../scripts/start_otelcol.sh",
        "../directory1",
        "../opentelemetry-helper/configs/testHttpJson_OperationSucceeded.yaml");

    EchoSettings httpJsonEchoSettings =
        EchoSettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTracerFactory(createOpenTelemetryTracerFactory("4317"))
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();

    EchoClient client = EchoClient.create(httpJsonEchoSettings);

    EchoRequest requestWithNoError =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.OK.ordinal()).build())
            .build();

    client.echoCallable().futureCall(requestWithNoError).get();

    // wait for the metrics to get uploaded
    Thread.sleep(5000);

    String filePath = "../directory1/testHttpJson_OperationSucceeded_metrics.txt";
    // 1. Check if the log file exists
    File file = new File(filePath);
    Truth.assertThat(file.exists()).isTrue();
    Truth.assertThat(file.length() > 0).isTrue();

    // this is for test purpose only, I'm verifying the correct logs exist or not
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testHttpJson_OperationCancelled_recordsMetrics() throws Exception {

    // initialize the otel-collector
    setupOtelCollector(
        "../scripts/start_otelcol.sh",
        "../directory2",
        "../opentelemetry-helper/configs/testHttpJson_OperationCancelled.yaml");

    EchoSettings httpJsonEchoSettings =
        EchoSettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTracerFactory(createOpenTelemetryTracerFactory("4318"))
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    EchoClient client = EchoClient.create(httpJsonEchoSettings);

    EchoRequest requestWithNoError =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.OK.ordinal()).build())
            .build();

    // explicitly cancel the request
    client.echoCallable().futureCall(requestWithNoError).cancel(true);

    // wait for the metrics to get uploaded
    Thread.sleep(5000);

    String filePath = "../directory2/testHttpJson_OperationCancelled_metrics.txt";
    File file = new File(filePath);
    Truth.assertThat(file.exists()).isTrue();
    Truth.assertThat(file.length() > 0).isTrue();
  }

  @Test
  public void testGrpc_attemptFailedRetriesExhausted_throwsException() throws Exception {

    setupOtelCollector(
        "../scripts/start_otelcol.sh",
        "../directory3",
        "../opentelemetry-helper/configs/testGrpc_attemptFailedRetriesExhausted.yaml");

    RetrySettings retrySettings = RetrySettings.newBuilder().setMaxAttempts(2).build();

    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    grpcEchoSettingsBuilder
        .blockSettings()
        .setRetrySettings(retrySettings)
        .setRetryableCodes(ImmutableSet.of(Code.INVALID_ARGUMENT));

    EchoSettings grpcEchoSettingsOtel = EchoSettings.create(grpcEchoSettingsBuilder.build());
    grpcEchoSettingsOtel =
        grpcEchoSettingsOtel
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTracerFactory(createOpenTelemetryTracerFactory("4319"))
            .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .setEndpoint("localhost:7469")
            .build();

    EchoClient grpcClientWithRetrySetting = EchoClient.create(grpcEchoSettingsOtel);

    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.INVALID_ARGUMENT.ordinal()).build())
            .build();

    RetryingFuture<BlockResponse> retryingFuture =
        (RetryingFuture<BlockResponse>)
            grpcClientWithRetrySetting.blockCallable().futureCall(blockRequest);

    Thread.sleep(4000);

    String filePath = "../directory3/testGrpc_attemptFailedRetriesExhausted_metrics.txt";
    File file = new File(filePath);
    Truth.assertThat(file.exists()).isTrue();
    Truth.assertThat(file.length() > 0).isTrue();

    // this is for test purpose only, I'm verifying the correct logs exist or not
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Helper function for creating Opentelemetry object
  private static ApiTracerFactory createOpenTelemetryTracerFactory(String port) {
    // OTLP Metric Exporter setup
    String endpoint = "http://localhost:" + port;
    OtlpGrpcMetricExporter metricExporter =
        OtlpGrpcMetricExporter.builder().setEndpoint(endpoint).build();

    // Periodic Metric Reader configuration
    PeriodicMetricReader metricReader =
        PeriodicMetricReader.builder(metricExporter)
            .setInterval(java.time.Duration.ofSeconds(2))
            .build();

    // OpenTelemetry SDK Configuration
    Resource resource = Resource.builder().build();
    SdkMeterProvider sdkMeterProvider =
        SdkMeterProvider.builder().registerMetricReader(metricReader).setResource(resource).build();

    OpenTelemetry openTelemetry =
        OpenTelemetrySdk.builder().setMeterProvider(sdkMeterProvider).build();

    // Meter Creation
    Meter meter =
        openTelemetry
            .meterBuilder("gax")
            .setInstrumentationVersion(GaxProperties.getGaxVersion())
            .build();

    // OpenTelemetry Metrics Recorder
    OpentelemetryMetricsRecorder otelMetricsRecorder = new OpentelemetryMetricsRecorder(meter);

    // Finally, create the Tracer Factory
    return new MetricsTracerFactory(otelMetricsRecorder);
  }

  private void setupOtelCollector(String scriptPath, String directoryPath, String configPath)
      throws Exception {
    Process process =
        Runtime.getRuntime().exec(scriptPath + " " + directoryPath + " " + configPath);
    process.waitFor();
  }
}
