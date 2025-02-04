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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.LoggerContext;
import com.google.api.gax.logging.LoggingUtils.LoggerFactoryProvider;
import com.google.api.gax.rpc.internal.EnvironmentProvider;
import com.google.protobuf.Field;
import com.google.protobuf.Field.Cardinality;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Option;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.event.Level;
import org.slf4j.helpers.NOPLogger;

class LoggingUtilsTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingUtilsTest.class);
  private EnvironmentProvider envProvider = Mockito.mock(EnvironmentProvider.class);

  @AfterEach
  void tearDown() {
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).detachAppender("CONSOLE");
  }

  @Test
  void testGetLogger_loggingEnabled_slf4jBindingPresent() {
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("true");
    LoggingUtils.setEnvironmentProvider(envProvider);
    Logger logger = LoggingUtils.getLogger(LoggingUtilsTest.class);
    Assertions.assertInstanceOf(Logger.class, logger);
    Assertions.assertNotEquals(NOPLogger.class, logger.getClass());
  }

  @Test
  void testGetLogger_loggingDisabled() {
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("false");
    LoggingUtils.setEnvironmentProvider(envProvider);

    Logger logger = LoggingUtils.getLogger(LoggingUtilsTest.class);
    assertEquals(NOPLogger.class, logger.getClass());
    Assertions.assertFalse(logger.isInfoEnabled());
    Assertions.assertFalse(logger.isDebugEnabled());
  }

  @Test
  void testGetLogger_loggingEnabled_noBinding() {
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("true");
    LoggingUtils.setEnvironmentProvider(envProvider);
    // Create a mock LoggerFactoryProvider
    LoggerFactoryProvider mockLoggerFactoryProvider = mock(LoggerFactoryProvider.class);
    ILoggerFactory mockLoggerFactory = mock(ILoggerFactory.class);
    when(mockLoggerFactoryProvider.getLoggerFactory()).thenReturn(mockLoggerFactory);
    when(mockLoggerFactory.getLogger(anyString()))
        .thenReturn(org.slf4j.helpers.NOPLogger.NOP_LOGGER);

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
    Assertions.assertFalse(LoggingUtils.isLoggingEnabled());
  }

  // run with slf4j2_logback profile
  @Test
  void testLog_slf4JLogger() {
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

  // run with slf4j1_logback profile
  @Test
  void testLogWithMDC_InfoLevel_VerifyMDC() {
    TestLogger testLogger = new TestLogger("test-logger");
    Map<String, Object> contextMap = new HashMap<>();
    contextMap.put("key1", "value1");
    contextMap.put("key2", 123);
    String message = "Test message";

    LoggingUtils.logWithMDC(testLogger, Level.INFO, contextMap, message);

    Map<String, String> mdcMap = testLogger.MDCMap;

    assertEquals(2, mdcMap.size());
    assertEquals("value1", mdcMap.get("key1"));
    assertEquals("123", mdcMap.get("key2"));

    assertEquals(message, testLogger.messageList.get(0));
    // verify MDC is cleared
    assertNull(MDC.getCopyOfContextMap());
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
}
