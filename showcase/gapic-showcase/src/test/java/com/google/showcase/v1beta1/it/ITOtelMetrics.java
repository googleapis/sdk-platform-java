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
import com.google.api.gax.rpc.StatusCode.Code;
import com.google.api.gax.tracing.ApiTracerFactory;
import com.google.api.gax.tracing.MetricsTracerFactory;
import com.google.api.gax.tracing.OpentelemetryMetricsRecorder;
import com.google.common.truth.Truth;
import com.google.rpc.Status;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoSettings;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import java.io.File;
import org.junit.After;
import org.junit.Test;

public class ITOtelMetrics {

  @After
  public void cleanup_otelcol() throws Exception {
    Process process = Runtime.getRuntime().exec("../scripts/cleanup_otelcol.sh");
    process.waitFor();
  }

  @Test
  public void testHttpJson_OperationSucceded_recordsMetrics() throws Exception {

    // initialize the otel-collector
    setupOtelCollector(
        "../scripts/start_otelcol.sh",
        "../opentelemetry-helper/configs/testHttpJson_OperationSucceeded.yaml");

    EchoSettings httpJsonEchoSettings =
        EchoSettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTracerFactory(createOpenTelemetryTracerFactory())
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
    Thread.sleep(4000);

    String filePath = "../opentelemetry-logs/testHttpJson_OperationSucceeded_metrics.txt";
    // 1. Check if the log file exists
    File file = new File(filePath);
    Truth.assertThat(file.exists()).isTrue();
    // Assert that the file is not empty
    Truth.assertThat(file.length() > 0).isTrue();
  }

  @Test
  public void testHttpJson_OperationCancelled_recordsMetrics() throws Exception {

    // initialize the otel-collector
    setupOtelCollector(
        "../scripts/start_otelcol.sh",
        "../opentelemetry-helper/configs/testHttpJson_OperationCancelled.yaml");

    EchoSettings httpJsonEchoSettings =
        EchoSettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTracerFactory(createOpenTelemetryTracerFactory())
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
    Thread.sleep(4000);

    String filePath = "../opentelemetry-logs/testHttpJson_OperationCancelled_metrics.txt";
    // 1. Check if the log file exists
    File file = new File(filePath);
    Truth.assertThat(file.exists()).isTrue();
    // Assert that the file is not empty
    Truth.assertThat(file.length() > 0).isTrue();
  }

  // Helper function for creating opentelemetry object
  private static ApiTracerFactory createOpenTelemetryTracerFactory() {
    // OTLP Metric Exporter setup
    OtlpGrpcMetricExporter metricExporter =
        OtlpGrpcMetricExporter.builder().setEndpoint("http://localhost:4317").build();

    // Periodic Metric Reader configuration
    PeriodicMetricReader metricReader =
        PeriodicMetricReader.builder(metricExporter)
            .setInterval(java.time.Duration.ofSeconds(3))
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

  private void setupOtelCollector(String scriptPath, String configPath) throws Exception {
    Process process = Runtime.getRuntime().exec(scriptPath + " " + configPath);
    process.waitFor();
  }
}
