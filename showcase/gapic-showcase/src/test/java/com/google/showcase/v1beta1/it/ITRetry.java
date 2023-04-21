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

import static com.google.common.truth.Truth.assertThat;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.retrying.RetrySettings;
import com.google.showcase.v1beta1.CreateSequenceRequest;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.Sequence;
import com.google.showcase.v1beta1.SequenceReport;
import com.google.showcase.v1beta1.SequenceServiceClient;
import com.google.showcase.v1beta1.SequenceServiceSettings;
import com.google.showcase.v1beta1.stub.SequenceServiceStubSettings;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.junit.Test;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

public class ITRetry {
  @Test
  public void testGRPC_sequence() throws IOException {
    SequenceServiceSettings grpcSequenceSettings =
        SequenceServiceSettings.newBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                SequenceServiceStubSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .build();
    try (SequenceServiceClient grpcClient = SequenceServiceClient.create(grpcSequenceSettings)) {
      Sequence sequence = grpcClient.createSequence(CreateSequenceRequest.newBuilder().build());
      Instant now = Instant.now();
      grpcClient.attemptSequence(sequence.getName());
      String sequenceReportName = sequence.getName() + "/sequenceReport";
      SequenceReport sequenceReport = grpcClient.getSequenceReport(sequenceReportName);
      List<SequenceReport.Attempt> attemptList = sequenceReport.getAttemptsList();
      assertThat(attemptList.size()).isEqualTo(1);
      SequenceReport.Attempt attempt = attemptList.get(0);
      Instant attemptDeadlineDuration =
          Instant.ofEpochSecond(
              attempt.getAttemptDeadline().getSeconds(), attempt.getAttemptDeadline().getNanos());
      Duration duration = Duration.between(now, attemptDeadlineDuration);
      assertThat(duration.minus(Duration.ofSeconds(10))).isLessThan(Duration.ofMillis(10));
    }
  }

  @Test
  public void testHttpJson_sequence() throws IOException, GeneralSecurityException {
    SequenceServiceSettings httpJsonSequenceSettings =
        SequenceServiceSettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    try (SequenceServiceClient httpJsonClient =
        SequenceServiceClient.create(httpJsonSequenceSettings)) {
      Sequence sequence = httpJsonClient.createSequence(CreateSequenceRequest.newBuilder().build());
      Instant now = Instant.now();
      httpJsonClient.attemptSequence(sequence.getName());
      String sequenceReportName = sequence.getName() + "/sequenceReport";
      SequenceReport sequenceReport = httpJsonClient.getSequenceReport(sequenceReportName);
      List<SequenceReport.Attempt> attemptList = sequenceReport.getAttemptsList();
      assertThat(attemptList.size()).isEqualTo(1);
      SequenceReport.Attempt attempt = attemptList.get(0);
      Instant attemptDeadlineDuration =
          Instant.ofEpochSecond(
              attempt.getAttemptDeadline().getSeconds(), attempt.getAttemptDeadline().getNanos());
      Duration duration = Duration.between(now, attemptDeadlineDuration);
      assertThat(duration.minus(Duration.ofSeconds(10))).isLessThan(Duration.ofMillis(10));
    }
  }

  @Test
  public void testGRPC_customSequence() throws IOException {
    RetrySettings defaultRetrySettings =
        RetrySettings.newBuilder()
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ofMillis(2000L))
            .setTotalTimeout(Duration.ofMillis(2000L))
            .build();
    SequenceServiceStubSettings.Builder gRPCSequenceServiceStubSettings =
        SequenceServiceStubSettings.newBuilder();
    gRPCSequenceServiceStubSettings
        .attemptSequenceSettings()
        .setRetrySettings(defaultRetrySettings);
    SequenceServiceSettings grpcSequenceServiceSettings =
        SequenceServiceSettings.create(gRPCSequenceServiceStubSettings.build());
    grpcSequenceServiceSettings =
        grpcSequenceServiceSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                SequenceServiceStubSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .build();
    try (SequenceServiceClient grpcClient =
        SequenceServiceClient.create(grpcSequenceServiceSettings)) {
      Sequence sequence = grpcClient.createSequence(CreateSequenceRequest.newBuilder().build());
      Instant now = Instant.now();
      grpcClient.attemptSequence(sequence.getName());
      String sequenceReportName = sequence.getName() + "/sequenceReport";
      SequenceReport sequenceReport = grpcClient.getSequenceReport(sequenceReportName);
      List<SequenceReport.Attempt> attemptList = sequenceReport.getAttemptsList();
      assertThat(attemptList.size()).isEqualTo(1);
      SequenceReport.Attempt attempt = attemptList.get(0);
      Instant attemptDeadlineDuration =
          Instant.ofEpochSecond(
              attempt.getAttemptDeadline().getSeconds(), attempt.getAttemptDeadline().getNanos());
      Duration duration = Duration.between(now, attemptDeadlineDuration);
      assertThat(duration.minus(Duration.ofSeconds(2))).isLessThan(Duration.ofMillis(10));
    }
  }

  @Test
  public void testHttpJson_customSequence() throws IOException, GeneralSecurityException {
    RetrySettings defaultRetrySettings =
        RetrySettings.newBuilder()
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ofMillis(2000L))
            .setTotalTimeout(Duration.ofMillis(2000L))
            .build();
    SequenceServiceStubSettings.Builder httpJsonSequenceServiceStubSettings =
        SequenceServiceStubSettings.newHttpJsonBuilder();
    httpJsonSequenceServiceStubSettings
        .attemptSequenceSettings()
        .setRetrySettings(defaultRetrySettings);
    SequenceServiceSettings httpJsonSequenceSettings =
        SequenceServiceSettings.create(httpJsonSequenceServiceStubSettings.build());
    httpJsonSequenceSettings =
        httpJsonSequenceSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    try (SequenceServiceClient httpJsonClient =
        SequenceServiceClient.create(httpJsonSequenceSettings)) {
      Sequence sequence = httpJsonClient.createSequence(CreateSequenceRequest.newBuilder().build());
      Instant now = Instant.now();
      httpJsonClient.attemptSequence(sequence.getName());
      String sequenceReportName = sequence.getName() + "/sequenceReport";
      SequenceReport sequenceReport = httpJsonClient.getSequenceReport(sequenceReportName);
      List<SequenceReport.Attempt> attemptList = sequenceReport.getAttemptsList();
      assertThat(attemptList.size()).isEqualTo(1);
      SequenceReport.Attempt attempt = attemptList.get(0);
      Instant attemptDeadlineDuration =
          Instant.ofEpochSecond(
              attempt.getAttemptDeadline().getSeconds(), attempt.getAttemptDeadline().getNanos());
      Duration duration = Duration.between(now, attemptDeadlineDuration);
      assertThat(duration.minus(Duration.ofSeconds(2))).isLessThan(Duration.ofMillis(10));
    }
  }

  //  // Assuming that jitter sets the retry delay to the max possible value:
  //  // Attempt #  | Milli Start Time  | Milli End Time  | Retry Delay | RPC Timeout
  //  // 1          | 0                 | 500             | 200         | 500
  //  // 2 (Retry)  | 700               | 1700            | 400         | 1000
  //  // 3 (Retry)  | 2100              | 4000            | 500 (cap)   | 1900
  //  @Test
  //  public void
  //  testHttpJson_unaryCallableRetry_exceedsDefaultTimeout_throwsDeadlineExceededException() {
  //    RetrySettings defaultRetrySettings =
  //            RetrySettings.newBuilder()
  //                    .setInitialRetryDelay(Duration.ofMillis(200L))
  //                    .setRetryDelayMultiplier(2.0)
  //                    .setMaxRetryDelay(Duration.ofMillis(500L))
  //                    .setInitialRpcTimeout(Duration.ofMillis(500L))
  //                    .setRpcTimeoutMultiplier(2.0)
  //                    .setMaxRpcTimeout(Duration.ofMillis(2000L))
  //                    .setTotalTimeout(Duration.ofMillis(4000L))
  //                    .setJittered(false)
  //                    .build();
  //    EchoStubSettings.Builder httpJsonEchoSettingsBuilder =
  // EchoStubSettings.newHttpJsonBuilder();
  //    // Manually set DEADLINE_EXCEEDED as showcase tests do not have that as a retryable code
  //    httpJsonEchoSettingsBuilder
  //            .blockSettings()
  //            .setRetrySettings(defaultRetrySettings)
  //            .setRetryableCodes(StatusCode.Code.DEADLINE_EXCEEDED);
  //    EchoSettings httpJsonEchoSettings =
  // EchoSettings.create(httpJsonEchoSettingsBuilder.build());
  //    httpJsonEchoSettings =
  //            httpJsonEchoSettings
  //                    .toBuilder()
  //                    .setCredentialsProvider(NoCredentialsProvider.create())
  //                    .setTransportChannelProvider(
  //                            EchoSettings.defaultHttpJsonTransportProviderBuilder()
  //                                    .setHttpTransport(
  //                                            new
  // NetHttpTransport.Builder().doNotValidateCertificate().build())
  //                                    .setEndpoint("http://localhost:7469")
  //                                    .build())
  //                    .build();
  //    try (EchoClient httpJsonClient = EchoClient.create(httpJsonEchoSettings)) {
  //      BlockRequest blockRequest =
  //              BlockRequest.newBuilder()
  //                      .setSuccess(
  //
  // BlockResponse.newBuilder().setContent("httpjsonBlockContent_3sDelay_Retry"))
  //
  // .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(3).build())
  //                      .build();
  //      RetryingFuture<BlockResponse> retryingFuture =
  //              (RetryingFuture<BlockResponse>)
  // httpJsonClient.blockCallable().futureCall(blockRequest);
  //      ExecutionException exception =
  //              assertThrows(ExecutionException.class, () -> retryingFuture.get(10,
  // TimeUnit.SECONDS));
  //      assertThat(exception.getCause()).isInstanceOf(DeadlineExceededException.class);
  //      DeadlineExceededException deadlineExceededException =
  //              (DeadlineExceededException) exception.getCause();
  //      assertThat(deadlineExceededException.getStatusCode().getCode())
  //              .isEqualTo(StatusCode.Code.DEADLINE_EXCEEDED);
  //      int attemptCount = retryingFuture.getAttemptSettings().getAttemptCount() + 1;
  //      assertThat(attemptCount).isEqualTo(3);
  //      httpJsonClient.awaitTermination(10, TimeUnit.SECONDS);
  //    }
  //  }
  //
  //  // The purpose of this test is to ensure that the deadlineScheduleExecutor is able
  //  // to properly cancel the HttpRequest for each retry attempt. This test attempts to
  //  // make a call every 100ms for 10 seconds. If the requestRunnable blocks until we
  //  // receive a response from the server (200ms) regardless of it was cancelled, then
  //  // we would expect at most 50 responses.
  //  @Test
  //  public void testHttpJson_unaryCallableRetry_deadlineExecutorTimesOutRequest()
  //          throws IOException, GeneralSecurityException, InterruptedException {
  //    RetrySettings defaultRetrySettings =
  //            RetrySettings.newBuilder()
  //                    .setInitialRpcTimeout(Duration.ofMillis(100L))
  //                    .setRpcTimeoutMultiplier(1.0)
  //                    .setMaxRpcTimeout(Duration.ofMillis(100L))
  //                    .setTotalTimeout(Duration.ofMillis(10000L))
  //                    .setJittered(false)
  //                    .build();
  //    EchoStubSettings.Builder httpJsonEchoSettingsBuilder =
  // EchoStubSettings.newHttpJsonBuilder();
  //    // Manually set DEADLINE_EXCEEDED as showcase tests do not have that as a retryable code
  //    httpJsonEchoSettingsBuilder
  //            .blockSettings()
  //            .setRetrySettings(defaultRetrySettings)
  //            .setRetryableCodes(StatusCode.Code.DEADLINE_EXCEEDED);
  //    EchoSettings httpJsonEchoSettings =
  // EchoSettings.create(httpJsonEchoSettingsBuilder.build());
  //    httpJsonEchoSettings =
  //            httpJsonEchoSettings
  //                    .toBuilder()
  //                    .setCredentialsProvider(NoCredentialsProvider.create())
  //                    .setTransportChannelProvider(
  //                            EchoSettings.defaultHttpJsonTransportProviderBuilder()
  //                                    .setHttpTransport(
  //                                            new
  // NetHttpTransport.Builder().doNotValidateCertificate().build())
  //                                    .setEndpoint("http://localhost:7469")
  //                                    .build())
  //                    .build();
  //    try (EchoClient httpJsonClient = EchoClient.create(httpJsonEchoSettings)) {
  //      BlockRequest blockRequest =
  //              BlockRequest.newBuilder()
  //                      .setSuccess(
  //
  // BlockResponse.newBuilder().setContent("httpjsonBlockContent_200msDelay_Retry"))
  //                      // Set the timeout to be longer than the RPC timeout
  //                      .setResponseDelay(
  //
  // com.google.protobuf.Duration.newBuilder().setNanos(200000000).build())
  //                      .build();
  //      RetryingFuture<BlockResponse> retryingFuture =
  //              (RetryingFuture<BlockResponse>)
  // httpJsonClient.blockCallable().futureCall(blockRequest);
  //      ExecutionException exception =
  //              assertThrows(ExecutionException.class, () -> retryingFuture.get(20,
  // TimeUnit.SECONDS));
  //      assertThat(exception.getCause()).isInstanceOf(DeadlineExceededException.class);
  //      DeadlineExceededException deadlineExceededException =
  //              (DeadlineExceededException) exception.getCause();
  //      assertThat(deadlineExceededException.getStatusCode().getCode())
  //              .isEqualTo(StatusCode.Code.DEADLINE_EXCEEDED);
  //      // We cannot guarantee the number of attempts. The RetrySettings should be configured
  //      // such that there is no delay between the attempts, but the execution takes time
  //      // to run. Theoretically this should run exactly 100 times.
  //      int attemptCount = retryingFuture.getAttemptSettings().getAttemptCount() + 1;
  //      assertThat(attemptCount).isGreaterThan(80);
  //      assertThat(attemptCount).isAtMost(100);
  //      httpJsonClient.awaitTermination(10, TimeUnit.SECONDS);
  //    }
  //  }
}
