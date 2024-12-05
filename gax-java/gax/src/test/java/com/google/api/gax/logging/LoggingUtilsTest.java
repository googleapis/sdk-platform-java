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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLogger;

public class LoggingUtilsTest {

  private EnvironmentProvider envProvider = Mockito.mock(EnvironmentProvider.class);

  @BeforeEach
  public void setup() {
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
  public void tearDown() {
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).detachAppender("CONSOLE");
  }

  @org.junit.Test
  public void testGetLogger_loggingEnabled_slf4jBindingPresent() {
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("true");
    LoggingUtils.setEnvironmentProvider(envProvider);
    Logger logger = LoggingUtils.getLogger(LoggingUtilsTest.class);
    assertTrue(logger instanceof org.slf4j.Logger);
    assertNotEquals(logger.getClass(), NOPLogger.class);
  }

  @org.junit.Test
  public void testGetLogger_loggingDisabled() {
    Mockito.when(envProvider.getenv(LoggingUtils.GOOGLE_SDK_JAVA_LOGGING)).thenReturn("false");
    LoggingUtils.setEnvironmentProvider(envProvider);

    Logger logger = LoggingUtils.getLogger(LoggingUtilsTest.class);
    assertEquals(NOPLogger.class, logger.getClass());
  }

  @org.junit.Test
  public void testGetLogger_loggingEnabled_noBinding() {
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

  @org.junit.Test
  public void testIsLoggingEnabled_true() {
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

  @org.junit.Test
  public void testIsLoggingEnabled_defaultToFalse() {
    LoggingUtils.setEnvironmentProvider(envProvider);
    assertFalse(LoggingUtils.isLoggingEnabled());
  }

  // @Test
  // public void testLogWithMDC_slf4jLogger() {
  //   TestAppender.clearEvents();
  //   Map<String, String> contextMap = new HashMap<>();
  //   contextMap.put("key", "value");
  //   LoggingUtils.logWithMDC(LOGGER, org.slf4j.event.Level.DEBUG, contextMap, "test message");
  //
  //   assertEquals(1, TestAppender.events.size());
  //   assertEquals("test message", TestAppender.events.get(0).getFormattedMessage());
  //
  //   // Verify MDC content
  //   ILoggingEvent event = TestAppender.events.get(0);
  //   assertEquals("value", event.getMDCPropertyMap().get("key"));
  // }
}
