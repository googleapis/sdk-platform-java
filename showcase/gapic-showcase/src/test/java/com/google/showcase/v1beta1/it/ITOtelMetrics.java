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

import com.google.api.gax.grpc.GrpcStatusCode;
import com.google.api.gax.rpc.CancelledException;
import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.api.gax.rpc.NotFoundException;
import com.google.api.gax.rpc.StatusCode;
import com.google.api.gax.rpc.StatusCode.Code;
import com.google.rpc.Status;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITOtelMetrics {

  private static EchoClient grpcClient;

  private static EchoClient httpjsonClient;

  @BeforeClass
  public static void createClients() throws Exception {
    // Create gRPC Echo Client
    grpcClient = TestClientInitializer.createGrpcEchoClient();
    // Create Http JSON Echo Client
    httpjsonClient = TestClientInitializer.createHttpJsonEchoClientOtel();
  }

  @AfterClass
  public static void destroyClients() throws InterruptedException {
    grpcClient.close();
    httpjsonClient.close();

    grpcClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
    httpjsonClient.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testHttpJson_serverResponseError_throwsException() throws InterruptedException {

    EchoRequest requestWithServerError =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.CANCELLED.ordinal()).build())
            .build();
    // httpjsonClient.echo(requestWithServerError);
    CancelledException exception =
        assertThrows(CancelledException.class, () -> httpjsonClient.echo(requestWithServerError));

    // Introduce a 30-second delay
    Thread.sleep(30000);

    // assertThat(exception.getStatusCode().getCode()).isEqualTo(StatusCode.Code.CANCELLED);
  }

  @Test
  public void testGrpc_attemptFailedRetriesExhausted_throwsException() throws Exception {

    RetrySettings defaultRetrySettings =
        RetrySettings.newBuilder()
            .setMaxAttempts(2)
            .build();
    EchoClient grpcClientWithRetrySetting = TestClientInitializer.createGrpcEchoClientOtelWithRetrySettings(defaultRetrySettings, ImmutableSet.of(Code.INVALID_ARGUMENT));

    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.INVALID_ARGUMENT.ordinal()).build())
            .build();
    RetryingFuture<BlockResponse> retryingFuture =
        (RetryingFuture<BlockResponse>) grpcClientWithRetrySetting.blockCallable().futureCall(blockRequest);
    BlockResponse blockResponse = retryingFuture.get(100, TimeUnit.SECONDS);
  }

  @Test
  public void testGrpc_attemptPermanentFailure_throwsException() throws Exception {

    RetrySettings defaultRetrySettings =
        RetrySettings.newBuilder()
            .setMaxAttempts(2)
            .build();
    EchoClient grpcClientWithRetrySetting = TestClientInitializer.createGrpcEchoClientOtelWithRetrySettings(defaultRetrySettings, ImmutableSet.of(Code.UNAVAILABLE));

    // if the request code is not in set of retryable codes, the ApiResultRetryAlgorithm
    // send false for shouldRetry(), which sends false in retryAlgorithm.shouldRetryBasedOnResult()

    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.INVALID_ARGUMENT.ordinal()).build())
            .build();

    RetryingFuture<BlockResponse> retryingFuture =
        (RetryingFuture<BlockResponse>) grpcClientWithRetrySetting.blockCallable().futureCall(blockRequest);
    BlockResponse blockResponse = retryingFuture.get(100,TimeUnit.SECONDS);

  }



}
