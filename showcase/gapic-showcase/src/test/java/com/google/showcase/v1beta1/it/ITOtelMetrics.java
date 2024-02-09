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
import com.google.api.core.BetaApi;
import com.google.api.core.ListenableFutureToApiFuture;
import com.google.api.core.SettableApiFuture;
import com.google.api.gax.core.GaxProperties;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.retrying.RetryingFuture;
import com.google.api.gax.rpc.CancelledException;
import com.google.api.gax.rpc.NotFoundException;
import com.google.api.gax.rpc.StatusCode.Code;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.api.gax.tracing.ApiTracerFactory;
import com.google.api.gax.tracing.MetricsTracerFactory;
import com.google.api.gax.tracing.OpentelemetryMetricsRecorder;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.rpc.Status;
import io.grpc.ManagedChannelBuilder;
import com.google.showcase.v1beta1.BlockRequest;
import com.google.showcase.v1beta1.BlockResponse;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import com.google.showcase.v1beta1.WaitResponse;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.ObservableLongMeasurement;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.data.MetricData;
import io.opentelemetry.sdk.metrics.export.MetricReader;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITOtelMetrics {

  // Helper function for creating opentelemetry object defined below, used in each test.
  @Test
  public void testHttpJson_OperationSucceded_recordsMetrics()
      throws Exception {

    //initialize the otel-collector
    Process process = Runtime.getRuntime().exec("../scripts/start_otelcol.sh ../opentelemetry-helper/configs/testHttpJson_OperationSucceded.yaml");
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
    //wait for the metrics to get uploaded
    Thread.sleep(3000);
    //verify the metrics and cleanup
    Process cleanup = Runtime.getRuntime().exec("../scripts/verify_metrics.sh ../opentelemetry-helper/metrics/testHttpJson_OperationSucceded.txt");
    process.waitFor();

  }

  private static ApiTracerFactory createOpenTelemetryTracerFactory() {
    // OTLP Metric Exporter setup
    OtlpGrpcMetricExporter metricExporter = OtlpGrpcMetricExporter.builder()
        .setEndpoint("http://localhost:4317")
        .build();

    // Periodic Metric Reader configuration
    PeriodicMetricReader metricReader = PeriodicMetricReader.builder(metricExporter)
        .setInterval(java.time.Duration.ofSeconds(2))
        .build();

    // OpenTelemetry SDK Configuration
    Resource resource = Resource.builder().build();
    SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder()
        .registerMetricReader(metricReader)
        .setResource(resource)
        .build();

    OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
        .setMeterProvider(sdkMeterProvider)
        .build();

    // Meter Creation
    Meter meter = openTelemetry.meterBuilder("gax")
        .setInstrumentationVersion(GaxProperties.getGaxVersion())
        .build();

    // OpenTelemetry Metrics Recorder
    OpentelemetryMetricsRecorder otelMetricsRecorder = new OpentelemetryMetricsRecorder(meter);

    // Finally, create the Tracer Factory
    return new MetricsTracerFactory(otelMetricsRecorder);
  }
}
