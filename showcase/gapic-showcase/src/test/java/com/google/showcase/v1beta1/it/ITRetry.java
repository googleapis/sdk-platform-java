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
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.api.gax.rpc.DeadlineExceededException;
import com.google.api.gax.rpc.StatusCode;
import com.google.showcase.v1beta1.BlockRequest;
import com.google.showcase.v1beta1.BlockResponse;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoSettings;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ITRetry {
  private EchoClient grpcClient;
  private EchoClient httpJsonClient;

  @Before
  public void createClients() throws IOException, GeneralSecurityException {
    // Create gRPC Echo Client
    EchoSettings grpcEchoSettings =
        EchoSettings.newBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                InstantiatingGrpcChannelProvider.newBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .build();
    grpcClient = EchoClient.create(grpcEchoSettings);
    // Create Http JSON Echo Client
    EchoSettings httpJsonEchoSettings =
        EchoSettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    httpJsonClient = EchoClient.create(httpJsonEchoSettings);
  }

  @After
  public void destroyClient() {
    grpcClient.close();
    httpJsonClient.close();
  }

  @Test
  public void testGRPC_throwsDeadlineExceededException() {
    // Default timeout for UnaryCall is 5 seconds -- We want to ensure a long enough delay for
    // this test
    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setSuccess(BlockResponse.newBuilder().setContent("gRPCBlockContent_Delay"))
            .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(10).build())
            .build();
    DeadlineExceededException exception =
        assertThrows(DeadlineExceededException.class, () -> grpcClient.block(blockRequest));
    assertThat(exception.getStatusCode().getCode()).isEqualTo(StatusCode.Code.DEADLINE_EXCEEDED);
  }

  @Test
  public void testHttpJson_throwsDeadlineExceededException() {
    // Default timeout for UnaryCall is 5 seconds -- We want to ensure a long enough delay for
    // this test
    BlockRequest blockRequest =
        BlockRequest.newBuilder()
            .setSuccess(BlockResponse.newBuilder().setContent("gRPCBlockContent_Delay"))
            .setResponseDelay(com.google.protobuf.Duration.newBuilder().setSeconds(10).build())
            .build();
    DeadlineExceededException exception =
        assertThrows(DeadlineExceededException.class, () -> httpJsonClient.block(blockRequest));
    assertThat(exception.getStatusCode().getCode()).isEqualTo(StatusCode.Code.DEADLINE_EXCEEDED);
  }
}
