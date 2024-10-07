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

import static com.google.api.gax.rpc.internal.Headers.DYNAMIC_ROUTING_HEADER_KEY;
import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.showcase.v1beta1.ComplianceClient;
import com.google.showcase.v1beta1.ComplianceData;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.RepeatRequest;
import com.google.showcase.v1beta1.RepeatResponse;
import com.google.showcase.v1beta1.it.util.GrpcCapturingClientInterceptor;
import com.google.showcase.v1beta1.it.util.HttpJsonCapturingClientInterceptor;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import io.grpc.Metadata;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ITDynamicRoutingHeaders {
  private static final String SPLIT_TOKEN = "&";
  private static final Metadata.Key<String> REQUEST_PARAMS_HEADER_KEY =
      Metadata.Key.of(DYNAMIC_ROUTING_HEADER_KEY, Metadata.ASCII_STRING_MARSHALLER);

  private static HttpJsonCapturingClientInterceptor httpJsonInterceptor;
  private static HttpJsonCapturingClientInterceptor httpJsonComplianceInterceptor;
  private static GrpcCapturingClientInterceptor grpcInterceptor;
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

    // Create gRPC ComplianceClient and Interceptor
    // Creating a compliance client because echo client doesn't have an implicit enum routing test
    // case
    grpcComplianceInterceptor = new GrpcCapturingClientInterceptor();
    grpcComplianceClient =
        TestClientInitializer.createGrpcComplianceClient(
            ImmutableList.of(grpcComplianceInterceptor));

    // Create HttpJson ComplianceClient and Interceptor
    httpJsonComplianceInterceptor = new HttpJsonCapturingClientInterceptor();
    httpJsonComplianceClient =
        TestClientInitializer.createHttpJsonComplianceClient(
            ImmutableList.of(httpJsonComplianceInterceptor));

    // Create HttpJson Interceptor and Client
    httpJsonInterceptor = new HttpJsonCapturingClientInterceptor();
    httpJsonClient =
        TestClientInitializer.createHttpJsonEchoClient(ImmutableList.of(httpJsonInterceptor));
  }

  @BeforeEach
  void cleanUpParams() {
    grpcInterceptor.metadata = null;
    grpcComplianceInterceptor.metadata = null;
    httpJsonInterceptor.requestMetadata = null;
    httpJsonComplianceInterceptor.requestMetadata = null;
  }

  @AfterAll
  static void destroyClient() throws InterruptedException {
    grpcClient.close();
    grpcComplianceClient.close();

    httpJsonClient.close();
    httpJsonComplianceClient.close();

    grpcClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
    grpcComplianceClient.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);

    httpJsonClient.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
    httpJsonComplianceClient.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  void testGrpc_noRoutingHeaderUsed() {
    grpcClient.echo(EchoRequest.newBuilder().build());
    String headerValue = grpcInterceptor.metadata.get(REQUEST_PARAMS_HEADER_KEY);
    assertThat(headerValue).isNull();
  }

  @Test
  void testHttpJson_noRoutingHeaderUsed() {
    httpJsonClient.echo(EchoRequest.newBuilder().build());
    String headerValue = getDynamicRoutingHeaderValueFromInterceptor(httpJsonInterceptor);
    assertThat(headerValue).isNull();
  }

  @Test
  void testGrpc_emptyHeader() {
    grpcClient.echo(EchoRequest.newBuilder().setHeader("").build());
    String headerValue = grpcInterceptor.metadata.get(REQUEST_PARAMS_HEADER_KEY);
    assertThat(headerValue).isNull();
  }

  @Test
  void testHttpJson_emptyHeader() {
    httpJsonClient.echo(EchoRequest.newBuilder().setHeader("").build());
    String headerValue = getDynamicRoutingHeaderValueFromInterceptor(httpJsonInterceptor);
    assertThat(headerValue).isNull();
  }

  @Test
  void testGrpc_matchesHeaderName() {
    grpcClient.echo(EchoRequest.newBuilder().setHeader("potato").build());
    String headerValue = grpcInterceptor.metadata.get(REQUEST_PARAMS_HEADER_KEY);
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders = ImmutableList.of("header=potato", "routing_id=potato");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  void testGrpc_implicitHeaders_enumsEncodedasInt() {
    RepeatRequest request =
        RepeatRequest.newBuilder().setInfo(ComplianceData.newBuilder().setFKingdomValue(5)).build();
    RepeatResponse actualResponse = grpcComplianceClient.repeatDataSimplePath(request);
    String headerValue = grpcComplianceInterceptor.metadata.get(REQUEST_PARAMS_HEADER_KEY);
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    // fields beside "info.f_kingdom" are default values (false, 0.0, 0) since we are not setting
    // them in the request message.
    List<String> expectedHeaders =
        ImmutableList.of(
            "info.f_bool=false", "info.f_double=0.0", "info.f_int32=0", "info.f_kingdom=5");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  void testHttpJson_implicitHeaders_enumsEncodedasInt() {
    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setInfo(
                ComplianceData.newBuilder()
                    .setFString("test")
                    .setFInt32(1)
                    .setFDouble(2)
                    .setFBool(true)
                    .setFKingdomValue(3))
            .build();
    RepeatResponse actualResponse = httpJsonComplianceClient.repeatDataSimplePath(request);
    String headerValue = getDynamicRoutingHeaderValueFromInterceptor(httpJsonComplianceInterceptor);
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    // In this case, we are setting the values explicitly.
    List<String> expectedHeaders =
        ImmutableList.of(
            "info.f_bool=true",
            "info.f_double=2.0",
            "info.f_int32=1",
            "info.f_kingdom=3",
            "info.f_string=test");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  void testHttpJson_matchesHeaderName() {
    httpJsonClient.echo(EchoRequest.newBuilder().setHeader("potato").build());
    String headerValue = getDynamicRoutingHeaderValueFromInterceptor(httpJsonInterceptor);
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders = ImmutableList.of("header=potato", "routing_id=potato");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  void testGrpc_matchesOtherHeaderName() {
    grpcClient.echo(EchoRequest.newBuilder().setOtherHeader("instances/456").build());
    String headerValue = grpcInterceptor.metadata.get(REQUEST_PARAMS_HEADER_KEY);
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders = ImmutableList.of("baz=instances%2F456");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  void testHttpJson_matchesOtherHeaderName() {
    httpJsonClient.echo(EchoRequest.newBuilder().setOtherHeader("instances/456").build());
    String headerValue = getDynamicRoutingHeaderValueFromInterceptor(httpJsonInterceptor);
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders = ImmutableList.of("baz=instances%2F456");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  void testGrpc_matchesMultipleOfSameRoutingHeader_usesHeader() {
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
  void testHttpJson_matchesMultipleOfSameRoutingHeader_usesHeader() {
    httpJsonClient.echo(EchoRequest.newBuilder().setHeader("projects/123/instances/456").build());
    String headerValue = getDynamicRoutingHeaderValueFromInterceptor(httpJsonInterceptor);
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
  void testGrpc_matchesMultipleOfSameRoutingHeader_usesOtherHeader() {
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
  void testHttpJson_matchesMultipleOfSameRoutingHeader_usesOtherHeader() {
    httpJsonClient.echo(
        EchoRequest.newBuilder().setOtherHeader("projects/123/instances/456").build());
    String headerValue = getDynamicRoutingHeaderValueFromInterceptor(httpJsonInterceptor);
    assertThat(headerValue).isNotNull();
    List<String> requestHeaders =
        Arrays.stream(headerValue.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders =
        ImmutableList.of("baz=projects%2F123%2Finstances%2F456", "qux=projects%2F123");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  void testGrpc_matchesMultipleRoutingHeaders() {
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
  void testHttpJson_matchesMultipleRoutingHeaders() {
    httpJsonClient.echo(
        EchoRequest.newBuilder()
            .setHeader("regions/123/zones/456")
            .setOtherHeader("projects/123/instances/456")
            .build());
    String headerValue = getDynamicRoutingHeaderValueFromInterceptor(httpJsonInterceptor);
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

  private String getDynamicRoutingHeaderValueFromInterceptor(
      HttpJsonCapturingClientInterceptor httpJsonInterceptor) {
    if (httpJsonInterceptor.requestMetadata != null
        && httpJsonInterceptor
            .requestMetadata
            .getHeaders()
            .containsKey(DYNAMIC_ROUTING_HEADER_KEY)) {
      return String.valueOf(
          httpJsonInterceptor.requestMetadata.getHeaders().get(DYNAMIC_ROUTING_HEADER_KEY));
    }
    return null;
  }
}
