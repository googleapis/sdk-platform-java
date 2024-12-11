/*
 * Copyright 2024 Google LLC
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import com.google.api.gax.logging.LoggingUtils.LoggerFactoryProvider;
import com.google.api.gax.rpc.internal.EnvironmentProvider;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLogger;

class LoggingUtilsTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingUtilsTest.class);
  private EnvironmentProvider envProvider = Mockito.mock(EnvironmentProvider.class);

  @BeforeEach
  void setup() {
    EnvironmentProvider envProvider = Mockito.mock(EnvironmentProvider.class);

    // need to setup a ConsoleAppender and attach to root logger because TestAppender
    // does not correctly capture MDC info
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
    patternLayoutEncoder.setPattern("%-4relative [%thread] %-5level %logger{35} - %msg%n");
    patternLayoutEncoder.setContext(loggerContext);

    patternLayoutEncoder.start();

    ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
    consoleAppender.setEncoder(patternLayoutEncoder);

    consoleAppender.setContext(loggerContext);
    consoleAppender.setName("CONSOLE");

    consoleAppender.start();

    ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
    rootLogger.addAppender(consoleAppender);
  }

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
    assertTrue(logger instanceof org.slf4j.Logger);
    assertNotEquals(logger.getClass(), NOPLogger.class);
  }

  @Test
  void testGetLogger_loggingDisabled() {
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("false");
    LoggingUtils.setEnvironmentProvider(envProvider);

    Logger logger = LoggingUtils.getLogger(LoggingUtilsTest.class);
    assertEquals(NOPLogger.class, logger.getClass());
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
    assertTrue(logger instanceof org.slf4j.helpers.NOPLogger);
  }

  @Test
  void testIsLoggingEnabled_true() {
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("true");
    LoggingUtils.setEnvironmentProvider(envProvider);
    assertTrue(LoggingUtils.isLoggingEnabled());
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("TRUE");
    LoggingUtils.setEnvironmentProvider(envProvider);
    assertTrue(LoggingUtils.isLoggingEnabled());
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("True");
    LoggingUtils.setEnvironmentProvider(envProvider);
    assertTrue(LoggingUtils.isLoggingEnabled());
  }

  @Test
  void testIsLoggingEnabled_defaultToFalse() {
    LoggingUtils.setEnvironmentProvider(envProvider);
    assertFalse(LoggingUtils.isLoggingEnabled());
  }

  private TestAppender setupTestLogger(Class<?> clazz) {
    TestAppender testAppender = new TestAppender();
    testAppender.start();
    Logger logger = LoggerFactory.getLogger(clazz);
    ((ch.qos.logback.classic.Logger) logger).addAppender(testAppender);
    return testAppender;
  }

  @Test
  public void testLogWithMDC_slf4jLogger() {
    TestAppender testAppender = setupTestLogger(LoggingUtilsTest.class);
    Map<String, String> contextMap = new HashMap<>();
    contextMap.put("key", "value");
    LoggingUtils.logWithMDC(LOGGER, org.slf4j.event.Level.DEBUG, contextMap, "test message");

    assertEquals(1, testAppender.events.size());
    assertEquals(
        "{\"message\":\"test message\",\"key\":\"value\"}",
        testAppender.events.get(0).getFormattedMessage());

    // Verify MDC content
    ILoggingEvent event = testAppender.events.get(0);
    assertEquals("value", event.getMDCPropertyMap().get("key"));
    testAppender.stop();
  }
}
