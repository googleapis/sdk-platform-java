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

package com.google.showcase.v1beta1.it.logging;

import static com.google.common.truth.Truth.assertThat;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.google.api.gax.grpc.GrpcLoggingInterceptor;
import com.google.api.gax.httpjson.HttpJsonLoggingInterceptor;
import com.google.common.collect.ImmutableMap;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.KeyValuePair;

// This test needs to run with GOOGLE_SDK_JAVA_LOGGING=true
// mvn clean test -Dslf4j2_logback -Dtest=com.google.showcase.v1beta1.it.logging.ITLogging
public class ITLogging {
  private static EchoClient grpcClient;

  private static EchoClient httpjsonClient;

  private static final KeyValuePair SERVICE_NAME_KEY_VALUE_PAIR =
      new KeyValuePair("serviceName", "google.showcase.v1beta1.Echo");
  private static final KeyValuePair RPC_NAME_KEY_VALUE_PAIR =
      new KeyValuePair("rpcName", "google.showcase.v1beta1.Echo/Echo");
  private static final KeyValuePair RESPONSE_STATUS_KEY_VALUE_PAIR =
      new KeyValuePair("response.status", "OK");
  private static final KeyValuePair RESPONSE_STATUS_KEY_VALUE_PAIR_HTTP =
      new KeyValuePair("response.status", "200");

  private static final KeyValuePair RESPONSE_HEADERS_KEY_VALUE_PAIR =
      new KeyValuePair("response.headers", ImmutableMap.of("content-type", "application/grpc"));

  private static final String ECHO_STRING = "echo?";

  private static Logger logger = LoggerFactory.getLogger(ITLogging.class);
  private TestAppender setupTestLogger(Class<?> clazz) {
    TestAppender testAppender = new TestAppender();
    testAppender.start();
    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    listAppender.start();
    org.slf4j.Logger logger = LoggerFactory.getLogger(clazz);
    ((ch.qos.logback.classic.Logger) logger).setLevel(Level.DEBUG);
    ((ch.qos.logback.classic.Logger) logger).addAppender(testAppender);
    return testAppender;
  }

  @BeforeAll
  static void createClients() throws Exception {
    // Create gRPC Echo Client
    grpcClient = TestClientInitializer.createGrpcEchoClient();
    // Create Http JSON Echo Client
    httpjsonClient = TestClientInitializer.createHttpJsonEchoClient();
  }

  @AfterAll
  static void destroyClients() throws InterruptedException {
    grpcClient.close();
    httpjsonClient.close();

    grpcClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS,
TimeUnit.SECONDS);
    httpjsonClient.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  // only run when GOOGLE_SDK_JAVA_LOGGING!=true
  @Test
  void testloggingDisabled() {
    TestAppender testAppender = setupTestLogger(GrpcLoggingInterceptor.class);
    assertThat(echoGrpc(ECHO_STRING)).isEqualTo(ECHO_STRING);

    assertThat(testAppender.events.size()).isEqualTo(0);
  }

  @Test
  void testGrpc_receiveContent_logDebug() {
    TestAppender testAppender = setupTestLogger(GrpcLoggingInterceptor.class);
    assertThat(echoGrpc(ECHO_STRING)).isEqualTo(ECHO_STRING);

    assertThat(true).isTrue();

    assertThat(testAppender.events.size()).isEqualTo(2);
    // logging event for request
    ILoggingEvent loggingEvent1 = testAppender.events.get(0);
    assertThat(loggingEvent1.getMessage()).isEqualTo("Sending gRPC request");
    assertThat(loggingEvent1.getLevel()).isEqualTo(Level.DEBUG);
    List<KeyValuePair> keyValuePairs = loggingEvent1.getKeyValuePairs();
    assertThat(keyValuePairs.size()).isEqualTo(4);
    assertThat(keyValuePairs)
        .containsAtLeast(
            SERVICE_NAME_KEY_VALUE_PAIR, RPC_NAME_KEY_VALUE_PAIR);

    for (KeyValuePair kvp : keyValuePairs) {
      if (kvp.key.equals("request.payload")) {
        Map payload = (Map) kvp.value;
        assertThat(payload.get("content")).isEqualTo(ECHO_STRING);
        assertThat(payload.get("requestId")).isNotNull();
        assertThat(payload.get("otherRequestId")).isNotNull();
      }
      if (kvp.key.equals("request.headers")) {
        Map headers = (Map) kvp.value;
        assertThat(headers).containsKey("x-goog-api-version");
        assertThat(headers).containsKey("x-goog-api-client");
      }
    }

    // logging event for response
    ILoggingEvent loggingEvent2 = testAppender.events.get(1);
    assertThat(loggingEvent2.getMessage()).isEqualTo("Received Grpc response");

    assertThat(loggingEvent2.getLevel()).isEqualTo(Level.DEBUG);
    List<KeyValuePair> keyValuePairs2 = loggingEvent2.getKeyValuePairs();
    assertThat(keyValuePairs2.size()).isEqualTo(5);
    assertThat(keyValuePairs2)
        .containsAtLeast(
            RESPONSE_STATUS_KEY_VALUE_PAIR,
            RESPONSE_HEADERS_KEY_VALUE_PAIR,
            SERVICE_NAME_KEY_VALUE_PAIR,
            RPC_NAME_KEY_VALUE_PAIR);
    for (KeyValuePair kvp : keyValuePairs2) {
      if (kvp.key.equals("response.payload")) {
        Map payload = (Map) kvp.value;
        assertThat(payload.get("content")).isEqualTo(ECHO_STRING);
        assertThat(payload.get("requestId")).isNotNull();
        assertThat(payload.get("otherRequestId")).isNotNull();
      }
    }
    testAppender.stop();
  }

  @Test
  void testHttpJson_receiveContent_logDebug() {
    TestAppender testAppender = setupTestLogger(HttpJsonLoggingInterceptor.class);
    assertThat(echoHttpJson(ECHO_STRING)).isEqualTo(ECHO_STRING);
    assertThat(testAppender.events.size()).isEqualTo(2);
    // logging event for request
    ILoggingEvent loggingEvent1 = testAppender.events.get(0);
    assertThat(loggingEvent1.getMessage()).isEqualTo("Sending gRPC request");
    assertThat(loggingEvent1.getLevel()).isEqualTo(Level.DEBUG);
    List<KeyValuePair> keyValuePairs = loggingEvent1.getKeyValuePairs();
    assertThat(keyValuePairs.size()).isEqualTo(4);
    assertThat(keyValuePairs)
        .contains(
            RPC_NAME_KEY_VALUE_PAIR);

    for (KeyValuePair kvp : keyValuePairs) {
      if (kvp.key.equals("request.payload")) {
        Map payload = (Map) kvp.value;
        assertThat(payload.get("content")).isEqualTo(ECHO_STRING);
        assertThat(payload.get("requestId")).isNotNull();
        assertThat(payload.get("otherRequestId")).isNotNull();
      }
      if (kvp.key.equals("request.headers")) {
        Map headers = (Map) kvp.value;
        assertThat(headers).containsKey("x-goog-api-version");
        assertThat(headers).containsKey("x-goog-api-client");
      }
    }

    // logging event for response
    ILoggingEvent loggingEvent2 = testAppender.events.get(1);
    assertThat(loggingEvent2.getMessage()).isEqualTo("Received Grpc response");

    assertThat(loggingEvent2.getLevel()).isEqualTo(Level.DEBUG);
    List<KeyValuePair> keyValuePairs2 = loggingEvent2.getKeyValuePairs();
    assertThat(keyValuePairs2.size()).isEqualTo(4);
    assertThat(keyValuePairs2)
        .containsAtLeast(
            RESPONSE_STATUS_KEY_VALUE_PAIR_HTTP,
            RPC_NAME_KEY_VALUE_PAIR);
    for (KeyValuePair kvp : keyValuePairs2) {
      if (kvp.key.equals("response.payload")) {
        Map payload = (Map) kvp.value;
        assertThat(payload.get("content")).isEqualTo(ECHO_STRING);
        assertThat(payload.get("requestId")).isNotNull();
        assertThat(payload.get("otherRequestId")).isNotNull();
      }
      if (kvp.key.equals("response.headers")) {
        Map headers = (Map) kvp.value;
        assertThat(headers.size()).isEqualTo(3);
      }
    }    testAppender.stop();
  }

  private String echoGrpc(String value) {
    EchoResponse response = grpcClient.echo(EchoRequest.newBuilder().setContent(value).build());
    return response.getContent();
  }

  private String echoHttpJson(String value) {
    EchoResponse response =
httpjsonClient.echo(EchoRequest.newBuilder().setContent(value).build());
    return response.getContent();
  }
}
