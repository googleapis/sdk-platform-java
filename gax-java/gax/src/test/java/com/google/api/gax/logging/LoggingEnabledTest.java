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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.api.gax.logging.LoggingUtils.LoggerFactoryProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.helpers.NOPLogger;
import org.slf4j.helpers.NOPLoggerFactory;

// This test class should only run when env GOOGLE_SDK_JAVA_LOGGING = true
// it is excluded by default
class LoggingEnabledTest {

  @Test
  void testIsLoggingEnabled_true() {
    Assertions.assertTrue(LoggingHelpers.isLoggingEnabled());
  }

  // GOOGLE_SDK_JAVA_LOGGING = true
  @Test
  void testGetLogger_loggingEnabled_slf4jBindingPresent() {
    // should get ILoggerFactory from TestServiceProvider
    Logger logger = LoggingUtils.getLogger(LoggingUtilsTest.class);
    Assertions.assertInstanceOf(Logger.class, logger);
    Assertions.assertNotEquals(NOPLogger.class, logger.getClass());
  }

  // GOOGLE_SDK_JAVA_LOGGING = true
  @Test
  void testGetLogger_loggingEnabled_noBinding_shouldGetNOPLogger() {
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
}
