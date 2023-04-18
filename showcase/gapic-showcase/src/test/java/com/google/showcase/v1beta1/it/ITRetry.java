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
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.retrying.RetryingFuture;
import com.google.api.gax.rpc.DeadlineExceededException;
import com.google.api.gax.rpc.StatusCode;
import com.google.showcase.v1beta1.BlockRequest;
import com.google.showcase.v1beta1.BlockResponse;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Test;
import org.threeten.bp.Duration;

/**
 * For this test, we test a combination of various retry situations and try to ensure that the calls
 * are: - being retried if needed/ not retried if set not to be retried - respecting the timeouts
 * set by the customer - cancelled when timeouts have exceeded their limits
 *
 * <p>Each test attempts to get the number of attempts done in each call. The attemptCount is
 * incremented by 1 as the first attempt is zero indexed.
 */
public class ITRetry {

  @Test
  public void testGRPC_unaryCallableNoRetry()
      throws IOException, ExecutionException, InterruptedException, TimeoutException {
    RetrySettings defaultNoRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRpcTimeout(Duration.ofMillis(5000L))
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ofMillis(5000L))
            .setTotalTimeout(Duration.ofMillis(5000L))
            // Explicitly set retries as disabled (maxAttempts == 1)
            .setMaxAttempts(1)
            .build();
    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    grpcEchoSettingsBuilder.blockSettings().setRetrySettings(defaultNoRetrySettings);
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
      BlockRequest blockRequest =
          BlockRequest.newBuilder()
              .setSuccess(BlockResponse.newBuilder().setContent("gRPCBlockContent_3sDelay_noRetry"))
              .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(3).build())
              .build();
      RetryingFuture<BlockResponse> retryingFuture =
          (RetryingFuture<BlockResponse>) grpcClient.blockCallable().futureCall(blockRequest);
      BlockResponse blockResponse = retryingFuture.get(10, TimeUnit.SECONDS);
      assertThat(blockResponse.getContent()).isEqualTo("gRPCBlockContent_3sDelay_noRetry");
      // We can guarantee that this only runs once
      int attemptCount = retryingFuture.getAttemptSettings().getAttemptCount() + 1;
      assertThat(attemptCount).isEqualTo(1);
    }
  }

  @Test
  public void testHttpJson_unaryCallableNoRetry()
      throws IOException, GeneralSecurityException, ExecutionException, InterruptedException,
          TimeoutException {
    RetrySettings defaultNoRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRpcTimeout(Duration.ofMillis(5000L))
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ofMillis(5000L))
            .setTotalTimeout(Duration.ofMillis(5000L))
            // Explicitly set retries as disabled (maxAttempts == 1)
            .setMaxAttempts(1)
            .build();
    EchoStubSettings.Builder httpJsonEchoSettingsBuilder = EchoStubSettings.newHttpJsonBuilder();
    httpJsonEchoSettingsBuilder.blockSettings().setRetrySettings(defaultNoRetrySettings);
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
      BlockRequest blockRequest =
          BlockRequest.newBuilder()
              .setSuccess(
                  BlockResponse.newBuilder().setContent("httpjsonBlockContent_3sDelay_noRetry"))
              .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(3).build())
              .build();
      RetryingFuture<BlockResponse> retryingFuture =
          (RetryingFuture<BlockResponse>) httpJsonClient.blockCallable().futureCall(blockRequest);
      BlockResponse blockResponse = retryingFuture.get(10, TimeUnit.SECONDS);
      assertThat(blockResponse.getContent()).isEqualTo("httpjsonBlockContent_3sDelay_noRetry");
      // We can guarantee that this only runs once
      int attemptCount = retryingFuture.getAttemptSettings().getAttemptCount() + 1;
      assertThat(attemptCount).isEqualTo(1);
    }
  }

  // Retry is configured by setting the initial RPC timeout (1.5s) to be less than
  // the RPC delay (2s). The next RPC timeout (3s) will wait long enough for the delay.
  @Test
  public void testGRPC_unaryCallableRetry()
      throws IOException, ExecutionException, InterruptedException, TimeoutException {
    RetrySettings defaultRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRetryDelay(Duration.ofMillis(200L))
            .setRetryDelayMultiplier(2.0)
            .setMaxRetryDelay(Duration.ofMillis(500L))
            .setInitialRpcTimeout(Duration.ofMillis(1500L))
            .setRpcTimeoutMultiplier(2.0)
            .setMaxRpcTimeout(Duration.ofMillis(3000L))
            .setTotalTimeout(Duration.ofMillis(5000L))
            .build();
    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    // Manually set DEADLINE_EXCEEDED as showcase tests do not have that as a retryable code
    grpcEchoSettingsBuilder
        .blockSettings()
        .setRetrySettings(defaultRetrySettings)
        .setRetryableCodes(StatusCode.Code.DEADLINE_EXCEEDED);
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
      BlockRequest blockRequest =
          BlockRequest.newBuilder()
              .setSuccess(BlockResponse.newBuilder().setContent("gRPCBlockContent_2sDelay_Retry"))
              .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(2).build())
              .build();
      RetryingFuture<BlockResponse> retryingFuture =
          (RetryingFuture<BlockResponse>) grpcClient.blockCallable().futureCall(blockRequest);
      BlockResponse blockResponse = retryingFuture.get(10, TimeUnit.SECONDS);
      assertThat(blockResponse.getContent()).isEqualTo("gRPCBlockContent_2sDelay_Retry");
      // We can guarantee that this only runs once
      int attemptCount = retryingFuture.getAttemptSettings().getAttemptCount() + 1;
      assertThat(attemptCount).isEqualTo(2);
    }
  }

  // Retry is configured by setting the initial RPC timeout (1.5s) to be less than
  // the RPC delay (2s). The next RPC timeout (3s) will wait long enough for the delay.
  @Test
  public void testHttpJson_unaryCallableRetry()
      throws IOException, GeneralSecurityException, ExecutionException, InterruptedException,
          TimeoutException {
    RetrySettings defaultRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRetryDelay(Duration.ofMillis(200L))
            .setRetryDelayMultiplier(2.0)
            .setMaxRetryDelay(Duration.ofMillis(500L))
            .setInitialRpcTimeout(Duration.ofMillis(1500L))
            .setRpcTimeoutMultiplier(2.0)
            .setMaxRpcTimeout(Duration.ofMillis(3000L))
            .setTotalTimeout(Duration.ofMillis(5000L))
            .build();
    EchoStubSettings.Builder httpJsonEchoSettingsBuilder = EchoStubSettings.newHttpJsonBuilder();
    // Manually set DEADLINE_EXCEEDED as showcase tests do not have that as a retryable code
    httpJsonEchoSettingsBuilder
        .blockSettings()
        .setRetrySettings(defaultRetrySettings)
        .setRetryableCodes(StatusCode.Code.DEADLINE_EXCEEDED);
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
      BlockRequest blockRequest =
          BlockRequest.newBuilder()
              .setSuccess(
                  BlockResponse.newBuilder().setContent("httpjsonBlockContent_2sDelay_Retry"))
              .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(2).build())
              .build();
      RetryingFuture<BlockResponse> retryingFuture =
          (RetryingFuture<BlockResponse>) httpJsonClient.blockCallable().futureCall(blockRequest);
      BlockResponse blockResponse = retryingFuture.get(10, TimeUnit.SECONDS);
      assertThat(blockResponse.getContent()).isEqualTo("httpjsonBlockContent_2sDelay_Retry");
      // We can guarantee that this only runs once
      int attemptCount = retryingFuture.getAttemptSettings().getAttemptCount() + 1;
      assertThat(attemptCount).isEqualTo(2);
    }
  }

  // Request is set to block for 6 seconds to allow the RPC to timeout. If retries are
  // disabled, the RPC timeout is set to be the totalTimeout (5s).
  @Test
  public void testGRPC_unaryCallableNoRetry_exceedsDefaultTimeout_throwsDeadlineExceededException()
      throws IOException {
    RetrySettings defaultNoRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRpcTimeout(Duration.ofMillis(5000L))
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ofMillis(5000L))
            .setTotalTimeout(Duration.ofMillis(5000L))
            // Explicitly set retries as disabled (maxAttempts == 1)
            .setMaxAttempts(1)
            .build();
    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    grpcEchoSettingsBuilder.blockSettings().setRetrySettings(defaultNoRetrySettings);
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
      BlockRequest blockRequest =
          BlockRequest.newBuilder()
              .setSuccess(BlockResponse.newBuilder().setContent("gRPCBlockContent_6sDelay_noRetry"))
              .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(6).build())
              .build();
      RetryingFuture<BlockResponse> retryingFuture =
          (RetryingFuture<BlockResponse>) grpcClient.blockCallable().futureCall(blockRequest);
      ExecutionException exception =
          assertThrows(ExecutionException.class, () -> retryingFuture.get(10, TimeUnit.SECONDS));
      assertThat(exception.getCause()).isInstanceOf(DeadlineExceededException.class);
      DeadlineExceededException deadlineExceededException =
          (DeadlineExceededException) exception.getCause();
      assertThat(deadlineExceededException.getStatusCode().getCode())
          .isEqualTo(StatusCode.Code.DEADLINE_EXCEEDED);
      // We can guarantee that this only runs once
      int attemptCount = retryingFuture.getAttemptSettings().getAttemptCount() + 1;
      assertThat(attemptCount).isEqualTo(1);
    }
  }

  // Request is set to block for 6 seconds to allow the RPC to timeout. If retries are
  // disabled, the RPC timeout is set to be the totalTimeout (5s).
  @Test
  public void
      testHttpJson_unaryCallableNoRetry_exceedsDefaultTimeout_throwsDeadlineExceededException()
          throws IOException, GeneralSecurityException {
    RetrySettings defaultNoRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRpcTimeout(Duration.ofMillis(5000L))
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ofMillis(5000L))
            .setTotalTimeout(Duration.ofMillis(5000L))
            // Explicitly set retries as disabled (maxAttempts == 1)
            .setMaxAttempts(1)
            .build();
    EchoStubSettings.Builder httpJsonEchoSettingsBuilder = EchoStubSettings.newHttpJsonBuilder();
    httpJsonEchoSettingsBuilder.blockSettings().setRetrySettings(defaultNoRetrySettings);
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
      BlockRequest blockRequest =
          BlockRequest.newBuilder()
              .setSuccess(
                  BlockResponse.newBuilder().setContent("httpjsonBlockContent_6sDelay_noRetry"))
              .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(6).build())
              .build();
      RetryingFuture<BlockResponse> retryingFuture =
          (RetryingFuture<BlockResponse>) httpJsonClient.blockCallable().futureCall(blockRequest);
      ExecutionException exception =
          assertThrows(ExecutionException.class, () -> retryingFuture.get(10, TimeUnit.SECONDS));
      assertThat(exception.getCause()).isInstanceOf(DeadlineExceededException.class);
      DeadlineExceededException deadlineExceededException =
          (DeadlineExceededException) exception.getCause();
      assertThat(deadlineExceededException.getStatusCode().getCode())
          .isEqualTo(StatusCode.Code.DEADLINE_EXCEEDED);
      // We can guarantee that this only runs once
      int attemptCount = retryingFuture.getAttemptSettings().getAttemptCount() + 1;
      assertThat(attemptCount).isEqualTo(1);
    }
  }

  // Assuming that jitter sets the retry delay to the max possible value:
  // Attempt #  | Milli Start Time  | Milli End Time  | Retry Delay | RPC Timeout
  // 1          | 0                 | 500             | 200         | 500
  // 2 (Retry)  | 700               | 1700            | 400         | 1000
  // 3 (Retry)  | 2100              | 4100            | 500 (cap)   | 1900
  @Test
  public void testGRPC_unaryCallableRetry_exceedsDefaultTimeout_throwsDeadlineExceededException()
      throws IOException {
    RetrySettings defaultRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRetryDelay(Duration.ofMillis(200L))
            .setRetryDelayMultiplier(2.0)
            .setMaxRetryDelay(Duration.ofMillis(500L))
            .setInitialRpcTimeout(Duration.ofMillis(500L))
            .setRpcTimeoutMultiplier(2.0)
            .setMaxRpcTimeout(Duration.ofMillis(2000L))
            .setTotalTimeout(Duration.ofMillis(4000L))
            .build();
    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    // Manually set DEADLINE_EXCEEDED as showcase tests do not have that as a retryable code
    grpcEchoSettingsBuilder
        .blockSettings()
        .setRetrySettings(defaultRetrySettings)
        .setRetryableCodes(StatusCode.Code.DEADLINE_EXCEEDED);
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
      BlockRequest blockRequest =
          BlockRequest.newBuilder()
              .setSuccess(BlockResponse.newBuilder().setContent("gRPCBlockContent_3sDelay_Retry"))
              .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(3).build())
              .build();
      RetryingFuture<BlockResponse> retryingFuture =
          (RetryingFuture<BlockResponse>) grpcClient.blockCallable().futureCall(blockRequest);
      ExecutionException exception =
          assertThrows(ExecutionException.class, () -> retryingFuture.get(10, TimeUnit.SECONDS));
      assertThat(exception.getCause()).isInstanceOf(DeadlineExceededException.class);
      DeadlineExceededException deadlineExceededException =
          (DeadlineExceededException) exception.getCause();
      assertThat(deadlineExceededException.getStatusCode().getCode())
          .isEqualTo(StatusCode.Code.DEADLINE_EXCEEDED);
      // Due to jitter, we cannot guarantee the number of attempts.
      // The RetrySettings should be configured such that there should always
      // 2 - 4 overall attempts
      int attemptCount = retryingFuture.getAttemptSettings().getAttemptCount() + 1;
      assertThat(attemptCount).isGreaterThan(1);
      assertThat(attemptCount).isLessThan(5);
    }
  }

  // Assuming that jitter sets the retry delay to the max possible value:
  // Attempt #  | Milli Start Time  | Milli End Time  | Retry Delay | RPC Timeout
  // 1          | 0                 | 500             | 200         | 500
  // 2 (Retry)  | 700               | 1700            | 400         | 1000
  // 3 (Retry)  | 2100              | 4100            | 500 (cap)   | 1900
  @Test
  public void
      testHttpJson_unaryCallableRetry_exceedsDefaultTimeout_throwsDeadlineExceededException()
          throws IOException, GeneralSecurityException {
    RetrySettings defaultRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRetryDelay(Duration.ofMillis(200L))
            .setRetryDelayMultiplier(2.0)
            .setMaxRetryDelay(Duration.ofMillis(500L))
            .setInitialRpcTimeout(Duration.ofMillis(500L))
            .setRpcTimeoutMultiplier(2.0)
            .setMaxRpcTimeout(Duration.ofMillis(2000L))
            .setTotalTimeout(Duration.ofMillis(4000L))
            .build();
    EchoStubSettings.Builder httpJsonEchoSettingsBuilder = EchoStubSettings.newHttpJsonBuilder();
    // Manually set DEADLINE_EXCEEDED as showcase tests do not have that as a retryable code
    httpJsonEchoSettingsBuilder
        .blockSettings()
        .setRetrySettings(defaultRetrySettings)
        .setRetryableCodes(StatusCode.Code.DEADLINE_EXCEEDED);
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
      BlockRequest blockRequest =
          BlockRequest.newBuilder()
              .setSuccess(
                  BlockResponse.newBuilder().setContent("httpjsonBlockContent_5sDelay_Retry"))
              .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(3).build())
              .build();
      RetryingFuture<BlockResponse> retryingFuture =
          (RetryingFuture<BlockResponse>) httpJsonClient.blockCallable().futureCall(blockRequest);
      ExecutionException exception =
          assertThrows(ExecutionException.class, () -> retryingFuture.get(10, TimeUnit.SECONDS));
      assertThat(exception.getCause()).isInstanceOf(DeadlineExceededException.class);
      DeadlineExceededException deadlineExceededException =
          (DeadlineExceededException) exception.getCause();
      assertThat(deadlineExceededException.getStatusCode().getCode())
          .isEqualTo(StatusCode.Code.DEADLINE_EXCEEDED);
      // Due to jitter, we cannot guarantee the number of attempts.
      // The RetrySettings should be configured such that there should always
      // 2 - 4 overall attempts
      int attemptCount = retryingFuture.getAttemptSettings().getAttemptCount() + 1;
      assertThat(attemptCount).isGreaterThan(1);
      assertThat(attemptCount).isLessThan(5);
    }
  }

  // The purpose of this test is to ensure that the deadlineScheduleExecutor is able
  // to properly cancel the HttpRequest for each retry attempt. This test attempts to
  // make a call every 100ms for 500ms. If the requestRunnable blocks until we
  // receive a response from the server (1s) regardless of it was cancelled, then
  // we would not expect a response at all.
  @Test
  public void testHttpJson_unaryCallableRetry_multipleCancellationsViaDeadlineExecutor()
      throws IOException, GeneralSecurityException {
    RetrySettings defaultRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRpcTimeout(Duration.ofMillis(100L))
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ofMillis(100L))
            .setTotalTimeout(Duration.ofMillis(500L))
            .build();
    EchoStubSettings.Builder httpJsonEchoSettingsBuilder = EchoStubSettings.newHttpJsonBuilder();
    // Manually set DEADLINE_EXCEEDED as showcase tests do not have that as a retryable code
    httpJsonEchoSettingsBuilder
        .blockSettings()
        .setRetrySettings(defaultRetrySettings)
        .setRetryableCodes(StatusCode.Code.DEADLINE_EXCEEDED);
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
      BlockRequest blockRequest =
          BlockRequest.newBuilder()
              .setSuccess(
                  BlockResponse.newBuilder().setContent("httpjsonBlockContent_5sDelay_Retry"))
              // Set the timeout to be longer than the RPC timeout
              .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(5).build())
              .build();
      RetryingFuture<BlockResponse> retryingFuture =
          (RetryingFuture<BlockResponse>) httpJsonClient.blockCallable().futureCall(blockRequest);
      ExecutionException exception =
          assertThrows(ExecutionException.class, () -> retryingFuture.get(10, TimeUnit.SECONDS));
      assertThat(exception.getCause()).isInstanceOf(DeadlineExceededException.class);
      DeadlineExceededException deadlineExceededException =
          (DeadlineExceededException) exception.getCause();
      assertThat(deadlineExceededException.getStatusCode().getCode())
          .isEqualTo(StatusCode.Code.DEADLINE_EXCEEDED);
      // We cannot guarantee the number of attempts. The RetrySettings should be configured
      // such that there is no delay between the attempts, but the execution takes time
      // to run. Theoretically this should run exactly 10 times.
      int attemptCount = retryingFuture.getAttemptSettings().getAttemptCount() + 1;
      assertThat(attemptCount).isGreaterThan(0);
      assertThat(attemptCount).isAtMost(5);
    }
  }
}
