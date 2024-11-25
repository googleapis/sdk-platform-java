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
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.api.gax.grpc.GrpcStatusCode;
import com.google.api.gax.rpc.CancelledException;
import com.google.api.gax.rpc.StatusCode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.rpc.Status;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ITUnaryCallable {

  private static EchoClient grpcClient;

  private static EchoClient httpjsonClient;
  static final Logger LOGGER = Logger.getLogger(ITUnaryCallable.class.getName());

  @BeforeAll
  static void createClients() throws Exception {
    // Create gRPC Echo Client
    grpcClient = TestClientInitializer.createGrpcEchoClient();
    // Create Http JSON Echo Client
    httpjsonClient = TestClientInitializer.createHttpJsonEchoClient();

    // Get the root logger
    Logger rootLogger =
        LogManager.getLogManager().getLogger("");
    // Set the root logger's level to ALL or FINEST to see DEBUG messages
    // rootLogger.setLevel(Level.ALL); // or rootLogger.setLevel(Level.FINEST);
    // Remove any existing handlers (if needed)
    for (Handler handler : rootLogger.getHandlers()) {
      rootLogger.removeHandler(handler);
    }

    // Create a ConsoleHandler
    ConsoleHandler consoleHandler = new ConsoleHandler();
    consoleHandler.setLevel(Level.ALL);
    consoleHandler.setFormatter(new SimpleFormatter());
    // String targetClassName = "com.google.api.gax.logging.LoggingUtils$JulWrapperLogger";
    // Filter filter = new Filter() {
    //   @Override
    //   public boolean isLoggable(LogRecord record) {
    //     return record.getLoggerName().equals(targetClassName);
    //   }
    // };
    // consoleHandler.setFilter(filter);

    // Add the ConsoleHandler to the root logger
    rootLogger.addHandler(consoleHandler);
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
  void test() {
    Gson gson = new Gson();
    JsonObject jsonObject1 = gson.fromJson("{\"name\":\"John\", \"age\":30}", JsonObject.class);
    JsonObject jsonObject2 = gson.fromJson("{\"city\":\"New York\", \"country\":\"USA\"}", JsonObject.class);

    JsonObject mergedObject = jsonObject1.deepCopy();
    mergedObject.entrySet().forEach(entry -> jsonObject2.add(entry.getKey(), entry.getValue()));

    System.out.println(jsonObject2); // Output: {"city":"New York", "country":"USA", "name":"John", "age":30}
  }

  @Test
  void testGrpc_receiveContent() {
    LOGGER.log(
        Level.INFO,
        "This is log message directly from JUL. Starting test: testGrpc_receiveContent.");
    assertThat(echoGrpc("grpc-echo?")).isEqualTo("grpc-echo?");
    assertThat(echoGrpc("grpc-echo!")).isEqualTo("grpc-echo!");
  }

  @Test
  void testGrpc_serverResponseError_throwsException() {
    Status cancelledStatus =
        Status.newBuilder().setCode(StatusCode.Code.CANCELLED.ordinal()).build();
    EchoRequest requestWithServerError = EchoRequest.newBuilder().setError(cancelledStatus).build();
    CancelledException exception =
        assertThrows(CancelledException.class, () -> grpcClient.echo(requestWithServerError));
    assertThat(exception.getStatusCode().getCode()).isEqualTo(GrpcStatusCode.Code.CANCELLED);
  }

  @Test
  void testHttpJson_receiveContent() {
    assertThat(echoHttpJson("http-echo?")).isEqualTo("http-echo?");
    assertThat(echoHttpJson("http-echo!")).isEqualTo("http-echo!");
  }

  @Test
  void testHttpJson_serverResponseError_throwsException() {
    EchoRequest requestWithServerError =
        EchoRequest.newBuilder()
            .setError(Status.newBuilder().setCode(StatusCode.Code.CANCELLED.ordinal()).build())
            .build();
    CancelledException exception =
        assertThrows(CancelledException.class, () -> httpjsonClient.echo(requestWithServerError));
    assertThat(exception.getStatusCode().getCode()).isEqualTo(StatusCode.Code.CANCELLED);
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
