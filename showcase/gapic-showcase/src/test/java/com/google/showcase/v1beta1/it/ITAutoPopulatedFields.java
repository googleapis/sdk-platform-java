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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.api.gax.httpjson.ApiMethodDescriptor;
import com.google.api.gax.httpjson.ForwardingHttpJsonClientCall;
import com.google.api.gax.httpjson.HttpJsonCallOptions;
import com.google.api.gax.httpjson.HttpJsonChannel;
import com.google.api.gax.httpjson.HttpJsonClientCall;
import com.google.api.gax.httpjson.HttpJsonClientInterceptor;
import com.google.common.collect.ImmutableList;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.MethodDescriptor;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ITAutoPopulatedFields {
  // Implement a request interceptor to retrieve the request ID being sent on the request.
  private static class GrpcRequestClientInterceptor implements ClientInterceptor {
    private Object requestSent;

    @Override
    public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> interceptCall(
        MethodDescriptor<RequestT, ResponseT> method, final CallOptions callOptions, Channel next) {
      ClientCall<RequestT, ResponseT> call = next.newCall(method, callOptions);
      return new ForwardingClientCall.SimpleForwardingClientCall<RequestT, ResponseT>(call) {
        @Override
        public void sendMessage(RequestT requestT) {
          requestSent = requestT;
          super.sendMessage(requestT);
        }
      };
    }
  }

  private static class HttpJsonRequestClientInterceptor implements HttpJsonClientInterceptor {
    private Object requestSent;

    @Override
    public <RequestT, ResponseT> HttpJsonClientCall<RequestT, ResponseT> interceptCall(
        ApiMethodDescriptor<RequestT, ResponseT> method,
        HttpJsonCallOptions callOptions,
        HttpJsonChannel next) {
      HttpJsonClientCall<RequestT, ResponseT> call = next.newCall(method, callOptions);
      return new ForwardingHttpJsonClientCall.SimpleForwardingHttpJsonClientCall<RequestT, ResponseT>(call) {
        @Override
        public void sendMessage(RequestT requestT) {
          requestSent = requestT;
          super.sendMessage(requestT);
        }
      };
    }
  }


  private GrpcRequestClientInterceptor grpcInterceptor;
  private HttpJsonRequestClientInterceptor httpJsonInterceptor;
  private EchoClient grpcClient;

  private EchoClient httpJsonClient;
  @Before
  public void createClients() throws Exception {
    // Create gRPC Interceptor and Client
    grpcInterceptor = new ITAutoPopulatedFields.GrpcRequestClientInterceptor();
    grpcClient = TestClientInitializer.createGrpcEchoClient(ImmutableList.of(grpcInterceptor));

    // Create HttpJson Interceptor and Client
    httpJsonInterceptor = new ITAutoPopulatedFields.HttpJsonRequestClientInterceptor();
    httpJsonClient =
        TestClientInitializer.createHttpJsonEchoClient(ImmutableList.of(httpJsonInterceptor));
  }


  @After
  public void destroyClient() {
    grpcClient.close();
  }


  @Test
  public void testGrpc_autoPopulateRequestId() {
    grpcClient.echo(EchoRequest.newBuilder().build());
    EchoRequest requestSent = (EchoRequest) grpcInterceptor.requestSent;
    assertTrue(UUID.fromString(requestSent.getRequestId()).version() == 4);
  }

  @Test
  public void testGrpc_shouldNotAutoPopulateRequestId() {
    String UUIDsent = UUID.randomUUID().toString();
    grpcClient.echo(EchoRequest.newBuilder().setRequestId(UUIDsent).build());
    EchoRequest requestSent = (EchoRequest) grpcInterceptor.requestSent;
    assertEquals(requestSent.getRequestId(), UUIDsent);
  }

  @Test
  public void testHttpJson_autoPopulateRequestId() {
    httpJsonClient.echo(EchoRequest.newBuilder().build());
    EchoRequest requestSent = (EchoRequest) httpJsonInterceptor.requestSent;
    assertTrue(UUID.fromString(requestSent.getRequestId()).version() == 4);
  }

  @Test
  public void testHttpJson_shouldNotAutoPopulateRequestId() {
    String UUIDsent = UUID.randomUUID().toString();
    httpJsonClient.echo(EchoRequest.newBuilder().setRequestId(UUIDsent).build());
    EchoRequest requestSent = (EchoRequest) httpJsonInterceptor.requestSent;
    assertEquals(requestSent.getRequestId(), UUIDsent);
  }
}
