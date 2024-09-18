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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.api.gax.httpjson.*;
import com.google.api.gax.rpc.ApiClientHeaderProvider;
import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.api.gax.rpc.StubSettings;
import com.google.common.collect.ImmutableList;
import com.google.showcase.v1beta1.*;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import com.google.showcase.v1beta1.stub.ComplianceStubSettings;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import io.grpc.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

// TODO: add testing on error responses once feat is implemented in showcase.
//  https://github.com/googleapis/gapic-showcase/pull/1456
// TODO: watch for showcase gRPC trailer changes suggested in
// https://github.com/googleapis/gapic-showcase/pull/1509#issuecomment-2089147103
class ITVersionHeaders {
  private static final String HTTP_RESPONSE_HEADER_STRING =
      "x-showcase-request-" + ApiClientHeaderProvider.API_VERSION_HEADER_KEY;
  private static final String HTTP_CLIENT_API_HEADER_KEY =
      "x-showcase-request-" + ApiClientHeaderProvider.getDefaultApiClientHeaderKey();
  private static final Metadata.Key<String> API_VERSION_HEADER_KEY =
      Metadata.Key.of(
          ApiClientHeaderProvider.API_VERSION_HEADER_KEY, Metadata.ASCII_STRING_MARSHALLER);

  private static final Metadata.Key<String> API_CLIENT_HEADER_KEY =
      Metadata.Key.of(
          ApiClientHeaderProvider.getDefaultApiClientHeaderKey(), Metadata.ASCII_STRING_MARSHALLER);

  private static final String EXPECTED_ECHO_API_VERSION = "v1_20240408";
  private static final String CUSTOM_API_VERSION = "user-supplied-version";
  private static final String EXPECTED_EXCEPTION_MESSAGE =
      "Header provider can't override the header: "
          + ApiClientHeaderProvider.API_VERSION_HEADER_KEY;

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

  private static HttpJsonCapturingClientInterceptor httpJsonInterceptor;
  private static GrpcCapturingClientInterceptor grpcInterceptor;
  private static HttpJsonCapturingClientInterceptor httpJsonComplianceInterceptor;
  private static GrpcCapturingClientInterceptor grpcComplianceInterceptor;
  private static EchoClient grpcClient;
  private static EchoClient httpJsonClient;
  private static ComplianceClient grpcComplianceClient;
  private static ComplianceClient httpJsonComplianceClient;

  @BeforeAll
  static void createClients() throws Exception {
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

  @AfterAll
  static void destroyClient() throws InterruptedException {
    grpcClient.close();
    httpJsonClient.close();
    grpcComplianceClient.close();
    httpJsonComplianceClient.close();

    grpcClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
    httpJsonClient.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
    grpcComplianceClient.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
    httpJsonComplianceClient.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  void testGrpc_matchesApiVersion() {
    grpcClient.echo(EchoRequest.newBuilder().build());
    String headerValue = grpcInterceptor.metadata.get(API_VERSION_HEADER_KEY);
    assertThat(headerValue).isEqualTo(EXPECTED_ECHO_API_VERSION);
  }

  @Test
  void testHttpJson_matchesHeaderName() {
    httpJsonClient.echo(EchoRequest.newBuilder().build());
    ArrayList headerValues =
        (ArrayList) httpJsonInterceptor.metadata.getHeaders().get(HTTP_RESPONSE_HEADER_STRING);
    String headerValue = (String) headerValues.get(0);
    assertThat(headerValue).isEqualTo(EXPECTED_ECHO_API_VERSION);
  }

  @Test
  void testGrpc_noApiVersion() {
    RepeatRequest request =
        RepeatRequest.newBuilder().setInfo(ComplianceData.newBuilder().setFString("test")).build();
    grpcComplianceClient.repeatDataSimplePath(request);
    assertThat(API_VERSION_HEADER_KEY).isNotIn(grpcComplianceInterceptor.metadata.keys());
  }

  @Test
  void testHttpJson_noApiVersion() {
    RepeatRequest request =
        RepeatRequest.newBuilder().setInfo(ComplianceData.newBuilder().setFString("test")).build();
    httpJsonComplianceClient.repeatDataSimplePath(request);
    assertThat(API_VERSION_HEADER_KEY)
        .isNotIn(httpJsonComplianceInterceptor.metadata.getHeaders().keySet());
  }

  @Test
  void testGrpcEcho_userApiVersionThrowsException() throws IOException {
    StubSettings stubSettings =
        grpcClient
            .getSettings()
            .getStubSettings()
            .toBuilder()
            .setHeaderProvider(
                FixedHeaderProvider.create(
                    ApiClientHeaderProvider.API_VERSION_HEADER_KEY, CUSTOM_API_VERSION))
            .build();

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> EchoClient.create(EchoSettings.create((EchoStubSettings) stubSettings)));
    assertThat(exception.getMessage()).isEqualTo(EXPECTED_EXCEPTION_MESSAGE);
  }

  @Test
  void testHttpJsonEcho_userApiVersionThrowsException() throws IOException {
    StubSettings stubSettings =
        httpJsonClient
            .getSettings()
            .getStubSettings()
            .toBuilder()
            .setHeaderProvider(
                FixedHeaderProvider.create(
                    ApiClientHeaderProvider.API_VERSION_HEADER_KEY, CUSTOM_API_VERSION))
            .build();

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> EchoClient.create(EchoSettings.create((EchoStubSettings) stubSettings)));
    assertThat(exception.getMessage()).isEqualTo(EXPECTED_EXCEPTION_MESSAGE);
  }

  @Test
  void testGrpcCompliance_userApiVersionSetSuccess() throws IOException {
    StubSettings stubSettingsWithApiVersionHeader =
        grpcComplianceClient
            .getSettings()
            .getStubSettings()
            .toBuilder()
            .setHeaderProvider(
                FixedHeaderProvider.create(
                    ApiClientHeaderProvider.API_VERSION_HEADER_KEY, CUSTOM_API_VERSION))
            .build();
    try (ComplianceClient customComplianceClient =
        ComplianceClient.create(
            ComplianceSettings.create((ComplianceStubSettings) stubSettingsWithApiVersionHeader))) {

      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setInfo(ComplianceData.newBuilder().setFString("test"))
              .build();
      customComplianceClient.repeatDataSimplePath(request);
      String headerValue = grpcComplianceInterceptor.metadata.get(API_VERSION_HEADER_KEY);
      assertThat(headerValue).isEqualTo(CUSTOM_API_VERSION);
    }
  }

  @Test
  void testHttpJsonCompliance_userApiVersionSetSuccess() throws IOException {
    StubSettings httpJsonStubSettingsWithApiVersionHeader =
        httpJsonComplianceClient
            .getSettings()
            .getStubSettings()
            .toBuilder()
            .setHeaderProvider(
                FixedHeaderProvider.create(
                    ApiClientHeaderProvider.API_VERSION_HEADER_KEY, CUSTOM_API_VERSION))
            .build();
    try (ComplianceClient customComplianceClient =
        ComplianceClient.create(
            ComplianceSettings.create(
                (ComplianceStubSettings) httpJsonStubSettingsWithApiVersionHeader))) {

      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setInfo(ComplianceData.newBuilder().setFString("test"))
              .build();
      customComplianceClient.repeatDataSimplePath(request);

      ArrayList headerValues =
          (ArrayList)
              httpJsonComplianceInterceptor.metadata.getHeaders().get(HTTP_RESPONSE_HEADER_STRING);
      String headerValue = (String) headerValues.get(0);
      assertThat(headerValue).isEqualTo(CUSTOM_API_VERSION);
    }
  }

  @Test
  void testGrpcCall_sendsCorrectApiClientHeader() {
    Pattern defautlGrpcHeaderPattern =
        Pattern.compile("gl-java/.* gapic/.*?--protobuf-.* gax/.* grpc/.* protobuf/.*");
    grpcClient.echo(EchoRequest.newBuilder().build());
    String headerValue = grpcInterceptor.metadata.get(API_CLIENT_HEADER_KEY);
    assertTrue(defautlGrpcHeaderPattern.matcher(headerValue).matches());
  }

  @Test
  void testHttpJson_sendsCorrectApiClientHeader() {
    Pattern defautlHttpHeaderPattern =
        Pattern.compile("gl-java/.* gapic/.*?--protobuf-.* gax/.* rest/ protobuf/.*");
    httpJsonClient.echo(EchoRequest.newBuilder().build());
    ArrayList<String> headerValues =
        (ArrayList<String>)
            httpJsonInterceptor.metadata.getHeaders().get(HTTP_CLIENT_API_HEADER_KEY);
    String headerValue = headerValues.get(0);
    assertTrue(defautlHttpHeaderPattern.matcher(headerValue).matches());
  }
}
