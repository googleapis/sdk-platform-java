/*
 * Copyright 2025 Google LLC
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
import com.google.api.gax.grpc.GrpcLoggingInterceptor;
import com.google.api.gax.httpjson.HttpJsonLoggingInterceptor;
import com.google.common.collect.ImmutableMap;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import org.slf4j.event.KeyValuePair;

// This test needs to run with GOOGLE_SDK_JAVA_LOGGING=true
// mvn verify -P
// '!showcase,enable-integration-tests,loggingTestBase,slf4j1_logback '
public class ITLogging1x {
  private static EchoClient grpcClient;

  private static EchoClient httpjsonClient;

  private static final String ECHO_STRING = "echo?";

  private static Logger logger = LoggerFactory.getLogger(ITLogging1x.class);

  private TestAppender setupTestLogger(Class<?> clazz) {
    TestAppender testAppender = new TestAppender();
    testAppender.start();
    Logger logger = LoggerFactory.getLogger(clazz);
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

    grpcClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
    httpjsonClient.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  void test() {
    assertThat(logger.isInfoEnabled()).isTrue();
    assertThat(logger.isDebugEnabled()).isTrue();
  }

  @Test
  void testGrpc_receiveContent_logDebug() {
    TestAppender testAppender = setupTestLogger(GrpcLoggingInterceptor.class);
    assertThat(echoGrpc(ECHO_STRING)).isEqualTo(ECHO_STRING);

    assertThat(testAppender.events.size()).isEqualTo(2);
    // logging event for request
    ILoggingEvent loggingEvent1 = testAppender.events.get(0);
    assertThat(loggingEvent1.getMessage()).isEqualTo("Sending gRPC request");
    assertThat(loggingEvent1.getLevel()).isEqualTo(Level.DEBUG);
    Map<String, String> mdcPropertyMap = loggingEvent1.getMDCPropertyMap();
    assertThat(mdcPropertyMap)
        .containsAtLeastEntriesIn(
            ImmutableMap.of(
                "serviceName",
                "google.showcase.v1beta1.Echo",
                "rpcName",
                "google.showcase.v1beta1.Echo/Echo"));
    assertThat(mdcPropertyMap).containsKey("request.headers");
    assertThat(mdcPropertyMap.get("request.headers")).startsWith("{\"x-goog-api-");

    assertThat(mdcPropertyMap).containsKey("request.payload");
    assertThat(mdcPropertyMap.get("request.payload"))
        .startsWith("{\"content\":\"echo?\",\"requestId\":");

    // logging event for response
    ILoggingEvent loggingEvent2 = testAppender.events.get(1);
    assertThat(loggingEvent2.getMessage()).isEqualTo("Received Grpc response");
    assertThat(loggingEvent2.getLevel()).isEqualTo(Level.DEBUG);
    Map<String, String> responseMdcPropertyMap = loggingEvent2.getMDCPropertyMap();
    assertThat(responseMdcPropertyMap)
        .containsAtLeastEntriesIn(
            ImmutableMap.of(
                "serviceName",
                "google.showcase.v1beta1.Echo",
                "rpcName",
                "google.showcase.v1beta1.Echo/Echo",
                "response.status",
                "OK"));
    assertThat(responseMdcPropertyMap).containsKey("response.payload");
    assertThat(responseMdcPropertyMap).containsKey("response.headers");

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
    Map<String, String> mdcPropertyMap = loggingEvent1.getMDCPropertyMap();
    assertThat(mdcPropertyMap).containsEntry("rpcName", "google.showcase.v1beta1.Echo/Echo");
    assertThat(mdcPropertyMap).containsKey("request.url");
    assertThat(mdcPropertyMap).containsKey("request.headers");
    assertThat(mdcPropertyMap.get("request.headers")).startsWith("{\"x-goog-api-");
    assertThat(mdcPropertyMap).containsKey("request.payload");
    assertThat(mdcPropertyMap.get("request.payload"))
        .startsWith("{\"content\":\"echo?\",\"requestId\":");

    // logging event for response
    ILoggingEvent loggingEvent2 = testAppender.events.get(1);
    assertThat(loggingEvent2.getMessage()).isEqualTo("Received Grpc response");
    assertThat(loggingEvent2.getLevel()).isEqualTo(Level.DEBUG);
    Map<String, String> responseMdcPropertyMap = loggingEvent2.getMDCPropertyMap();
    assertThat(responseMdcPropertyMap)
        .containsAtLeastEntriesIn(
            ImmutableMap.of(
                "rpcName", "google.showcase.v1beta1.Echo/Echo",
                "response.status", "200"));
    assertThat(responseMdcPropertyMap).containsKey("response.payload");
    assertThat(responseMdcPropertyMap).containsKey("response.headers");
    testAppender.stop();
  }

  private String echoGrpc(String value) {
    EchoResponse response = grpcClient.echo(EchoRequest.newBuilder().setContent(value).build());
    return response.getContent();
  }

  private String echoHttpJson(String value) {
    EchoResponse response = httpjsonClient.echo(EchoRequest.newBuilder().setContent(value).build());
    return response.getContent();
  }
}
