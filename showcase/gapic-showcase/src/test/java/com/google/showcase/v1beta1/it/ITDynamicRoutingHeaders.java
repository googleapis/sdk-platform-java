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

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
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
import com.google.showcase.v1beta1.EchoSettings;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ITDynamicRoutingHeaders {
  private static final String SPLIT_TOKEN = "&";

  private static class CapturingClientInterceptor implements HttpJsonClientInterceptor {
    private volatile String requestParam;

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
          requestParam = callOptions.getRequestParamsHeader();
        }
      };
    }
  }

  private ITDynamicRoutingHeaders.CapturingClientInterceptor httpJsonInterceptor;

  private EchoClient httpJsonClient;

  @Before
  public void createClients() throws Exception {
    httpJsonInterceptor = new ITDynamicRoutingHeaders.CapturingClientInterceptor();
    // Create Http JSON Echo Client
    EchoSettings httpJsonEchoSettings =
        EchoSettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .setInterceptorProvider(() -> Collections.singletonList(httpJsonInterceptor))
                    .build())
            .build();
    httpJsonClient = EchoClient.create(httpJsonEchoSettings);
  }

  @After
  public void destroyClient() {
    httpJsonClient.close();
  }

  @Test
  public void testHttpJson_noRoutingHeaderUsed() {
    httpJsonClient.echo(EchoRequest.newBuilder().build());
    assertThat(httpJsonInterceptor.requestParam).isNull();
  }

  @Test
  public void testHttpJson_emptyHeader() {
    httpJsonClient.echo(EchoRequest.newBuilder().setHeader("").build());
    assertThat(httpJsonInterceptor.requestParam).isNull();
  }

  @Test
  public void testHttpJson_matchesHeaderName() {
    httpJsonClient.echo(EchoRequest.newBuilder().setHeader("potato").build());
    List<String> requestHeaders =
        Arrays.stream(httpJsonInterceptor.requestParam.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders = ImmutableList.of("header=potato", "routing_id=potato");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  public void testHttpJson_matchesOtherHeaderName() {
    httpJsonClient.echo(EchoRequest.newBuilder().setOtherHeader("instances/456").build());
    List<String> requestHeaders =
        Arrays.stream(httpJsonInterceptor.requestParam.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders = ImmutableList.of("baz=instances%2F456");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  public void testHttpJson_matchesMultipleOfSameRoutingHeader_usesHeader() {
    httpJsonClient.echo(EchoRequest.newBuilder().setHeader("projects/123/instances/456").build());
    List<String> requestHeaders =
        Arrays.stream(httpJsonInterceptor.requestParam.split(SPLIT_TOKEN)).collect(Collectors.toList());
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
  public void testHttpJson_matchesMultipleOfSameRoutingHeader_usesOtherHeader() {
    httpJsonClient.echo(
        EchoRequest.newBuilder().setOtherHeader("projects/123/instances/456").build());
    List<String> requestHeaders =
        Arrays.stream(httpJsonInterceptor.requestParam.split(SPLIT_TOKEN)).collect(Collectors.toList());
    List<String> expectedHeaders =
        ImmutableList.of("baz=projects%2F123%2Finstances%2F456", "qux=projects%2F123");
    assertThat(requestHeaders).containsExactlyElementsIn(expectedHeaders);
  }

  @Test
  public void testHttpJson_matchesMultipleRoutingHeaders() {
    httpJsonClient.echo(
        EchoRequest.newBuilder()
            .setHeader("regions/123/zones/456")
            .setOtherHeader("projects/123/instances/456")
            .build());
    List<String> requestHeaders =
        Arrays.stream(httpJsonInterceptor.requestParam.split(SPLIT_TOKEN)).collect(Collectors.toList());
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
