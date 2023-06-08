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

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.rpc.CancelledException;
import com.google.api.gax.rpc.ServerStream;
import com.google.api.gax.rpc.StatusCode;
import com.google.api.gax.rpc.WatchdogTimeoutException;
import com.google.common.collect.ImmutableList;
import com.google.rpc.Status;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.ExpandRequest;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import io.grpc.ManagedChannelBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.threeten.bp.Duration;

public class ITServerSideStreaming {

  private static EchoClient grpcClient;
  private static EchoClient httpjsonClient;

  @BeforeClass
  public static void createClients() throws Exception {
    // Create gRPC Echo Client
    grpcClient = TestClientInitializer.createGrpcEchoClient();
    // Create Http JSON Echo Client
    httpjsonClient = TestClientInitializer.createHttpJsonEchoClient();
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
  public void testGrpc_receiveStreamedContent() {
    String content = "The rain in Spain stays mainly on the plain!";
    ServerStream<EchoResponse> responseStream =
        grpcClient.expandCallable().call(ExpandRequest.newBuilder().setContent(content).build());
    ArrayList<String> responses = new ArrayList<>();
    for (EchoResponse response : responseStream) {
      responses.add(response.getContent());
    }

    assertThat(responses)
        .containsExactlyElementsIn(
            ImmutableList.of(
                "The", "rain", "in", "Spain", "stays", "mainly", "on", "the", "plain!"))
        .inOrder();
  }

  @Test
  public void testGrpc_receiveStreamedContentStreamAPI() {
    String content = "The rain in Spain stays mainly on the plain!";
    ServerStream<EchoResponse> responseStream =
        grpcClient.expandCallable().call(ExpandRequest.newBuilder().setContent(content).build());
    assertThat(responseStream.stream().map(EchoResponse::getContent).collect(Collectors.toList()))
        .containsExactlyElementsIn(
            ImmutableList.of(
                "The", "rain", "in", "Spain", "stays", "mainly", "on", "the", "plain!"))
        .inOrder();
  }

  @Test
  public void testGrpc_serverError_receiveErrorAfterLastWordInStream() {
    String content = "The rain in Spain";
    Status cancelledStatus =
        Status.newBuilder().setCode(StatusCode.Code.CANCELLED.ordinal()).build();
    ServerStream<EchoResponse> responseStream =
        grpcClient
            .expandCallable()
            .call(ExpandRequest.newBuilder().setContent(content).setError(cancelledStatus).build());
    Iterator<EchoResponse> echoResponseIterator = responseStream.iterator();

    assertThat(echoResponseIterator.next().getContent()).isEqualTo("The");
    assertThat(echoResponseIterator.next().getContent()).isEqualTo("rain");
    assertThat(echoResponseIterator.next().getContent()).isEqualTo("in");
    assertThat(echoResponseIterator.next().getContent()).isEqualTo("Spain");
    CancelledException cancelledException =
        assertThrows(CancelledException.class, echoResponseIterator::next);
    assertThat(cancelledException.getStatusCode().getCode()).isEqualTo(StatusCode.Code.CANCELLED);
  }

  @Test
  public void testGrpc_serverWaitTimeout_watchdogCancelsStream() throws Exception {
    EchoSettings.Builder settings =
        EchoSettings.newBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build());

    settings
        .expandSettings()
        .setIdleTimeout(Duration.ofMillis(100))
        .setWaitTimeout(Duration.ofMillis(100));

    settings.getStubSettingsBuilder().setStreamWatchdogCheckInterval(Duration.ofMillis(50));

    EchoClient echoClient = EchoClient.create(settings.build());

    String content = "The rain in Spain stays mainly on the plain!";
    ServerStream<EchoResponse> responseStream =
        echoClient
            .expandCallable()
            .call(
                ExpandRequest.newBuilder()
                    .setContent(content)
                    // Configure server interval for returning the next response
                    .setStreamWaitTime(
                        com.google.protobuf.Duration.newBuilder().setSeconds(1).build())
                    .build());
    ArrayList<String> responses = new ArrayList<>();
    try {
      for (EchoResponse response : responseStream) {
        responses.add(response.getContent());
      }
      Assert.fail("No exception was thrown");
    } catch (WatchdogTimeoutException e) {
      assertThat(e).hasMessageThat().contains("Canceled due to timeout waiting for next response");
    } finally {
      echoClient.close();
    }
  }

  @Test
  public void testHttpJson_receiveStreamedContent() {
    String content = "The rain in Spain stays mainly on the plain!";
    ServerStream<EchoResponse> responseStream =
        httpjsonClient
            .expandCallable()
            .call(ExpandRequest.newBuilder().setContent(content).build());
    ArrayList<String> responses = new ArrayList<>();
    for (EchoResponse response : responseStream) {
      responses.add(response.getContent());
    }

    assertThat(responses)
        .containsExactlyElementsIn(
            ImmutableList.of(
                "The", "rain", "in", "Spain", "stays", "mainly", "on", "the", "plain!"))
        .inOrder();
  }

  @Ignore(
      value = "Ignore until https://github.com/googleapis/gapic-showcase/issues/1286 is resolved")
  @Test
  public void testHttpJson_serverError_receiveErrorAfterLastWordInStream() {
    String content = "The rain in Spain";
    Status cancelledStatus =
        Status.newBuilder().setCode(StatusCode.Code.CANCELLED.ordinal()).build();
    ServerStream<EchoResponse> responseStream =
        httpjsonClient
            .expandCallable()
            .call(ExpandRequest.newBuilder().setContent(content).setError(cancelledStatus).build());
    Iterator<EchoResponse> echoResponseIterator = responseStream.iterator();

    assertThat(echoResponseIterator.next().getContent()).isEqualTo("The");
    assertThat(echoResponseIterator.next().getContent()).isEqualTo("rain");
    assertThat(echoResponseIterator.next().getContent()).isEqualTo("in");
    assertThat(echoResponseIterator.next().getContent()).isEqualTo("Spain");
    CancelledException cancelledException =
        assertThrows(CancelledException.class, echoResponseIterator::next);
    assertThat(cancelledException.getStatusCode().getCode()).isEqualTo(StatusCode.Code.CANCELLED);
  }
}
