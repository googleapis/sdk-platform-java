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

import com.google.api.gax.rpc.StatusCode.Code;
import com.google.rpc.Status;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITOtelMetrics {

  private static EchoClient grpcClientOpenTelemetry;

  private static EchoClient httpjsonClientOpenTelemetry;

  @BeforeClass
  public static void createClients() throws Exception {
    // grpcClientOpenTelemetry = TestClientInitializer.createGrpcEchoClientOpenTelemetry();
    httpjsonClientOpenTelemetry = TestClientInitializer.createHttpJsonEchoClientOpenTelemetry();
  }

  @AfterClass
  public static void destroyClients() throws InterruptedException {
    // grpcClientOpenTelemetry.close();
    httpjsonClientOpenTelemetry.close();

    // grpcClientOpenTelemetry.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS,
    // TimeUnit.SECONDS);
    httpjsonClientOpenTelemetry.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testHttpJson_OperationSucceded() throws InterruptedException {

    EchoRequest requestWithNoError =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(Code.OK.ordinal()).build())
            .build();

    EchoResponse response = httpjsonClientOpenTelemetry.echo(requestWithNoError);

    Thread.sleep(30000);

    boolean fileExists = Files.exists(Paths.get("../../../metrics.txt"));

    System.out.println(fileExists);

    // assertThat(exception.getStatusCode().getCode()).isEqualTo(StatusCode.Code.CANCELLED);
  }

  // @Test
  // public void testGrpc_attemptFailedRetriesExhausted_throwsException() throws Exception {
  //
  //   RetrySettings defaultRetrySettings =
  //       RetrySettings.newBuilder()
  //           .setMaxAttempts(2)
  //           .build();
  //   EchoClient grpcClientWithRetrySetting =
  // TestClientInitializer.createGrpcEchoClientOtelWithRetrySettings(
  //       defaultRetrySettings, ImmutableSet.of(Code.INVALID_ARGUMENT));
  //
  //   BlockRequest blockRequest =
  //       BlockRequest.newBuilder()
  //           .setError(Status.newBuilder().setCode(Code.INVALID_ARGUMENT.ordinal()).build())
  //           .build();
  //   RetryingFuture<BlockResponse> retryingFuture =
  //       (RetryingFuture<BlockResponse>) grpcClientWithRetrySetting.blockCallable()
  //           .futureCall(blockRequest);
  //
  //   Thread.sleep(50000);
  //
  //   BlockResponse blockResponse = retryingFuture.get(10000, TimeUnit.SECONDS);
  //
  // }
  //
  // @Test
  // public void testGrpc_attemptPermanentFailure_throwsException() throws Exception {
  //
  //   RetrySettings defaultRetrySettings =
  //       RetrySettings.newBuilder()
  //           .setMaxAttempts(2)
  //           .build();
  //   EchoClient grpcClientWithRetrySetting =
  // TestClientInitializer.createGrpcEchoClientOtelWithRetrySettings(
  //       defaultRetrySettings, ImmutableSet.of(Code.UNAVAILABLE));
  //
  //   // if the request code is not in set of retryable codes, the ApiResultRetryAlgorithm
  //   // send false for shouldRetry(), which sends false in
  // retryAlgorithm.shouldRetryBasedOnResult()
  //   // which triggers this scenario -
  // https://github.com/googleapis/sdk-platform-java/blob/main/gax-java/gax/src/main/java/com/google/api/gax/retrying/BasicRetryingFuture.java#L194-L198
  //
  //   BlockRequest blockRequest =
  //       BlockRequest.newBuilder()
  //           .setError(Status.newBuilder().setCode(Code.INVALID_ARGUMENT.ordinal()).build())
  //           .build();
  //
  //   RetryingFuture<BlockResponse> retryingFuture =
  //       (RetryingFuture<BlockResponse>) grpcClientWithRetrySetting.blockCallable()
  //           .futureCall(blockRequest);
  //   BlockResponse blockResponse = retryingFuture.get(100, TimeUnit.SECONDS);
  //
  // }
}
