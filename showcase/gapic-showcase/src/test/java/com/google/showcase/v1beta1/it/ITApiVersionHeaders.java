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

import static com.google.common.truth.Truth.assertThat;

import com.google.api.gax.httpjson.*;
import com.google.common.collect.ImmutableList;
import com.google.showcase.v1beta1.*;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import io.grpc.*;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ITApiVersionHeaders {
  private static final String SPLIT_TOKEN = "&";
  private static final String API_VERSION_HEADER_STRING = "x-goog-api-version";
  private static final String HTTP_RESPONSE_HEADER_STRING =
      "x-showcase-request-" + API_VERSION_HEADER_STRING;
  private static final Metadata.Key<String> API_VERSION_HEADER_KEY =
      Metadata.Key.of(API_VERSION_HEADER_STRING, Metadata.ASCII_STRING_MARSHALLER);

  // Implement a client interceptor to retrieve the trailing metadata from response.
  private static class GrpcCapturingClientInterceptor implements ClientInterceptor {
    private Metadata metadata;

    @Override
    public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> interceptCall(
        MethodDescriptor<RequestT, ResponseT> method, final CallOptions callOptions, Channel next) {
      ClientCall<RequestT, ResponseT> call = next.newCall(method, callOptions);
      return new ForwardingClientCall.SimpleForwardingClientCall<RequestT, ResponseT>(call) {
        @Override
        public void start(Listener<ResponseT> responseListener, Metadata headers) {
          Listener<ResponseT> wrappedListener =
              new SimpleForwardingClientCallListener<ResponseT>(responseListener) {
                @Override
                public void onClose(Status status, Metadata trailers) {
                  if (status.isOk()) {
                    metadata = trailers;
                  }
                  super.onClose(status, trailers);
                }
              };

          super.start(wrappedListener, headers);
        }
      };
    }
  }

  private static class SimpleForwardingClientCallListener<RespT>
      extends ClientCall.Listener<RespT> {
    private final ClientCall.Listener<RespT> delegate;

    SimpleForwardingClientCallListener(ClientCall.Listener<RespT> delegate) {
      this.delegate = delegate;
    }

    @Override
    public void onHeaders(Metadata headers) {
      delegate.onHeaders(headers);
    }

    @Override
    public void onMessage(RespT message) {
      delegate.onMessage(message);
    }

    @Override
    public void onClose(Status status, Metadata trailers) {
      delegate.onClose(status, trailers);
    }

    @Override
    public void onReady() {
      delegate.onReady();
    }
  }
  // Implement a client interceptor to retrieve the response headers
  private static class HttpJsonCapturingClientInterceptor implements HttpJsonClientInterceptor {
    private HttpJsonMetadata metadata;

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
                  metadata = responseHeaders;
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
        }
      };
    }
  }

  private HttpJsonCapturingClientInterceptor httpJsonInterceptor;
  private GrpcCapturingClientInterceptor grpcInterceptor;
  private HttpJsonCapturingClientInterceptor httpJsonComplianceInterceptor;
  private GrpcCapturingClientInterceptor grpcComplianceInterceptor;
  private EchoClient grpcClient;
  private EchoClient httpJsonClient;
  private ComplianceClient grpcComplianceClient;
  private ComplianceClient httpJsonComplianceClient;

  @Before
  public void createClients() throws Exception {
    // Create gRPC Interceptor and Client
    grpcInterceptor = new GrpcCapturingClientInterceptor();
    grpcClient = TestClientInitializer.createGrpcEchoClient(ImmutableList.of(grpcInterceptor));

    // Create HttpJson Interceptor and Client
    httpJsonInterceptor = new HttpJsonCapturingClientInterceptor();
    httpJsonClient =
        TestClientInitializer.createHttpJsonEchoClient(ImmutableList.of(httpJsonInterceptor));

    // Create gRPC ComplianceClient and Interceptor
    // Creating a compliance client to test case where api version is not set
    grpcComplianceInterceptor = new GrpcCapturingClientInterceptor();
    grpcComplianceClient =
        TestClientInitializer.createGrpcComplianceClient(
            ImmutableList.of(grpcComplianceInterceptor));

    // Create HttpJson ComplianceClient and Interceptor
    httpJsonComplianceInterceptor = new HttpJsonCapturingClientInterceptor();
    httpJsonComplianceClient =
        TestClientInitializer.createHttpJsonComplianceClient(
            ImmutableList.of(httpJsonComplianceInterceptor));
  }

  @After
  public void destroyClient() {
    grpcClient.close();
    httpJsonClient.close();
    grpcComplianceClient.close();
    httpJsonComplianceClient.close();
  }

  @Test
  public void testGrpc_matchesApiVersion() {
    grpcClient.echo(EchoRequest.newBuilder().build());
    String headerValue = grpcInterceptor.metadata.get(API_VERSION_HEADER_KEY);
    assertThat(headerValue).isEqualTo("v1_20240408");
  }

  @Test
  public void testHttpJson_matchesHeaderName() {
    httpJsonClient.echo(EchoRequest.newBuilder().build());
    ArrayList headerValues =
        (ArrayList) httpJsonInterceptor.metadata.getHeaders().get(HTTP_RESPONSE_HEADER_STRING);
    String headerValue = (String) headerValues.get(0);
    assertThat(headerValue).isEqualTo("v1_20240408");
  }

  @Test
  public void testGrpc_noApiVersion() {
    RepeatRequest request =
            RepeatRequest.newBuilder().setInfo(ComplianceData.newBuilder().setFString("test")).build();
    grpcComplianceClient.repeatDataSimplePath(request);
    assertThat(API_VERSION_HEADER_KEY).isNotIn(grpcComplianceInterceptor.metadata.keys());
  }

  @Test
  public void testHttpJson_noApiVersion() {
    RepeatRequest request =
            RepeatRequest.newBuilder().setInfo(ComplianceData.newBuilder().setFString("test")).build();
    httpJsonComplianceClient.repeatDataSimplePath(request);
    assertThat(API_VERSION_HEADER_KEY)
            .isNotIn(httpJsonComplianceInterceptor.metadata.getHeaders().keySet());
  }

}
