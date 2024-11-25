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
import com.google.gson.JsonObject;
import java.util.logging.Level;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

@InternalApi
public class LoggingUtils {

  private static final java.util.logging.Logger LOGGER =
      java.util.logging.Logger.getLogger(LoggingUtils.class.getName());

  private LoggingUtils() {}

  public static Logger getLogger(Class<?> clazz) {
    if (!isLoggingEnabled()) {
      //  use SLF4j's NOP logger regardless of bindings
      return LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
    }

    ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
    if (loggerFactory != null && !(loggerFactory instanceof org.slf4j.helpers.NOPLoggerFactory)) {
      // Use SLF4j binding when present
      return LoggerFactory.getLogger(clazz);
    }
    // No SLF4j binding found, use JUL as fallback
    Logger logger = new JulWrapperLogger(clazz.getName());
    logger.info("No SLF4J providers were found, fall back to JUL.");
    return logger;
  }

  public static boolean isLoggingEnabled() {
    String enableLogging = System.getenv("GOOGLE_SDK_JAVA_LOGGING");
    LOGGER.info("GOOGLE_SDK_JAVA_LOGGING=" + enableLogging); // log for debug now, remove it.
    return "true".equalsIgnoreCase(enableLogging);
  }

  public static JsonObject mergeJsonObject(JsonObject jsonObject1, JsonObject jsonObject2) {
    JsonObject mergedObject = jsonObject1.deepCopy();
    jsonObject2.entrySet().forEach(entry -> mergedObject.add(entry.getKey(), entry.getValue()));
    return mergedObject;
  }

  // JulWrapperLogger implementation
  static class JulWrapperLogger implements Logger {

    private final java.util.logging.Logger julLogger;

    public JulWrapperLogger(String name) {
      this.julLogger = java.util.logging.Logger.getLogger(name);
    }

    @Override
    public String getName() {
      return julLogger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
      return julLogger.isLoggable(java.util.logging.Level.FINEST);
    }

    @Override
    public void trace(String msg) {
      julLogger.log(java.util.logging.Level.FINEST, msg);
    }

    @Override
    public void trace(String s, Object o) {}

    @Override
    public void trace(String s, Object o, Object o1) {}

    @Override
    public void trace(String s, Object... objects) {}

    @Override
    public void trace(String s, Throwable throwable) {}

    @Override
    public boolean isTraceEnabled(Marker marker) {
      return false;
    }

    @Override
    public void trace(Marker marker, String s) {}

    @Override
    public void trace(Marker marker, String s, Object o) {}

    @Override
    public void trace(Marker marker, String s, Object o, Object o1) {}

    @Override
    public void trace(Marker marker, String s, Object... objects) {}

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {}

    @Override
    public boolean isDebugEnabled() {
      return julLogger.isLoggable(Level.FINE);
    }

    @Override
    public void debug(String msg) {

      if (isDebugEnabled()) {
        julLogger.log(java.util.logging.Level.FINE, msg);
      }
    }

    @Override
    public void debug(String format, Object arg) {
      if (isDebugEnabled()) {
        FormattingTuple ft = MessageFormatter.format(format, arg);
        julLogger.log(Level.FINE, ft.getMessage());
      }
    }

    @Override
    public void debug(String s, Object o, Object o1) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void debug(String s, Object... objects) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void debug(String s, Throwable throwable) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void debug(Marker marker, String s) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void debug(Marker marker, String s, Object o) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o1) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public boolean isInfoEnabled() {
      return julLogger.isLoggable(Level.INFO);
    }

    @Override
    public void info(String msg) {
      if (isInfoEnabled()) {
        julLogger.log(java.util.logging.Level.INFO, msg);
      }
    }

    @Override
    public void info(String format, Object arg) {
      if (isInfoEnabled()) {
        FormattingTuple ft = MessageFormatter.format(format, arg);
        julLogger.log(java.util.logging.Level.INFO, ft.getMessage());
      }
    }

    @Override
    public void info(String s, Object o, Object o1) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void info(String s, Object... objects) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void info(String s, Throwable throwable) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
      return true;
    }

    @Override
    public void info(Marker marker, String s) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void info(Marker marker, String s, Object o) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void info(Marker marker, String s, Object o, Object o1) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void info(Marker marker, String s, Object... objects) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public boolean isWarnEnabled() {
      return true;
    }

    @Override
    public void warn(String msg) {
      julLogger.log(Level.WARNING, msg);
    }

    @Override
    public void warn(String s, Object o) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void warn(String s, Object... objects) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void warn(String s, Object o, Object o1) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void warn(String s, Throwable throwable) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
      return false;
    }

    @Override
    public void warn(Marker marker, String s) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void warn(Marker marker, String s, Object o) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o1) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public boolean isErrorEnabled() {
      return false;
    }

    @Override
    public void error(String s) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void error(String s, Object o) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void error(String s, Object o, Object o1) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void error(String s, Object... objects) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void error(String s, Throwable throwable) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
      return false;
    }

    @Override
    public void error(Marker marker, String s) {}

    @Override
    public void error(Marker marker, String s, Object o) {}

    @Override
    public void error(Marker marker, String s, Object o, Object o1) {}

    @Override
    public void error(Marker marker, String s, Object... objects) {}

    @Override
    public void error(Marker marker, String s, Throwable throwable) {}
  }
}
