/*
 * Copyright 2024 Google LLC
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

import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.StatusCode;
import com.google.common.collect.ImmutableSet;
import com.google.common.truth.Truth;
import com.google.showcase.v1beta1.BlockRequest;
import com.google.showcase.v1beta1.BlockResponse;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.threeten.bp.Duration;

public class ITClientShutdown {

  // Test to ensure the client can close + terminate properly
  @Test
  public void testGrpc_closeClient() throws Exception {
    EchoClient grpcClient = TestClientInitializer.createGrpcEchoClient();
    grpcClient.close();
    // 500ms buffer time to properly terminate the client
    grpcClient.awaitTermination(500, TimeUnit.MILLISECONDS);
    Truth.assertThat(grpcClient.isShutdown()).isTrue();
    Truth.assertThat(grpcClient.isTerminated()).isTrue();
  }

  // Test to ensure the client can close + terminate properly
  @Test
  public void testHttpJson_closeClient() throws Exception {
    EchoClient httpjsonClient = TestClientInitializer.createHttpJsonEchoClient();
    httpjsonClient.close();
    // 500ms buffer time to properly terminate the client
    httpjsonClient.awaitTermination(500, TimeUnit.MILLISECONDS);
    Truth.assertThat(httpjsonClient.isShutdown()).isTrue();
    Truth.assertThat(httpjsonClient.isTerminated()).isTrue();
  }

  // Test to ensure hte client can close + terminate after a quick RPC invocation
  @Test
  public void testGrpc_rpcInvoked_closeClient() throws Exception {
    EchoClient grpcClient = TestClientInitializer.createGrpcEchoClient();

    grpcClient.echo(EchoRequest.newBuilder().setContent("Test").build());

    grpcClient.close();
    // 1s buffer time to properly terminate the client after RPC is invoked
    grpcClient.awaitTermination(1, TimeUnit.SECONDS);
    Truth.assertThat(grpcClient.isShutdown()).isTrue();
    Truth.assertThat(grpcClient.isTerminated()).isTrue();
  }

  // Test to ensure hte client can close + terminate after a quick RPC invocation
  @Test
  public void testHttpJson_rpcInvoked_closeClient() throws Exception {
    EchoClient httpjsonClient = TestClientInitializer.createHttpJsonEchoClient();

    httpjsonClient.echo(EchoRequest.newBuilder().setContent("Test").build());

    httpjsonClient.close();
    // 1s buffer time to properly terminate the client after RPC is invoked
    httpjsonClient.awaitTermination(1, TimeUnit.SECONDS);
    Truth.assertThat(httpjsonClient.isShutdown()).isTrue();
    Truth.assertThat(httpjsonClient.isTerminated()).isTrue();
  }

  // This test is to ensure that the client is able to close + terminate any resources
  // once a response has been received. Set a max test duration of 15s to ensure that
  // the test does not continue on forever.
  @Test(timeout = 15000L)
  public void testGrpc_rpcInvokedWithLargeTimeout_closeClientOnceResponseReceived()
      throws Exception {
    // Set the maxAttempts to 1 to ensure there are no retries scheduled. The single RPC
    // invocation should time out in 15s, but the client will receive a response in 2s.
    // Any outstanding tasks (timeout tasks) should be cancelled once a response has been
    // received so the client can properly terminate.
    RetrySettings defaultRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRpcTimeout(Duration.ofMillis(15000L))
            .setMaxRpcTimeout(Duration.ofMillis(15000L))
            .setTotalTimeout(Duration.ofMillis(15000L))
            .setMaxAttempts(1)
            .build();
    EchoClient grpcClient =
        TestClientInitializer.createGrpcEchoClientCustomBlockSettings(
            defaultRetrySettings, ImmutableSet.of(StatusCode.Code.DEADLINE_EXCEEDED));

    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setSuccess(BlockResponse.newBuilder().setContent("gRPCBlockContent_2sDelay"))
            .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(2).build())
            .build();

    long start = System.currentTimeMillis();
    BlockResponse response = grpcClient.block(blockRequest);
    Truth.assertThat(response.getContent()).isEqualTo("gRPCBlockContent_2sDelay");

    // Intentionally do not run grpcClient.awaitTermination(...) as this test will
    // check that everything is properly terminated after close() is called.
    grpcClient.close();

    busyWaitUntilClientTermination(grpcClient);
    long end = System.currentTimeMillis();

    Truth.assertThat(grpcClient.isShutdown()).isTrue();

    // Check the termination time. If all the tasks/ resources are closed successfully,
    // the termination time should only take about 2s (time to receive a response) + time
    // to close the client. Check that this takes less than 5s (2s request time + 3s
    // buffer time).
    long terminationTime = end - start;
    Truth.assertThat(terminationTime).isLessThan(5000L);
  }

  // This test is to ensure that the client is able to close + terminate any resources
  // once a response has been received. Set a max test duration of 15s to ensure that
  // the test does not continue on forever.
  @Test(timeout = 15000L)
  public void testHttpJson_rpcInvokedWithLargeTimeout_closeClientOnceResponseReceived()
      throws Exception {
    // Set the maxAttempts to 1 to ensure there are no retries scheduled. The single RPC
    // invocation should time out in 15s, but the client will receive a response in 2s.
    // Any outstanding tasks (timeout tasks) should be cancelled once a response has been
    // received so the client can properly terminate.
    RetrySettings defaultRetrySettings =
        RetrySettings.newBuilder()
            .setInitialRpcTimeout(Duration.ofMillis(15000L))
            .setMaxRpcTimeout(Duration.ofMillis(15000L))
            .setTotalTimeout(Duration.ofMillis(15000L))
            .setMaxAttempts(1)
            .build();
    EchoClient httpjsonClient =
        TestClientInitializer.createHttpJsonEchoClientCustomBlockSettings(
            defaultRetrySettings, ImmutableSet.of(StatusCode.Code.DEADLINE_EXCEEDED));

    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setSuccess(BlockResponse.newBuilder().setContent("httpjsonBlockContent_2sDelay"))
            .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(2).build())
            .build();

    long start = System.currentTimeMillis();
    BlockResponse response = httpjsonClient.block(blockRequest);
    Truth.assertThat(response.getContent()).isEqualTo("httpjsonBlockContent_2sDelay");

    // Intentionally do not run grpcClient.awaitTermination(...) as this test will
    // check that everything is properly terminated after close() is called.
    httpjsonClient.close();

    busyWaitUntilClientTermination(httpjsonClient);
    long end = System.currentTimeMillis();

    Truth.assertThat(httpjsonClient.isShutdown()).isTrue();

    // Check the termination time. If all the tasks/ resources are closed successfully,
    // the termination time should only take about 2s (time to receive a response) + time
    // to close the client. Check that this takes less than 5s (2s request time + 3s
    // buffer time).
    long terminationTime = end - start;
    Truth.assertThat(terminationTime).isLessThan(5000L);
  }

  // Loop until the client has terminated successfully. For tests that use this,
  // try to ensure there is a timeout associated, otherwise this may run forever.
  // Future enhancement: Use awaitility instead of busy waiting
  private static void busyWaitUntilClientTermination(EchoClient client)
      throws InterruptedException {
    while (!client.isTerminated()) {
      Thread.sleep(500L);
    }
  }
}
