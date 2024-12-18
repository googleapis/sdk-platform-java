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

import com.google.api.core.InternalApi;
import com.google.api.gax.rpc.internal.EnvironmentProvider;
import com.google.api.gax.rpc.internal.SystemEnvironmentProvider;
import com.google.gson.Gson;
import java.util.Map;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@InternalApi
public class LoggingUtils {

  private static EnvironmentProvider environmentProvider = SystemEnvironmentProvider.getInstance();
  private static final Logger NO_OP_LOGGER = org.slf4j.helpers.NOPLogger.NOP_LOGGER;
  private static boolean loggingEnabled = isLoggingEnabled();
  static final String GOOGLE_SDK_JAVA_LOGGING = "GOOGLE_SDK_JAVA_LOGGING";
  private static final Gson gson = new Gson();
  // expose this setter for testing purposes
  static void setEnvironmentProvider(EnvironmentProvider provider) {
    environmentProvider = provider;
    // Recalculate LOGGING_ENABLED after setting the new provider
    loggingEnabled = isLoggingEnabled();
  }

  private LoggingUtils() {}

  public static Logger getLogger(Class<?> clazz) {
    return getLogger(clazz, new DefaultLoggerFactoryProvider());
  }

  // constructor with LoggerFactoryProvider to make testing easier
  static Logger getLogger(Class<?> clazz, LoggerFactoryProvider factoryProvider) {
    if (loggingEnabled) {
      return factoryProvider.getLoggerFactory().getLogger(clazz.getName());
    } else {
      //  use SLF4j's NOP logger regardless of bindings
      return NO_OP_LOGGER;
    }
  }

  public static void logWithMDC(
      Logger logger, org.slf4j.event.Level level, Map<String, String> contextMap, String message) {
    if (!contextMap.isEmpty()) {
      contextMap.forEach(MDC::put);
      contextMap.put("message", message);
      message = gson.toJson(contextMap);
    }
    switch (level) {
      case TRACE:
        logger.trace(message);
        break;
      case DEBUG:
        logger.debug(message);
        break;
      case INFO:
        logger.info(message);
        break;
      case WARN:
        logger.warn(message);
        break;
      case ERROR:
        logger.error(message);
        break;
      default:
        logger.info(message);
        // Default to INFO level
    }
    if (!contextMap.isEmpty()) {
      MDC.clear();
    }
  }

  static boolean isLoggingEnabled() {
    String enableLogging = environmentProvider.getenv(GOOGLE_SDK_JAVA_LOGGING);
    return "true".equalsIgnoreCase(enableLogging);
  }

  interface LoggerFactoryProvider {
    ILoggerFactory getLoggerFactory();
  }

  static class DefaultLoggerFactoryProvider implements LoggerFactoryProvider {
    @Override
    public ILoggerFactory getLoggerFactory() {
      return LoggerFactory.getILoggerFactory();
    }
  }
}
