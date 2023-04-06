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
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.DeadlineExceededException;
import com.google.api.gax.rpc.StatusCode;
import com.google.showcase.v1beta1.BlockRequest;
import com.google.showcase.v1beta1.BlockResponse;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import io.grpc.ManagedChannelBuilder;
import org.junit.Test;
import org.threeten.bp.Duration;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

public class ITRetry {

  // Request is set to block for 6 seconds to allow the RPC to timeout. If retries are
  // disabled, the RPC timeout is set to be the totalTimeout (5s).
  @Test
  public void testGRPC_unaryCallableNoRetry_exceedsDefaultTimeout_throwsDeadlineExceededException() throws IOException {
    RetrySettings defaultNoRetrySettings = RetrySettings.newBuilder()
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
    grpcEchoSettings = grpcEchoSettings.toBuilder()
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
      DeadlineExceededException exception =
              assertThrows(DeadlineExceededException.class, () -> grpcClient.block(blockRequest));
      assertThat(exception.getStatusCode().getCode()).isEqualTo(StatusCode.Code.DEADLINE_EXCEEDED);
    }
  }

  // Request is set to block for 6 seconds to allow the RPC to timeout. If retries are
  // disabled, the RPC timeout is set to be the totalTimeout (5s).
  @Test
  public void testHttpJson_unaryCallableNoRetry_exceedsDefaultTimeout_throwsDeadlineExceededException() throws IOException, GeneralSecurityException {
    RetrySettings defaultNoRetrySettings = RetrySettings.newBuilder()
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
    httpJsonEchoSettings = httpJsonEchoSettings.toBuilder()
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
                      .setSuccess(BlockResponse.newBuilder().setContent("httpjsonBlockContent_6sDelay_noRetry"))
                      .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(6).build())
                      .build();
      DeadlineExceededException exception =
              assertThrows(DeadlineExceededException.class, () -> httpJsonClient.block(blockRequest));
      assertThat(exception.getStatusCode().getCode()).isEqualTo(StatusCode.Code.DEADLINE_EXCEEDED);
    }
  }

  // We should expect that there are four calls (three retry attempts)
  // Attempt #  | Milli Start Time  | Milli End Time  | Retry Delay | RPC Timeout
  // 1          | 0                 | 500             | 200         | 500
  // 2 (Retry)  | 700               | 1700            | 400         | 1000
  // 3 (Retry)  | 2100              | 4100            | 500 (cap)   | 2000
  // 4 (Retry)  | 4600              | 5000 (cap)      | 500         | 400
  // Note: Values are an approximation due to system jitter
  // There isn't a way to properly count the number of attempts. We ensure that retries
  // occur by setting the responseDelay value (3s) to be longer than the RPC timeout (2s)
  // and smaller than the totalTimeout (5).
  @Test
  public void testGRPC_unaryCallableRetry_exceedsDefaultTimeout_throwsDeadlineExceededException() throws IOException {
    RetrySettings defaultRetrySettings = RetrySettings.newBuilder()
            .setInitialRetryDelay(Duration.ofMillis(200L))
            .setRetryDelayMultiplier(2.0)
            .setMaxRetryDelay(Duration.ofMillis(500L))
            .setInitialRpcTimeout(Duration.ofMillis(500L))
            .setRpcTimeoutMultiplier(2.0)
            .setMaxRpcTimeout(Duration.ofMillis(2000L))
            .setTotalTimeout(Duration.ofMillis(5000L))
            .build();
    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    // Manually set DEADLINE_EXCEEDED as showcase tests do not have that as a retryable code
    grpcEchoSettingsBuilder.blockSettings().setRetrySettings(defaultRetrySettings).setRetryableCodes(StatusCode.Code.DEADLINE_EXCEEDED);
    EchoSettings grpcEchoSettings = EchoSettings.create(grpcEchoSettingsBuilder.build());
    grpcEchoSettings = grpcEchoSettings.toBuilder()
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
      DeadlineExceededException exception =
              assertThrows(DeadlineExceededException.class, () -> grpcClient.block(blockRequest));
      assertThat(exception.getStatusCode().getCode()).isEqualTo(StatusCode.Code.DEADLINE_EXCEEDED);
    }
  }

  // We should expect that there are four calls (three retry attempts)
  // Attempt #  | Milli Start Time  | Milli End Time  | Retry Delay | RPC Timeout
  // 1          | 0                 | 500             | 200         | 500
  // 2 (Retry)  | 700               | 1700            | 400         | 1000
  // 3 (Retry)  | 2100              | 4100            | 500 (cap)   | 2000
  // 4 (Retry)  | 4600              | 5000 (cap)      | 500         | 400
  // Note: Values are an approximation due to system jitter
  // There isn't a way to properly count the number of attempts. We ensure that retries
  // occur by setting the responseDelay value (3s) to be longer than the RPC timeout (2s)
  // and smaller than the totalTimeout (5).
  @Test
  public void testHttpJson_unaryCallableRetry_exceedsDefaultTimeout_throwsDeadlineExceededException() throws IOException, GeneralSecurityException {
    RetrySettings defaultRetrySettings = RetrySettings.newBuilder()
            .setInitialRetryDelay(Duration.ofMillis(200L))
            .setRetryDelayMultiplier(2.0)
            .setMaxRetryDelay(Duration.ofMillis(500L))
            .setInitialRpcTimeout(Duration.ofMillis(500L))
            .setRpcTimeoutMultiplier(2.0)
            .setMaxRpcTimeout(Duration.ofMillis(2000L))
            .setTotalTimeout(Duration.ofMillis(5000L))
            .build();
    EchoStubSettings.Builder httpJsonEchoSettingsBuilder = EchoStubSettings.newHttpJsonBuilder();
    // Manually set DEADLINE_EXCEEDED as showcase tests do not have that as a retryable code
    httpJsonEchoSettingsBuilder.blockSettings().setRetrySettings(defaultRetrySettings).setRetryableCodes(StatusCode.Code.DEADLINE_EXCEEDED);
    EchoSettings httpJsonEchoSettings = EchoSettings.create(httpJsonEchoSettingsBuilder.build());
    httpJsonEchoSettings = httpJsonEchoSettings.toBuilder()
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
                      .setSuccess(BlockResponse.newBuilder().setContent("httpjsonBlockContent_5sDelay_Retry"))
                      .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(3).build())
                      .build();
      DeadlineExceededException exception =
              assertThrows(DeadlineExceededException.class, () -> httpJsonClient.block(blockRequest));
      assertThat(exception.getStatusCode().getCode()).isEqualTo(StatusCode.Code.DEADLINE_EXCEEDED);
    }
  }
}
