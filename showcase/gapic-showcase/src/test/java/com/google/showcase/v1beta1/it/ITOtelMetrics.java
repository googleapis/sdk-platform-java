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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

public class ITOtelMetrics {

  // Helper function for creating opentelemetry object defined below, used in each test.
  @Test
  public void testHttpJson_OperationSucceded_recordsMetrics() throws Exception {

    // initialize the otel-collector
    Process process =
        Runtime.getRuntime()
            .exec(
                "../scripts/start_otelcol.sh ../opentelemetry-helper/configs/testHttpJson_OperationSucceded.yaml");
    process.waitFor();

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
    Thread.sleep(3000);

    String filePath = "../opentelemetry-logs/testHttpJson_OperationSucceded-metrics.txt";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String currentLine;

      while ((currentLine = reader.readLine()) != null) {
        System.out.println(currentLine); // Process each line
      }

    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
    }

    // verify the metrics and cleanup
    Process cleanup =
        Runtime.getRuntime()
            .exec(
                "../scripts/verify_metrics.sh ../opentelemetry-helper/metrics/testHttpJson_OperationSucceded.txt");
    process.waitFor();
  }

  private static ApiTracerFactory createOpenTelemetryTracerFactory() {
    // OTLP Metric Exporter setup
    OtlpGrpcMetricExporter metricExporter =
        OtlpGrpcMetricExporter.builder().setEndpoint("http://localhost:4317").build();

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
}
