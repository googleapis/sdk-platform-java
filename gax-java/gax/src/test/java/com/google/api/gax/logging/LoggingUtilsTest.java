/*
 * Copyright 2025 Google LLC
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google LLC nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.google.api.gax.logging;

import static com.google.api.gax.logging.LoggingUtils.messageToMapWithGson;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.api.gax.logging.LoggingUtils.LoggerFactoryProvider;
import com.google.api.gax.logging.LoggingUtils.ThrowingRunnable;
import com.google.api.gax.rpc.internal.EnvironmentProvider;
import com.google.protobuf.Field;
import com.google.protobuf.Field.Cardinality;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Option;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.slf4j.helpers.NOPLogger;
import org.slf4j.helpers.NOPLoggerFactory;

class LoggingUtilsTest {

  // private static final Logger LOGGER = LoggerFactory.getLogger(LoggingUtilsTest.class);
  private EnvironmentProvider envProvider = Mockito.mock(EnvironmentProvider.class);

  @Test
  void testGetLogger_loggingEnabled_slf4jBindingPresent() {
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("true");
    LoggingUtils.setEnvironmentProvider(envProvider);
    // should get ILoggerFactory from TestServiceProvider
    Logger logger = LoggingUtils.getLogger(LoggingUtilsTest.class);
    Assertions.assertInstanceOf(Logger.class, logger);
    Assertions.assertNotEquals(NOPLogger.class, logger.getClass());
  }

  @Test
  void testGetLogger_loggingDisabled_shouldGetNOPLogger() {
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("false");
    LoggingUtils.setEnvironmentProvider(envProvider);

    Logger logger = LoggingUtils.getLogger(LoggingUtilsTest.class);
    assertEquals(NOPLogger.class, logger.getClass());
    assertFalse(logger.isInfoEnabled());
    assertFalse(logger.isDebugEnabled());
  }

  @Test
  void testGetLogger_loggingEnabled_noBinding_shouldGetNOPLogger() {
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("true");
    LoggingUtils.setEnvironmentProvider(envProvider);
    // Create a mock LoggerFactoryProvider, mimic SLF4J's behavior to return NOPLoggerFactory when
    // no binding
    LoggerFactoryProvider mockLoggerFactoryProvider = mock(LoggerFactoryProvider.class);
    ILoggerFactory nopLoggerFactory = new NOPLoggerFactory();
    when(mockLoggerFactoryProvider.getLoggerFactory()).thenReturn(nopLoggerFactory);

    // Use the mock LoggerFactoryProvider in getLogger()
    Logger logger = LoggingUtils.getLogger(LoggingUtilsTest.class, mockLoggerFactoryProvider);

    // Assert that the returned logger is a NOPLogger
    Assertions.assertInstanceOf(NOPLogger.class, logger);
  }

  @Test
  void testIsLoggingEnabled_true() {
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("true");
    LoggingUtils.setEnvironmentProvider(envProvider);
    Assertions.assertTrue(LoggingUtils.isLoggingEnabled());
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("TRUE");
    LoggingUtils.setEnvironmentProvider(envProvider);
    Assertions.assertTrue(LoggingUtils.isLoggingEnabled());
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("True");
    LoggingUtils.setEnvironmentProvider(envProvider);
    Assertions.assertTrue(LoggingUtils.isLoggingEnabled());
  }

  @Test
  void testIsLoggingEnabled_defaultToFalse() {
    LoggingUtils.setEnvironmentProvider(envProvider);
    assertFalse(LoggingUtils.isLoggingEnabled());
  }

  @Test
  void testLog_slf4J2xLogger() {
    Map<String, Object> contextMap = new HashMap<>();
    contextMap.put("key1", "value");
    contextMap.put("key2", 123);
    String message = "Test message";
    TestLogger testLogger = new TestLogger("test-logger");
    LoggingUtils.log(testLogger, org.slf4j.event.Level.DEBUG, contextMap, message);

    assertEquals(message, testLogger.messageList.get(0));

    assertEquals("value", testLogger.keyValuePairsMap.get("key1"));
    assertEquals(123, testLogger.keyValuePairsMap.get("key2"));
  }

  @Test
  void testLogWithMDC_InfoLevel_VerifyMDC() {
    // this test replies on TestMDCApapter and TestServiceProvider
    TestLogger testLogger = new TestLogger("test-logger");
    Map<String, Object> contextMap = new HashMap<>();
    contextMap.put("key1", "value1");
    contextMap.put("key2", 123);
    String message = "Test message";

    // need a service provider
    LoggingUtils.logWithMDC(testLogger, Level.INFO, contextMap, message);

    Map<String, String> mdcMap = testLogger.MDCMap;

    assertEquals(2, mdcMap.size());
    assertEquals("value1", mdcMap.get("key1"));
    assertEquals("123", mdcMap.get("key2"));

    assertEquals(message, testLogger.messageList.get(0));
  }

  @Test
  void testMessageToMap_ValidMessage() throws InvalidProtocolBufferException {
    Field field =
        Field.newBuilder()
            .setNumber(2)
            .setName("field_name1")
            .addOptions(Option.newBuilder().setName("opt_name1").build())
            .addOptions(Option.newBuilder().setName("opt_name2").build())
            .setCardinality(Cardinality.CARDINALITY_OPTIONAL)
            .build();

    Map<String, Object> map = messageToMapWithGson(field);

    assertEquals("field_name1", map.get("name"));
    assertEquals(2.0, map.get("number")); // Gson converts ints to doubles by default
  }

  @Test
  void testRecordServiceRpcAndRequestHeaders_infoEnabled() {
    String serviceName = "testService";
    String rpcName = "testRpc";
    String endpoint = "http://test.com/endpoint";
    Map<String, String> requestHeaders = new HashMap<>();
    requestHeaders.put("header1", "value1");
    requestHeaders.put("header2", "value2");

    LogData.Builder logDataBuilder = LogData.builder();

    TestLogger testLogger = new TestLogger("test-logger", true, true);

    LoggingUtils.recordServiceRpcAndRequestHeaders(
        serviceName, rpcName, endpoint, requestHeaders, logDataBuilder, testLogger);

    LogData logData = logDataBuilder.build();
    assertEquals(serviceName, logData.serviceName());
    assertEquals(rpcName, logData.rpcName());
    assertEquals(endpoint, logData.httpUrl());
    assertEquals(requestHeaders, logData.requestHeaders());
  }

  @Test
  void testRecordServiceRpcAndRequestHeaders_infoDisabled() {
    String serviceName = "testService";
    String rpcName = "testRpc";
    String endpoint = "http://test.com/endpoint";
    Map<String, String> requestHeaders = new HashMap<>();
    requestHeaders.put("header1", "value1");
    requestHeaders.put("header2", "value2");

    LogData.Builder logDataBuilder = LogData.builder();

    TestLogger testLogger = new TestLogger("test-logger", false, false);

    LoggingUtils.recordServiceRpcAndRequestHeaders(
        serviceName, rpcName, endpoint, requestHeaders, logDataBuilder, testLogger);

    LogData logData = logDataBuilder.build();
    assertEquals(null, logData.serviceName());
    assertEquals(null, logData.rpcName());
    assertEquals(null, logData.httpUrl());
    assertEquals(null, logData.requestHeaders());
  }

  @Test
  void testRecordResponseHeaders_debugEnabled() {
    Map<String, String> responseHeaders = new HashMap<>();
    responseHeaders.put("header1", "value1");
    responseHeaders.put("header2", "value2");

    LogData.Builder logDataBuilder = LogData.builder();
    TestLogger testLogger = new TestLogger("test-logger", true, true);

    LoggingUtils.recordResponseHeaders(responseHeaders, logDataBuilder, testLogger);

    LogData logData = logDataBuilder.build();
    assertEquals(responseHeaders, logData.responseHeaders());
  }

  @Test
  void testRecordResponseHeaders_debugDisabled() {
    Map<String, String> responseHeaders = new HashMap<>();
    responseHeaders.put("header1", "value1");
    responseHeaders.put("header2", "value2");

    LogData.Builder logDataBuilder = LogData.builder();
    TestLogger testLogger = new TestLogger("test-logger", true, false);

    LoggingUtils.recordResponseHeaders(responseHeaders, logDataBuilder, testLogger);

    LogData logData = logDataBuilder.build();
    assertEquals(null, logData.responseHeaders());
  }

  @Test
  void testRecordResponsePayload_debugEnabled() {

    Field field =
        Field.newBuilder()
            .setName("field_name1")
            .addOptions(Option.newBuilder().setName("opt_name1").build())
            .addOptions(Option.newBuilder().setName("opt_name2").build())
            .build();

    LogData.Builder logDataBuilder = LogData.builder();
    TestLogger testLogger = new TestLogger("test-logger", true, true);

    LoggingUtils.recordResponsePayload(field, logDataBuilder, testLogger);

    LogData logData = logDataBuilder.build();
    assertEquals(2, logData.responsePayload().size());
    assertEquals("field_name1", logData.responsePayload().get("name"));
    assertEquals(
        "[{name=opt_name1}, {name=opt_name2}]",
        logData.responsePayload().get("options").toString());
  }

  @Test
  void testLogRequest_infoEnabled_debugDisabled() {
    Object message = new Object(); // not used in info path
    LogData.Builder logDataBuilder = Mockito.mock(LogData.Builder.class);

    LogData.Builder testLogDataBuilder =
        LogData.builder().serviceName("service-name").rpcName("rpc-name");
    when(logDataBuilder.build()).thenReturn(testLogDataBuilder.build());

    Logger logger = new TestLogger("test", true, false);
    LoggingUtils.logRequest(message, logDataBuilder, logger);

    assertEquals(2, ((TestLogger) logger).keyValuePairsMap.size());
    assertEquals("Sending gRPC request", ((TestLogger) logger).messageList.get(0));
    verify(logDataBuilder, never()).requestPayload(anyMap()); // Ensure debug path is not taken

    assertEquals(Level.INFO, ((TestLogger) logger).level);
  }

  @Test
  void testLogRequest_debugEnabled() throws InvalidProtocolBufferException {
    Field field =
        Field.newBuilder()
            .setName("field_name1")
            .addOptions(Option.newBuilder().setName("opt_name1").build())
            .addOptions(Option.newBuilder().setName("opt_name2").build())
            .build();

    LogData.Builder logDataBuilder = Mockito.mock(LogData.Builder.class);
    LogData.Builder testLogDataBuilder =
        LogData.builder()
            .serviceName("service-name")
            .rpcName("rpc-name")
            .requestPayload(LoggingUtils.messageToMapWithGson(field));
    when(logDataBuilder.build()).thenReturn(testLogDataBuilder.build());

    TestLogger logger = new TestLogger("test-logger", true, true);

    LoggingUtils.logRequest(field, logDataBuilder, logger);

    verify(logDataBuilder).requestPayload(LoggingUtils.messageToMapWithGson(field));

    assertEquals(3, ((TestLogger) logger).keyValuePairsMap.size());
    assertEquals(2, ((Map) ((TestLogger) logger).keyValuePairsMap.get("request.payload")).size());
    assertEquals("Sending gRPC request", ((TestLogger) logger).messageList.get(0));

    assertEquals(Level.DEBUG, ((TestLogger) logger).level);
  }

  @Test
  void testLogResponse_infoEnabled_debugDisabled() {
    String status = "OK";
    Map<String, Object> responseData = new HashMap<>();

    LogData.Builder logDataBuilder = Mockito.mock(LogData.Builder.class);
    LogData.Builder testLogDataBuilder =
        LogData.builder()
            .serviceName("service-name")
            .rpcName("rpc-name")
            .responsePayload(responseData);
    when(logDataBuilder.build()).thenReturn(testLogDataBuilder.build());
    TestLogger logger = new TestLogger("test-logger", true, false);

    LoggingUtils.logResponse(status, logDataBuilder, logger);

    verify(logDataBuilder).responseStatus(status);
    assertEquals("Received Grpc response", ((TestLogger) logger).messageList.get(0));
    assertEquals(3, ((TestLogger) logger).keyValuePairsMap.size());
    assertTrue(((TestLogger) logger).keyValuePairsMap.containsKey("response.payload"));
    assertEquals(Level.INFO, ((TestLogger) logger).level);
    Map<String, Object> keyValuePairsMap = ((TestLogger) logger).keyValuePairsMap;
  }

  @Test
  void testLogResponse_infoEnabled_debugEnabled() {
    String status = "OK";
    Map<String, Object> responseData = new HashMap<>();

    LogData.Builder logDataBuilder = Mockito.mock(LogData.Builder.class);
    LogData.Builder testLogDataBuilder =
        LogData.builder()
            .serviceName("service-name")
            .rpcName("rpc-name")
            .responsePayload(responseData);
    when(logDataBuilder.build()).thenReturn(testLogDataBuilder.build());
    TestLogger logger = new TestLogger("test-logger", true, true);

    LoggingUtils.logResponse(status, logDataBuilder, logger);

    verify(logDataBuilder).responseStatus(status);
    assertEquals("Received Grpc response", ((TestLogger) logger).messageList.get(0));
    assertEquals(3, ((TestLogger) logger).keyValuePairsMap.size());
    assertTrue(((TestLogger) logger).keyValuePairsMap.containsKey("response.payload"));

    assertEquals(Level.DEBUG, ((TestLogger) logger).level);
  }

  @Test
  void testExecuteWithTryCatch_noException() {
    assertDoesNotThrow(
        () ->
            LoggingUtils.executeWithTryCatch(
                () -> {
                  // Some code that should not throw an exception
                  int x = 5;
                  int y = 10;
                  int z = x + y;
                  assertEquals(15, z);
                }));
  }

  @Test
  void testExecuteWithTryCatch_WithException() throws Throwable {
    ThrowingRunnable action = Mockito.mock(ThrowingRunnable.class);
    Mockito.doThrow(new RuntimeException("Test Exception")).when(action).run();
    assertDoesNotThrow(() -> LoggingUtils.executeWithTryCatch(action));
    // Verify that the action was executed (despite the exception)
    Mockito.verify(action).run();
  }

  @Test
  void testExecuteWithTryCatch_WithNoSuchMethodError() throws Throwable {
    ThrowingRunnable action = Mockito.mock(ThrowingRunnable.class);
    Mockito.doThrow(new NoSuchMethodError("Test Error")).when(action).run();
    assertDoesNotThrow(() -> LoggingUtils.executeWithTryCatch(action));
    // Verify that the action was executed (despite the error)
    Mockito.verify(action).run();
  }

  @Test
  void testCheckIfClazzAvailable() {
    assertFalse(LoggingUtils.checkIfClazzAvailable("fake.class.should.not.be.in.classpath"));
    assertTrue(LoggingUtils.checkIfClazzAvailable("org.slf4j.event.KeyValuePair"));
  }
}
