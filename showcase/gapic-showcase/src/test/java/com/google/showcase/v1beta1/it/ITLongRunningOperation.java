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

import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.retrying.RetrySettings;
import com.google.protobuf.Timestamp;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.WaitMetadata;
import com.google.showcase.v1beta1.WaitRequest;
import com.google.showcase.v1beta1.WaitResponse;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.util.concurrent.CancellationException;
import org.junit.Test;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;
import org.threeten.bp.temporal.ChronoUnit;

/**
 * For this test, we test a combination of various LRO RetrySettings and try to ensure that the
 * calls are polling correctly. Each test attempts to test the number of attempts done in each call.
 */
public class ITLongRunningOperation {

  @Test
  public void testGRPC_LROSuccessfulResponse_doesNotExceedTotalTimeout() throws Exception {
    RetrySettings initialUnaryRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRpcTimeout(Duration.ofMillis(3000L))
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ofMillis(3000L))
            .setTotalTimeout(Duration.ofMillis(3000L))
            .build();
    RetrySettings pollingRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRetryDelay(Duration.ofMillis(3000L))
            .setRetryDelayMultiplier(2.0)
            .setMaxRetryDelay(Duration.ofMillis(5000L))
            .setInitialRpcTimeout(Duration.ZERO)
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ZERO)
            .setTotalTimeout(Duration.ofMillis(10000L))
            .build();
    try (EchoClient grpcClient =
        TestClientInitializer.createGrpcEchoClientCustomWaitSettings(
            initialUnaryRetrySettings, pollingRetrySettings)) {
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
      assertThat(attemptCount).isAtLeast(2);
    }
  }

  @Test
  public void testHttpJson_LROSuccessfulResponse_doesNotExceedTotalTimeout() throws Exception {
    RetrySettings initialUnaryRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRpcTimeout(Duration.ofMillis(3000L))
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ofMillis(3000L))
            .setTotalTimeout(Duration.ofMillis(3000L))
            .build();
    RetrySettings pollingRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRetryDelay(Duration.ofMillis(3000L))
            .setRetryDelayMultiplier(2.0)
            .setMaxRetryDelay(Duration.ofMillis(5000L))
            .setInitialRpcTimeout(Duration.ZERO)
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ZERO)
            .setTotalTimeout(Duration.ofMillis(10000L))
            .build();
    try (EchoClient httpJsonClient =
        TestClientInitializer.createHttpJsonEchoClientCustomWaitSettings(
            initialUnaryRetrySettings, pollingRetrySettings)) {
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
      assertThat(attemptCount).isAtLeast(2);
    }
  }

  @Test
  public void testGRPC_LROUnsuccessfulResponse_exceedsTotalTimeout_throwsDeadlineExceededException()
      throws Exception {
    RetrySettings initialUnaryRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRpcTimeout(Duration.ofMillis(2000L))
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ofMillis(2000L))
            .setTotalTimeout(Duration.ofMillis(2000L))
            .build();
    RetrySettings pollingRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRetryDelay(Duration.ofMillis(1000L))
            .setRetryDelayMultiplier(2.0)
            .setMaxRetryDelay(Duration.ofMillis(3000L))
            .setInitialRpcTimeout(Duration.ZERO)
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ZERO)
            .setTotalTimeout(Duration.ofMillis(5000L))
            .build();
    try (EchoClient grpcClient =
        TestClientInitializer.createGrpcEchoClientCustomWaitSettings(
            initialUnaryRetrySettings, pollingRetrySettings)) {
      long epochSecondsInFuture = Instant.now().plus(6, ChronoUnit.SECONDS).getEpochSecond();
      WaitRequest waitRequest =
          WaitRequest.newBuilder()
              .setSuccess(WaitResponse.newBuilder().setContent("gRPCWaitContent_6sDelay"))
              .setEndTime(Timestamp.newBuilder().setSeconds(epochSecondsInFuture).build())
              .build();
      OperationFuture<WaitResponse, WaitMetadata> operationFuture =
          grpcClient.waitOperationCallable().futureCall(waitRequest);
      assertThrows(CancellationException.class, operationFuture::get);
      int attemptCount = operationFuture.getPollingFuture().getAttemptSettings().getAttemptCount();
      assertThat(attemptCount).isGreaterThan(1);
    }
  }

  @Test
  public void
      testHttpJson_LROUnsuccessfulResponse_exceedsTotalTimeout_throwsDeadlineExceededException()
          throws Exception {
    RetrySettings initialUnaryRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRpcTimeout(Duration.ofMillis(2000L))
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ofMillis(2000L))
            .setTotalTimeout(Duration.ofMillis(2000L))
            .build();
    RetrySettings pollingRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRetryDelay(Duration.ofMillis(1000L))
            .setRetryDelayMultiplier(2.0)
            .setMaxRetryDelay(Duration.ofMillis(3000L))
            .setInitialRpcTimeout(Duration.ZERO)
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ZERO)
            .setTotalTimeout(Duration.ofMillis(5000L))
            .build();
    try (EchoClient httpJsonClient =
        TestClientInitializer.createHttpJsonEchoClientCustomWaitSettings(
            initialUnaryRetrySettings, pollingRetrySettings)) {
      long epochSecondsInFuture = Instant.now().plus(6, ChronoUnit.SECONDS).getEpochSecond();
      WaitRequest waitRequest =
          WaitRequest.newBuilder()
              .setSuccess(WaitResponse.newBuilder().setContent("httpjsonWaitContent_6sDelay"))
              .setEndTime(Timestamp.newBuilder().setSeconds(epochSecondsInFuture).build())
              .build();
      OperationFuture<WaitResponse, WaitMetadata> operationFuture =
          httpJsonClient.waitOperationCallable().futureCall(waitRequest);
      assertThrows(CancellationException.class, operationFuture::get);
      int attemptCount = operationFuture.getPollingFuture().getAttemptSettings().getAttemptCount();
      assertThat(attemptCount).isGreaterThan(1);
    }
  }
}
