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

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.google.api.gax.grpc.GrpcLoggingInterceptor;
import com.google.api.gax.httpjson.HttpJsonLoggingInterceptor;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.it.util.TestAppender;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

// This test needs to run with GOOGLE_SDK_JAVA_LOGGING=true
public class ITLogging {
  private static EchoClient grpcClient;

  private static EchoClient httpjsonClient;

  private TestAppender setupTestLogger(Class<?> clazz) {
    TestAppender testAppender = new TestAppender();
    testAppender.start();
    org.slf4j.Logger logger = LoggerFactory.getLogger(clazz);
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
  void testGrpc_receiveContent_logDebug() {
    TestAppender testAppender = setupTestLogger(GrpcLoggingInterceptor.class);
    assertThat(echoGrpc("grpc-echo?")).isEqualTo("grpc-echo?");
    assertThat(testAppender.events.size()).isEqualTo(2);
    assertThat(testAppender.events.get(0).getMessage())
        .isEqualTo(
            "{\"serviceName\":\"google.showcase.v1beta1.Echo\",\"message\":\"Sending gRPC request\",\"rpcName\":\"google.showcase.v1beta1.Echo/Echo\"}");
    assertThat(testAppender.events.get(0).getLevel()).isEqualTo(ch.qos.logback.classic.Level.INFO);
    assertThat(testAppender.events.get(1).getMessage())
        .isEqualTo(
            "{\"serviceName\":\"google.showcase.v1beta1.Echo\",\"response.status\":\"OK\",\"message\":\"Received Grpc response\",\"rpcName\":\"google.showcase.v1beta1.Echo/Echo\"}");
    testAppender.stop();
  }

  @Test
  void testHttpJson_receiveContent_logDebug() {
    TestAppender testAppender = setupTestLogger(HttpJsonLoggingInterceptor.class);
    assertThat(echoHttpJson("http-echo?")).isEqualTo("http-echo?");
    assertThat(testAppender.events.size()).isEqualTo(2);
    ILoggingEvent loggingEvent1 = testAppender.events.get(0);
    assertThat(loggingEvent1.getMessage())
        .isEqualTo(
            "{\"request.method\":\"POST\",\"request.url\":\"http://localhost:7469\",\"message\":\"Sending HTTP request\",\"rpcName\":\"google.showcase.v1beta1.Echo/Echo\"}");
    assertThat(loggingEvent1.getLevel()).isEqualTo(ch.qos.logback.classic.Level.INFO);
    assertThat(loggingEvent1.getMDCPropertyMap()).hasSize(3);
    assertThat(loggingEvent1.getMDCPropertyMap()).containsKey("rpcName");
    assertThat(testAppender.events.get(1).getMessage())
        .isEqualTo(
            "{\"response.status\":\"200\",\"message\":\"Received HTTP response\",\"rpcName\":\"google.showcase.v1beta1.Echo/Echo\"}");
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
