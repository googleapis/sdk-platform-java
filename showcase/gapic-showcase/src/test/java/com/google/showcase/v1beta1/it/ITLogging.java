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
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.it.util.TestAppender;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

// This test needs to run with GOOGLE_SDK_JAVA_LOGGING=true
public class ITLogging {
  private static EchoClient grpcClient;

  private static EchoClient httpjsonClient;
  static final Logger LOGGER = Logger.getLogger(ITUnaryCallable.class.getName());

  @BeforeAll
  static void createClients() throws Exception {
    // Create gRPC Echo Client
    grpcClient = TestClientInitializer.createGrpcEchoClient();
    // Create Http JSON Echo Client
    httpjsonClient = TestClientInitializer.createHttpJsonEchoClient();

    LOGGER.log(Level.INFO, "This is log message directly from JUL. Clients created.");
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
    LOGGER.log(
        Level.INFO,
        "This is log message directly from JUL. Starting test: testGrpc_receiveContent.");

    TestAppender.clearEvents();
    assertThat(echoGrpc("grpc-echo?")).isEqualTo("grpc-echo?");
    assertThat(TestAppender.events.size()).isEqualTo(3);
    assertThat(TestAppender.events.get(0).getMessage()).isEqualTo("Sending gRPC request");
    assertThat(TestAppender.events.get(0).getLevel()).isEqualTo(ch.qos.logback.classic.Level.DEBUG);
    assertThat(TestAppender.events.get(1).getMessage())
        .isEqualTo("Sending gRPC request: request payload");
    assertThat(TestAppender.events.get(2).getMessage()).isEqualTo("Received Grpc response");
  }

  // @Test
  // void testGrpc_receiveContent_logInfo() {
  //   ch.qos.logback.classic.Logger logger =
  //       (ch.qos.logback.classic.Logger) LoggingUtils.getLogger(GrpcLoggingInterceptor.class);
  //   ch.qos.logback.classic.Level originalLevel = logger.getLevel();
  //   try {
  //     logger.setLevel(ch.qos.logback.classic.Level.INFO);
  //     assertThat(logger.getLevel()).isEqualTo(ch.qos.logback.classic.Level.INFO);
  //
  //     TestAppender.clearEvents();
  //     assertThat(echoGrpc("grpc-echo?")).isEqualTo("grpc-echo?");
  //     assertThat(TestAppender.events.size()).isEqualTo(2);
  //     ILoggingEvent loggingEvent1 = TestAppender.events.get(0);
  //     assertThat(loggingEvent1.getMessage()).isEqualTo("Sending gRPC request");
  //     assertThat(loggingEvent1.getLevel()).isEqualTo(ch.qos.logback.classic.Level.INFO);
  //     assertThat(loggingEvent1.getMDCPropertyMap()).hasSize(3);
  //     assertThat(loggingEvent1.getMDCPropertyMap())
  //         .containsEntry("serviceName", "google.showcase.v1beta1.Echo");
  //     assertThat(loggingEvent1.getMDCPropertyMap())
  //         .containsEntry("rpcName", "google.showcase.v1beta1.Echo/Echo");
  //     assertThat(TestAppender.events.get(1).getMessage()).isEqualTo("Received Grpc response");
  //     assertThat(TestAppender.events.get(1).getLevel())
  //         .isEqualTo(ch.qos.logback.classic.Level.INFO);
  //   } finally {
  //     logger.setLevel(originalLevel);
  //   }
  // }

  @Test
  void testHttpJson_receiveContent_logDebug() {
    TestAppender.clearEvents();
    assertThat(echoHttpJson("http-echo?")).isEqualTo("http-echo?");
    assertThat(TestAppender.events.size()).isEqualTo(3);
    ILoggingEvent loggingEvent1 = TestAppender.events.get(0);
    assertThat(loggingEvent1.getMessage()).isEqualTo("Sending HTTP request");
    assertThat(loggingEvent1.getLevel()).isEqualTo(ch.qos.logback.classic.Level.DEBUG);
    assertThat(loggingEvent1.getMDCPropertyMap()).hasSize(5);
    assertThat(loggingEvent1.getMDCPropertyMap()).containsKey("request.headers");
    assertThat(TestAppender.events.get(1).getMessage())
        .isEqualTo("Sending HTTP request: request payload");
    assertThat(TestAppender.events.get(2).getMessage()).isEqualTo("Received HTTP response");
  }

  // @Test
  // void testHttpJson_receiveContent_logInfo() {
  //
  //   ch.qos.logback.classic.Logger logger =
  //       (ch.qos.logback.classic.Logger) LoggingUtils.getLogger(HttpJsonLoggingInterceptor.class);
  //   ch.qos.logback.classic.Level originalLevel = logger.getLevel();
  //   try {
  //     logger.setLevel(ch.qos.logback.classic.Level.INFO);
  //     assertThat(logger.getLevel()).isEqualTo(ch.qos.logback.classic.Level.INFO);
  //
  //     TestAppender.clearEvents();
  //     assertThat(echoHttpJson("http-echo?")).isEqualTo("http-echo?");
  //     assertThat(TestAppender.events.size()).isEqualTo(2);
  //     ILoggingEvent loggingEvent1 = TestAppender.events.get(0);
  //     assertThat(loggingEvent1.getMessage()).isEqualTo("Sending HTTP request");
  //     assertThat(loggingEvent1.getLevel()).isEqualTo(ch.qos.logback.classic.Level.INFO);
  //     assertThat(loggingEvent1.getMDCPropertyMap()).hasSize(4);
  //     assertThat(loggingEvent1.getMDCPropertyMap())
  //         .containsEntry("rpcName", "google.showcase.v1beta1.Echo/Echo");
  //     assertThat(loggingEvent1.getMDCPropertyMap()).containsEntry("request.method", "POST");
  //     assertThat(loggingEvent1.getMDCPropertyMap())
  //         .containsEntry("request.url", "http://localhost:7469");
  //     assertThat(TestAppender.events.get(1).getMessage()).isEqualTo("Received HTTP response");
  //     assertThat(TestAppender.events.get(1).getLevel())
  //         .isEqualTo(ch.qos.logback.classic.Level.INFO);
  //     assertThat(TestAppender.events.get(1).getMDCPropertyMap())
  //         .containsEntry("response.status", "200");
  //   } finally {
  //     logger.setLevel(originalLevel);
  //   }
  // }

  private String echoGrpc(String value) {
    EchoResponse response = grpcClient.echo(EchoRequest.newBuilder().setContent(value).build());
    return response.getContent();
  }

  private String echoHttpJson(String value) {
    EchoResponse response = httpjsonClient.echo(EchoRequest.newBuilder().setContent(value).build());
    return response.getContent();
  }
}
