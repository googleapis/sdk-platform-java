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
import static org.junit.Assert.assertThrows;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.longrunning.OperationSnapshot;
import com.google.api.gax.longrunning.OperationTimedPollAlgorithm;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.UnaryCallSettings;
import com.google.protobuf.Timestamp;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.WaitMetadata;
import com.google.showcase.v1beta1.WaitRequest;
import com.google.showcase.v1beta1.WaitResponse;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import org.junit.Test;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;
import org.threeten.bp.temporal.ChronoUnit;

/**
 * For this test, we test a combination of various LRO RetrySettings and try to ensure that the
 * calls are polling correctly. Each test attempts to test the number of attempts done in each call.
 * This is done by ignoring the jitter factor to normalize the results for each test.
 */
public class ITLongRunningOperation {

  @Test
  public void testGRPC_LROSuccessfulResponse_NoDeadlineExceeded()
      throws IOException, ExecutionException, InterruptedException {
    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    grpcEchoSettingsBuilder
        .waitOperationSettings()
        .setInitialCallSettings(
            UnaryCallSettings.<WaitRequest, OperationSnapshot>newUnaryCallSettingsBuilder()
                .setRetrySettings(
                    RetrySettings.newBuilder()
                        .setInitialRpcTimeout(Duration.ofMillis(1000L))
                        .setRpcTimeoutMultiplier(1.0)
                        .setMaxRpcTimeout(Duration.ofMillis(1000L))
                        .setTotalTimeout(Duration.ofMillis(1000L))
                        .build())
                .build())
        .setPollingAlgorithm(
            OperationTimedPollAlgorithm.create(
                RetrySettings.newBuilder()
                    .setInitialRetryDelay(Duration.ofMillis(1000L))
                    .setRetryDelayMultiplier(2.0)
                    .setMaxRetryDelay(Duration.ofMillis(5000L))
                    .setInitialRpcTimeout(Duration.ZERO)
                    .setRpcTimeoutMultiplier(1.0)
                    .setMaxRpcTimeout(Duration.ZERO)
                    .setTotalTimeout(Duration.ofMillis(15000L))
                    .setJittered(false)
                    .build()));
    EchoSettings grpcEchoSettings = EchoSettings.create(grpcEchoSettingsBuilder.build());
    grpcEchoSettings =
        grpcEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .build();
    try (EchoClient grpcClient = EchoClient.create(grpcEchoSettings)) {
      long epochSecondsInFuture = Instant.now().plus(5, ChronoUnit.SECONDS).getEpochSecond();
      WaitRequest waitRequest =
          WaitRequest.newBuilder()
              .setSuccess(WaitResponse.newBuilder().setContent("gRPCWaitContent_5sDelay_noRetry"))
              .setEndTime(Timestamp.newBuilder().setSeconds(epochSecondsInFuture).build())
              .build();
      OperationFuture<WaitResponse, WaitMetadata> operationFuture =
          grpcClient.waitOperationCallable().futureCall(waitRequest);
      WaitResponse waitResponse = operationFuture.get();
      assertThat(waitResponse.getContent()).isEqualTo("gRPCWaitContent_5sDelay_noRetry");
      int attemptCount = operationFuture.getPollingFuture().getAttemptSettings().getAttemptCount();
      assertThat(attemptCount).isEqualTo(3);
    }
  }

  @Test
  public void testHttpJson_LROSuccessfulResponse_NoDeadlineExceeded()
      throws IOException, GeneralSecurityException, ExecutionException, InterruptedException {
    EchoStubSettings.Builder httpJsonEchoSettingsBuilder = EchoStubSettings.newHttpJsonBuilder();
    httpJsonEchoSettingsBuilder
        .waitOperationSettings()
        .setInitialCallSettings(
            UnaryCallSettings.<WaitRequest, OperationSnapshot>newUnaryCallSettingsBuilder()
                .setRetrySettings(
                    RetrySettings.newBuilder()
                        .setInitialRpcTimeout(Duration.ofMillis(1000L))
                        .setRpcTimeoutMultiplier(1.0)
                        .setMaxRpcTimeout(Duration.ofMillis(1000L))
                        .setTotalTimeout(Duration.ofMillis(1000L))
                        .build())
                .build())
        .setPollingAlgorithm(
            OperationTimedPollAlgorithm.create(
                RetrySettings.newBuilder()
                    .setInitialRetryDelay(Duration.ofMillis(1000L))
                    .setRetryDelayMultiplier(2.0)
                    .setMaxRetryDelay(Duration.ofMillis(5000L))
                    .setInitialRpcTimeout(Duration.ZERO)
                    .setRpcTimeoutMultiplier(1.0)
                    .setMaxRpcTimeout(Duration.ZERO)
                    .setTotalTimeout(Duration.ofMillis(15000L))
                    .setJittered(false)
                    .build()));
    EchoSettings httpJsonEchoSettings = EchoSettings.create(httpJsonEchoSettingsBuilder.build());
    httpJsonEchoSettings =
        httpJsonEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    try (EchoClient httpJsonClient = EchoClient.create(httpJsonEchoSettings)) {
      long epochSecondsInFuture = Instant.now().plus(5, ChronoUnit.SECONDS).getEpochSecond();
      WaitRequest waitRequest =
          WaitRequest.newBuilder()
              .setSuccess(
                  WaitResponse.newBuilder().setContent("httpjsonWaitContent_5sDelay_noRetry"))
              .setEndTime(Timestamp.newBuilder().setSeconds(epochSecondsInFuture).build())
              .build();
      OperationFuture<WaitResponse, WaitMetadata> operationFuture =
          httpJsonClient.waitOperationCallable().futureCall(waitRequest);
      WaitResponse waitResponse = operationFuture.get();
      assertThat(waitResponse.getContent()).isEqualTo("httpjsonWaitContent_5sDelay_noRetry");
      int attemptCount = operationFuture.getPollingFuture().getAttemptSettings().getAttemptCount();
      assertThat(attemptCount).isEqualTo(3);
    }
  }

  @Test
  public void testGRPC_LROUnsuccessfulResponse_exceedsTotalTimeout_throwsDeadlineExceededException()
      throws IOException {
    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    grpcEchoSettingsBuilder
        .waitOperationSettings()
        .setInitialCallSettings(
            UnaryCallSettings.<WaitRequest, OperationSnapshot>newUnaryCallSettingsBuilder()
                .setRetrySettings(
                    RetrySettings.newBuilder()
                        .setInitialRpcTimeout(Duration.ofMillis(1000L))
                        .setRpcTimeoutMultiplier(1.0)
                        .setMaxRpcTimeout(Duration.ofMillis(1000L))
                        .setTotalTimeout(Duration.ofMillis(3000L))
                        .build())
                .build())
        .setPollingAlgorithm(
            OperationTimedPollAlgorithm.create(
                RetrySettings.newBuilder()
                    .setInitialRetryDelay(Duration.ofMillis(3000L))
                    .setRetryDelayMultiplier(2.0)
                    .setMaxRetryDelay(Duration.ofMillis(5000L))
                    .setInitialRpcTimeout(Duration.ZERO)
                    .setRpcTimeoutMultiplier(1.0)
                    .setMaxRpcTimeout(Duration.ZERO)
                    .setTotalTimeout(Duration.ofMillis(10000L))
                    .setJittered(false)
                    .build()));
    EchoSettings grpcEchoSettings = EchoSettings.create(grpcEchoSettingsBuilder.build());
    grpcEchoSettings =
        grpcEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .build();
    try (EchoClient grpcClient = EchoClient.create(grpcEchoSettings)) {
      long epochSecondsInFuture = Instant.now().plus(10, ChronoUnit.SECONDS).getEpochSecond();
      WaitRequest waitRequest =
          WaitRequest.newBuilder()
              .setSuccess(WaitResponse.newBuilder().setContent("gRPCWaitContent_10sDelay_noRetry"))
              .setEndTime(Timestamp.newBuilder().setSeconds(epochSecondsInFuture).build())
              .build();
      OperationFuture<WaitResponse, WaitMetadata> operationFuture =
          grpcClient.waitOperationCallable().futureCall(waitRequest);
      assertThrows(CancellationException.class, operationFuture::get);
      int attemptCount = operationFuture.getPollingFuture().getAttemptSettings().getAttemptCount();
      assertThat(attemptCount).isEqualTo(2);
    }
  }

  @Test
  public void
      testHttpJson_LROUnsuccessfulResponse_exceedsTotalTimeout_throwsDeadlineExceededException()
          throws IOException, GeneralSecurityException {
    EchoStubSettings.Builder httpJsonEchoSettingsBuilder = EchoStubSettings.newHttpJsonBuilder();
    httpJsonEchoSettingsBuilder
        .waitOperationSettings()
        .setInitialCallSettings(
            UnaryCallSettings.<WaitRequest, OperationSnapshot>newUnaryCallSettingsBuilder()
                .setRetrySettings(
                    RetrySettings.newBuilder()
                        .setInitialRpcTimeout(Duration.ofMillis(1000L))
                        .setRpcTimeoutMultiplier(1.0)
                        .setMaxRpcTimeout(Duration.ofMillis(1000L))
                        .setTotalTimeout(Duration.ofMillis(3000L))
                        .build())
                .build())
        .setPollingAlgorithm(
            OperationTimedPollAlgorithm.create(
                RetrySettings.newBuilder()
                    .setInitialRetryDelay(Duration.ofMillis(3000L))
                    .setRetryDelayMultiplier(2.0)
                    .setMaxRetryDelay(Duration.ofMillis(5000L))
                    .setInitialRpcTimeout(Duration.ZERO)
                    .setRpcTimeoutMultiplier(1.0)
                    .setMaxRpcTimeout(Duration.ZERO)
                    .setTotalTimeout(Duration.ofMillis(10000L))
                    .setJittered(false)
                    .build()));
    EchoSettings httpJsonEchoSettings = EchoSettings.create(httpJsonEchoSettingsBuilder.build());
    httpJsonEchoSettings =
        httpJsonEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    try (EchoClient httpJsonClient = EchoClient.create(httpJsonEchoSettings)) {
      long epochSecondsInFuture = Instant.now().plus(10, ChronoUnit.SECONDS).getEpochSecond();
      WaitRequest waitRequest =
          WaitRequest.newBuilder()
              .setSuccess(
                  WaitResponse.newBuilder().setContent("httpjsonWaitContent_10sDelay_noRetry"))
              .setEndTime(Timestamp.newBuilder().setSeconds(epochSecondsInFuture).build())
              .build();
      OperationFuture<WaitResponse, WaitMetadata> operationFuture =
          httpJsonClient.waitOperationCallable().futureCall(waitRequest);
      assertThrows(CancellationException.class, operationFuture::get);
      int attemptCount = operationFuture.getPollingFuture().getAttemptSettings().getAttemptCount();
      assertThat(attemptCount).isEqualTo(2);
    }
  }
}
