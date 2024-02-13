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
import com.google.api.gax.tracing.ApiTracerFactory;
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
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class ITOtelMetrics {

  // @AfterClass
  // public static void cleanup_otelcol() throws Exception {
  //   Process process = Runtime.getRuntime().exec("../scripts/cleanup_otelcol.sh");
  //   process.waitFor();
  // }

  @Test
  public void testHttpJson_OperationSucceded_recordsMetrics() throws Exception {

    // initialize the otel-collector
    setupOtelCollector("../opentelemetry-helper/configs/testHttpJson_OperationSucceeded.yaml");

    EchoClient client =
        TestClientInitializer.createHttpJsonEchoClientOpentelemetry(
            createOpenTelemetryTracerFactory("4317"));

    EchoRequest requestWithNoError =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.OK.ordinal()).build())
            .build();

    client.echo(requestWithNoError);

    // wait for the metrics to get uploaded
    Thread.sleep(5000);

    String filePath = "../test_data/testHttpJson_OperationSucceeded_metrics.txt";
    String attribute1 =
        "\"attributes\":[{\"key\":\"language\",\"value\":{\"stringValue\":\"Java\"}},{\"key\":\"method_name\",\"value\":{\"stringValue\":\"google.showcase.v1beta1.Echo/Echo\"}},{\"key\":\"status\",\"value\":{\"stringValue\":\"OK\"}}]";

    String[] params = {filePath, attribute1};
    int result = verify_metrics(params);
    Truth.assertThat(result).isEqualTo(0);

    client.close();
    client.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testGrpc_OperationSucceded_recordsMetrics() throws Exception {

    // initialize the otel-collector
    setupOtelCollector("../opentelemetry-helper/configs/testGrpc_OperationSucceeded.yaml");

    EchoClient client =
        TestClientInitializer.createGrpcEchoClientOpentelemetry(
            createOpenTelemetryTracerFactory("4318"));

    EchoRequest requestWithNoError =
        EchoRequest.newBuilder().setContent("test_grpc_operation_succeeded").build();

    client.echo(requestWithNoError);

    // wait for the metrics to get uploaded
    Thread.sleep(5000);

    String filePath = "../test_data/testGrpc_OperationSucceeded_metrics.txt";
    String attribute1 =
        "\"attributes\":[{\"key\":\"language\",\"value\":{\"stringValue\":\"Java\"}},{\"key\":\"method_name\",\"value\":{\"stringValue\":\"Echo.Echo\"}},{\"key\":\"status\",\"value\":{\"stringValue\":\"OK\"}}]";

    String[] params = {filePath, attribute1};
    int result = verify_metrics(params);
    Truth.assertThat(result).isEqualTo(0);

    client.close();
    client.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testHttpJson_OperationCancelled_recordsMetrics() throws Exception {

    // initialize the otel-collector
    setupOtelCollector("../opentelemetry-helper/configs/testHttpJson_OperationCancelled.yaml");

    EchoClient client =
        TestClientInitializer.createHttpJsonEchoClientOpentelemetry(
            createOpenTelemetryTracerFactory("4319"));

    EchoRequest requestWithNoError =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.OK.ordinal()).build())
            .build();

    // explicitly cancel the request
    client.echoCallable().futureCall(requestWithNoError).cancel(true);

    // wait for the metrics to get uploaded
    Thread.sleep(5000);

    String filePath = "../test_data/testHttpJson_OperationCancelled_metrics.txt";
    String attribute1 =
        "\"attributes\":[{\"key\":\"language\",\"value\":{\"stringValue\":\"Java\"}},{\"key\":\"method_name\",\"value\":{\"stringValue\":\"google.showcase.v1beta1.Echo/Echo\"}},{\"key\":\"status\",\"value\":{\"stringValue\":\"CANCELLED\"}}]";

    String[] params = {filePath, attribute1};
    int result = verify_metrics(params);
    Truth.assertThat(result).isEqualTo(0);

    client.close();
    client.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testGrpc_OperationCancelled_recordsMetrics() throws Exception {

    // initialize the otel-collector
    setupOtelCollector("../opentelemetry-helper/configs/testGrpc_OperationCancelled.yaml");

    EchoClient client =
        TestClientInitializer.createGrpcEchoClientOpentelemetry(
            createOpenTelemetryTracerFactory("4320"));

    EchoRequest requestWithNoError =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.OK.ordinal()).build())
            .build();

    client.echoCallable().futureCall(requestWithNoError).cancel(true);

    // wait for the metrics to get uploaded
    Thread.sleep(5000);

    String filePath = "../test_data/testGrpc_OperationCancelled_metrics.txt";
    String attribute1 =
        "\"attributes\":[{\"key\":\"language\",\"value\":{\"stringValue\":\"Java\"}},{\"key\":\"method_name\",\"value\":{\"stringValue\":\"Echo.Echo\"}},{\"key\":\"status\",\"value\":{\"stringValue\":\"CANCELLED\"}}]";

    String[] params = {filePath, attribute1};
    int result = verify_metrics(params);
    Truth.assertThat(result).isEqualTo(0);

    client.close();
    client.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testHttpJson_OperationFailed_recordsMetrics() throws Exception {

    // initialize the otel-collector
    setupOtelCollector("../opentelemetry-helper/configs/testHttpJson_OperationFailed.yaml");

    EchoClient client =
        TestClientInitializer.createHttpJsonEchoClientOpentelemetry(
            createOpenTelemetryTracerFactory("4321"));

    EchoRequest requestWithError =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.UNKNOWN.ordinal()).build())
            .build();

    client.echoCallable().futureCall(requestWithError).isDone();

    // wait for the metrics to get uploaded
    Thread.sleep(5000);

    String filePath = "../test_data/testHttpJson_OperationFailed_metrics.txt";
    String attribute1 =
        "\"attributes\":[{\"key\":\"language\",\"value\":{\"stringValue\":\"Java\"}},{\"key\":\"method_name\",\"value\":{\"stringValue\":\"google.showcase.v1beta1.Echo/Echo\"}},{\"key\":\"status\",\"value\":{\"stringValue\":\"UNKNOWN\"}}]";

    String[] params = {filePath, attribute1};
    int result = verify_metrics(params);
    Truth.assertThat(result).isEqualTo(0);

    client.close();
    client.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testGrpc_OperationFailed_recordsMetrics() throws Exception {

    // initialize the otel-collector
    setupOtelCollector("../opentelemetry-helper/configs/testGrpc_OperationFailed.yaml");

    EchoClient client =
        TestClientInitializer.createGrpcEchoClientOpentelemetry(
            createOpenTelemetryTracerFactory("4322"));

    EchoRequest requestWithError =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.UNAUTHENTICATED.ordinal()).build())
            .build();

    client.echoCallable().futureCall(requestWithError).isDone();

    // wait for the metrics to get uploaded
    Thread.sleep(5000);

    String filePath = "../test_data/testGrpc_OperationFailed_metrics.txt";
    String attribute1 =
        "\"attributes\":[{\"key\":\"language\",\"value\":{\"stringValue\":\"Java\"}},{\"key\":\"method_name\",\"value\":{\"stringValue\":\"Echo.Echo\"}},{\"key\":\"status\",\"value\":{\"stringValue\":\"UNAUTHENTICATED\"}}]";

    String[] params = {filePath, attribute1};
    int result = verify_metrics(params);
    Truth.assertThat(result).isEqualTo(0);

    client.close();
    client.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testGrpc_attemptFailedRetriesExhausted_recordsMetrics() throws Exception {

    setupOtelCollector(
        "../opentelemetry-helper/configs/testGrpc_attemptFailedRetriesExhausted.yaml");

    RetrySettings retrySettings = RetrySettings.newBuilder().setMaxAttempts(5).build();

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
            .setTracerFactory(createOpenTelemetryTracerFactory("4323"))
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

    Thread.sleep(5000);

    String filePath = "../test_data/testGrpc_attemptFailedRetriesExhausted_metrics.txt";

    String attribute1 =
        "\"attributes\":[{\"key\":\"language\",\"value\":{\"stringValue\":\"Java\"}},{\"key\":\"method_name\",\"value\":{\"stringValue\":\"Echo.Block\"}},{\"key\":\"status\",\"value\":{\"stringValue\":\"INVALID_ARGUMENT\"}}]";

    // additionally verify that 5 attempts were made
    // when we make 'x' attempts, attempt_count.asInt = 'x' and there are 'x' datapoints in
    // attempt_latency (Count : x} histogram
    // String attribute2 = "\"asInt\":\"5\"";
    String attribute2 = "\"asInt\":\"5\"";
    String attribute3 = "\"count\":\"5\"";

    String[] params = {filePath, attribute1, attribute2, attribute3};
    int result = verify_metrics(params);
    Truth.assertThat(result).isEqualTo(0);

    grpcClientWithRetrySetting.close();
    grpcClientWithRetrySetting.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testHttpjson_attemptFailedRetriesExhausted_recordsMetrics() throws Exception {

    setupOtelCollector(
        "../opentelemetry-helper/configs/testHttpjson_attemptFailedRetriesExhausted.yaml");

    RetrySettings retrySettings = RetrySettings.newBuilder().setMaxAttempts(3).build();

    EchoStubSettings.Builder httpJsonEchoSettingsBuilder = EchoStubSettings.newHttpJsonBuilder();
    httpJsonEchoSettingsBuilder
        .blockSettings()
        .setRetrySettings(retrySettings)
        .setRetryableCodes(ImmutableSet.of(Code.UNKNOWN));

    EchoSettings httpJsonEchoSettings = EchoSettings.create(httpJsonEchoSettingsBuilder.build());
    httpJsonEchoSettings =
        httpJsonEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTracerFactory(createOpenTelemetryTracerFactory("4324"))
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
            .setError(Status.newBuilder().setCode(Code.UNKNOWN.ordinal()).build())
            .build();

    httpJsonClientWithRetrySetting.blockCallable().futureCall(blockRequest).isDone();

    Thread.sleep(5000);

    String filePath = "../test_data/testHttpjson_attemptFailedRetriesExhausted_metrics.txt";

    String attribute1 =
        "\"attributes\":[{\"key\":\"language\",\"value\":{\"stringValue\":\"Java\"}},{\"key\":\"method_name\",\"value\":{\"stringValue\":\"google.showcase.v1beta1.Echo/Block\"}},{\"key\":\"status\",\"value\":{\"stringValue\":\"UNKNOWN\"}}]";

    String[] params = {filePath, attribute1};
    int result = verify_metrics(params);
    Truth.assertThat(result).isEqualTo(0);

    httpJsonClientWithRetrySetting.close();
    httpJsonClientWithRetrySetting.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testGrpc_attemptPermanentFailure_recordsMetrics() throws Exception {

    setupOtelCollector("../opentelemetry-helper/configs/testGrpc_attemptPermanentFailure.yaml");

    RetrySettings retrySettings = RetrySettings.newBuilder().setMaxAttempts(6).build();

    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    grpcEchoSettingsBuilder
        .blockSettings()
        .setRetrySettings(retrySettings)
        .setRetryableCodes(ImmutableSet.of(Code.NOT_FOUND));

    EchoSettings grpcEchoSettings = EchoSettings.create(grpcEchoSettingsBuilder.build());
    grpcEchoSettings =
        grpcEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTracerFactory(createOpenTelemetryTracerFactory("4326"))
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

    Thread.sleep(5000);

    String filePath = "../test_data/testGrpc_attemptPermanentFailure_metrics.txt";

    String attribute1 =
        "\"attributes\":[{\"key\":\"language\",\"value\":{\"stringValue\":\"Java\"}},{\"key\":\"method_name\",\"value\":{\"stringValue\":\"Echo.Block\"}},{\"key\":\"status\",\"value\":{\"stringValue\":\"INVALID_ARGUMENT\"}}]";
    // additionally verify that only 1 attempt was made
    String attribute2 = "\"asInt\":\"1\"";
    String attribute3 = "\"count\":\"1\"";

    String[] params = {filePath, attribute1, attribute2, attribute3};
    int result = verify_metrics(params);
    Truth.assertThat(result).isEqualTo(0);

    grpcClientWithRetrySetting.close();
    grpcClientWithRetrySetting.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  // Helper function for creating Opentelemetry object with a different port for exporter for every
  // test
  // this ensures that logs for each test are collected separately
  private static ApiTracerFactory createOpenTelemetryTracerFactory(String port) {
    // OTLP Metric Exporter setup
    String endpoint = "http://localhost:" + port;
    OtlpGrpcMetricExporter metricExporter =
        OtlpGrpcMetricExporter.builder().setEndpoint(endpoint).build();

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

  private void setupOtelCollector(String configPath) throws Exception {
    String scriptPath = "../scripts/start_otelcol.sh";
    String test_dataPath = "../test_data";
    Process process =
        Runtime.getRuntime().exec(scriptPath + " " + test_dataPath + " " + configPath);
    process.waitFor();
  }

  public static int verify_metrics(String... parameters) throws IOException, InterruptedException {

    String SCRIPT_PATH = "../scripts/verify_metrics.sh";

    // Construct the command to execute the script with parameters
    StringBuilder command = new StringBuilder(SCRIPT_PATH);
    for (String parameter : parameters) {
      command.append(" ").append(parameter);
    }
    // Execute the command
    Process process = Runtime.getRuntime().exec(command.toString());
    return process.waitFor();
  }
}
