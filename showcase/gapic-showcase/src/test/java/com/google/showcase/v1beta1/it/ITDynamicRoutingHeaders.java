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

import com.google.api.gax.httpjson.ApiMethodDescriptor;
import com.google.api.gax.httpjson.ForwardingHttpJsonClientCall;
import com.google.api.gax.httpjson.ForwardingHttpJsonClientCallListener;
import com.google.api.gax.httpjson.HttpJsonCallOptions;
import com.google.api.gax.httpjson.HttpJsonChannel;
import com.google.api.gax.httpjson.HttpJsonClientCall;
import com.google.api.gax.httpjson.HttpJsonClientInterceptor;
import com.google.api.gax.httpjson.HttpJsonMetadata;
import com.google.common.collect.ImmutableList;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ITDynamicRoutingHeaders {
  private static final String SPLIT_TOKEN = "&";
  private static final String DYNAMIC_ROUTING_HEADER_KEY = "x-goog-request-params";
  private static final Metadata.Key<String> REQUEST_PARAMS_HEADER_KEY =
      Metadata.Key.of(DYNAMIC_ROUTING_HEADER_KEY, Metadata.ASCII_STRING_MARSHALLER);

  // Implement a request interceptor to retrieve the headers being sent on the request.
  // The headers being set are part of the Metadata
  private static class GrpcCapturingClientInterceptor implements ClientInterceptor {
    private Metadata metadata;

    @Override
    public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> interceptCall(
        MethodDescriptor<RequestT, ResponseT> method, final CallOptions callOptions, Channel next) {
      ClientCall<RequestT, ResponseT> call = next.newCall(method, callOptions);
      return new ForwardingClientCall.SimpleForwardingClientCall<RequestT, ResponseT>(call) {
        @Override
        public void start(ClientCall.Listener<ResponseT> responseListener, Metadata headers) {
          metadata = headers;
          super.start(responseListener, headers);
        }
      };
    }
  }

  // Implement a request interceptor to retrieve the headers being sent on the request
  // The headers being set are part of the CallOptions
  private static class HttpJsonCapturingClientInterceptor implements HttpJsonClientInterceptor {
    private String requestParam;

    @Override
    public <RequestT, ResponseT> HttpJsonClientCall<RequestT, ResponseT> interceptCall(
        ApiMethodDescriptor<RequestT, ResponseT> method,
        HttpJsonCallOptions callOptions,
        HttpJsonChannel next) {
      HttpJsonClientCall<RequestT, ResponseT> call = next.newCall(method, callOptions);
      return new ForwardingHttpJsonClientCall.SimpleForwardingHttpJsonClientCall<
          RequestT, ResponseT>(call) {
        @Override
        public void start(Listener<ResponseT> responseListener, HttpJsonMetadata requestHeaders) {
          Listener<ResponseT> forwardingResponseListener =
              new ForwardingHttpJsonClientCallListener.SimpleForwardingHttpJsonClientCallListener<
                  ResponseT>(responseListener) {
                @Override
                public void onHeaders(HttpJsonMetadata responseHeaders) {
                  super.onHeaders(responseHeaders);
                }

                @Override
                public void onMessage(ResponseT message) {
                  super.onMessage(message);
                }

                @Override
                public void onClose(int statusCode, HttpJsonMetadata trailers) {
                  super.onClose(statusCode, trailers);
                }
              };

          super.start(forwardingResponseListener, requestHeaders);
          requestParam = callOptions.getRequestHeaderMap().get(DYNAMIC_ROUTING_HEADER_KEY);
        }
      };
    }
  }

  private HttpJsonCapturingClientInterceptor httpJsonInterceptor;
  private GrpcCapturingClientInterceptor grpcInterceptor;

  private EchoClient grpcClient;
  private EchoClient httpJsonClient;

  @Before
  public void createClients() throws Exception {
    // Create gRPC Interceptor and Client
    grpcInterceptor = new GrpcCapturingClientInterceptor();
    grpcClient = TestClientInitializer.createGrpcEchoClient(ImmutableList.of(grpcInterceptor));

    // Create HttpJson Interceptor and Client
    httpJsonInterceptor = new HttpJsonCapturingClientInterceptor();
    httpJsonClient =
        TestClientInitializer.createHttpJsonEchoClient(ImmutableList.of(httpJsonInterceptor));
  }

  @After
  public void destroyClient() {
    grpcClient.close();
    httpJsonClient.close();
  }

  @Test
  public void testGrpc_noRoutingHeaderUsed() {
    grpcClient.echo(EchoRequest.newBuilder().build());
    String headerValue = grpcInterceptor.metadata.get(REQUEST_PARAMS_HEADER_KEY);
    assertThat(headerValue).isNull();
  }

  @Test
  public void testHttpJson_noRoutingHeaderUsed() {
    httpJsonClient.echo(EchoRequest.newBuilder().build());
    String headerValue = httpJsonInterceptor.requestParam;
    assertThat(headerValue).isNull();
  }

  @Test
  public void testGrpc_emptyHeader() {
    grpcClient.echo(EchoRequest.newBuilder().setHeader("").build());
    String headerValue = grpcInterceptor.metadata.get(REQUEST_PARAMS_HEADER_KEY);
    assertThat(headerValue).isNull();
  }

  @Test
  public void testHttpJson_emptyHeader() {
    httpJsonClient.echo(EchoRequest.newBuilder().setHeader("").build());
    String headerValue = httpJsonInterceptor.requestParam;
    assertThat(headerValue).isNull();
  }

  @Test
  public void testGrpc_matchesHeaderName() {
    grpcClient.echo(EchoRequest.newBuilder().setHeader("potato").build());
    String headerValue = grpcInterceptor.metadata.get(REQUEST_PARAMS_HEADER_KEY);
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders = ImmutableList.of("header=potato", "routing_id=potato");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  public void testHttpJson_matchesHeaderName() {
    httpJsonClient.echo(EchoRequest.newBuilder().setHeader("potato").build());
    String headerValue = httpJsonInterceptor.requestParam;
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders = ImmutableList.of("header=potato", "routing_id=potato");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  public void testGrpc_matchesOtherHeaderName() {
    grpcClient.echo(EchoRequest.newBuilder().setOtherHeader("instances/456").build());
    String headerValue = grpcInterceptor.metadata.get(REQUEST_PARAMS_HEADER_KEY);
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders = ImmutableList.of("baz=instances%2F456");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  public void testHttpJson_matchesOtherHeaderName() {
    httpJsonClient.echo(EchoRequest.newBuilder().setOtherHeader("instances/456").build());
    String headerValue = httpJsonInterceptor.requestParam;
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders = ImmutableList.of("baz=instances%2F456");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  public void testGrpc_matchesMultipleOfSameRoutingHeader_usesHeader() {
    grpcClient.echo(EchoRequest.newBuilder().setHeader("projects/123/instances/456").build());
    String headerValue = grpcInterceptor.metadata.get(REQUEST_PARAMS_HEADER_KEY);
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders =
        ImmutableList.of(
            "header=projects%2F123%2Finstances%2F456",
            "routing_id=projects%2F123%2Finstances%2F456",
            "super_id=projects%2F123",
            "table_name=projects%2F123%2Finstances%2F456",
            "instance_id=instances%2F456");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  public void testHttpJson_matchesMultipleOfSameRoutingHeader_usesHeader() {
    httpJsonClient.echo(EchoRequest.newBuilder().setHeader("projects/123/instances/456").build());
    String headerValue = httpJsonInterceptor.requestParam;
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders =
        ImmutableList.of(
            "header=projects%2F123%2Finstances%2F456",
            "routing_id=projects%2F123%2Finstances%2F456",
            "super_id=projects%2F123",
            "table_name=projects%2F123%2Finstances%2F456",
            "instance_id=instances%2F456");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders).inOrder();
  }

  @Test
  public void testGrpc_matchesMultipleOfSameRoutingHeader_usesOtherHeader() {
    grpcClient.echo(EchoRequest.newBuilder().setOtherHeader("projects/123/instances/456").build());
    String headerValue = grpcInterceptor.metadata.get(REQUEST_PARAMS_HEADER_KEY);
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders =
        ImmutableList.of("baz=projects%2F123%2Finstances%2F456", "qux=projects%2F123");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  public void testHttpJson_matchesMultipleOfSameRoutingHeader_usesOtherHeader() {
    httpJsonClient.echo(
        EchoRequest.newBuilder().setOtherHeader("projects/123/instances/456").build());
    String headerValue = httpJsonInterceptor.requestParam;
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders =
        ImmutableList.of("baz=projects%2F123%2Finstances%2F456", "qux=projects%2F123");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  public void testGrpc_matchesMultipleRoutingHeaders() {
    grpcClient.echo(
        EchoRequest.newBuilder()
            .setHeader("regions/123/zones/456")
            .setOtherHeader("projects/123/instances/456")
            .build());
    String headerValue = grpcInterceptor.metadata.get(REQUEST_PARAMS_HEADER_KEY);
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders =
        ImmutableList.of(
            "baz=projects%2F123%2Finstances%2F456",
            "qux=projects%2F123",
            "table_name=regions%2F123%2Fzones%2F456",
            "header=regions%2F123%2Fzones%2F456",
            "routing_id=regions%2F123%2Fzones%2F456");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  public void testHttpJson_matchesMultipleRoutingHeaders() {
    httpJsonClient.echo(
        EchoRequest.newBuilder()
            .setHeader("regions/123/zones/456")
            .setOtherHeader("projects/123/instances/456")
            .build());
    String headerValue = httpJsonInterceptor.requestParam;
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders =
        ImmutableList.of(
            "baz=projects%2F123%2Finstances%2F456",
            "qux=projects%2F123",
            "table_name=regions%2F123%2Fzones%2F456",
            "header=regions%2F123%2Fzones%2F456",
            "routing_id=regions%2F123%2Fzones%2F456");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }
}
