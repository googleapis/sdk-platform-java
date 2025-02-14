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

import com.google.api.core.InternalApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;

// Actual interaction with SLF4J happens only in this class.
@InternalApi
class Slf4jUtils {

  private static final Logger NO_OP_LOGGER = org.slf4j.helpers.NOPLogger.NOP_LOGGER;
  private static boolean loggingEnabled = LoggingUtils.isLoggingEnabled();
  private static final Gson gson = new Gson();

  private static boolean isSLF4J2x;

  static {
    isSLF4J2x = checkIfClazzAvailable("org.slf4j.event.KeyValuePair");
  }

  static boolean checkIfClazzAvailable(String clazzName) {
    try {
      Class.forName(clazzName);
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  private Slf4jUtils() {}

  static Logger getLogger(Class<?> clazz) {
    return getLogger(clazz, new DefaultLoggerFactoryProvider());
  }

  // constructor with LoggerFactoryProvider to make testing easier
  static Logger getLogger(Class<?> clazz, LoggerFactoryProvider factoryProvider) {
    if (loggingEnabled) {
      ILoggerFactory loggerFactory = factoryProvider.getLoggerFactory();
      return loggerFactory.getLogger(clazz.getName());
    } else {
      //  use SLF4j's NOP logger regardless of bindings
      return NO_OP_LOGGER;
    }
  }

  static void log(
      Logger logger, org.slf4j.event.Level level, Map<String, Object> contextMap, String message) {
    if (isSLF4J2x) {
      logWithKeyValuePair(logger, level, contextMap, message);
    } else {
      logWithMDC(logger, level, contextMap, message);
    }
  }

  // exposed for testing
  static void logWithMDC(
      Logger logger, org.slf4j.event.Level level, Map<String, Object> contextMap, String message) {
    if (!contextMap.isEmpty()) {
      for (Entry<String, Object> entry : contextMap.entrySet()) {
        String key = entry.getKey();
        Object value = entry.getValue();
        MDC.put(key, value instanceof String ? (String) value : gson.toJson(value));
      }
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
        logger.debug(message);
        // Default to DEBUG level
    }
    if (!contextMap.isEmpty()) {
      MDC.clear();
    }
  }

  private static void logWithKeyValuePair(
      Logger logger, org.slf4j.event.Level level, Map<String, Object> contextMap, String message) {
    LoggingEventBuilder loggingEventBuilder;
    switch (level) {
      case TRACE:
        loggingEventBuilder = logger.atTrace();
        break;
      case DEBUG:
        loggingEventBuilder = logger.atDebug();
        break;
      case INFO:
        loggingEventBuilder = logger.atInfo();
        break;
      case WARN:
        loggingEventBuilder = logger.atWarn();
        break;
      case ERROR:
        loggingEventBuilder = logger.atError();
        break;
      default:
        loggingEventBuilder = logger.atDebug();
        // Default to DEBUG level
    }
    contextMap.forEach(loggingEventBuilder::addKeyValue);
    loggingEventBuilder.log(message);
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

  // logging helper methods
  static Map<String, Object> messageToMapWithGson(Message message)
      throws InvalidProtocolBufferException {
    String json = JsonFormat.printer().print(message);
    return gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
  }

  static void recordServiceRpcAndRequestHeaders(
      String serviceName,
      String rpcName,
      String endpoint,
      Map<String, String> requestHeaders,
      LogData.Builder logDataBuilder,
      LoggerProvider loggerProvider) {
    LoggingUtils.executeWithTryCatch(
        () -> {
          Logger logger = loggerProvider.getLogger();
          if (logger.isInfoEnabled()) {
            addIfNotEmpty(logDataBuilder::serviceName, serviceName);
            addIfNotEmpty(logDataBuilder::rpcName, rpcName);
            addIfNotEmpty(logDataBuilder::httpUrl, endpoint);
          }
          if (logger.isDebugEnabled()) {
            logDataBuilder.requestHeaders(requestHeaders);
          }
        });
  }

  private static void addIfNotEmpty(Consumer<String> setter, String value) {
    if (value != null && !value.isEmpty()) {
      setter.accept(value);
    }
  }

  static void recordResponseHeaders(
      Map<String, String> headers, LogData.Builder logDataBuilder, LoggerProvider loggerProvider) {
    LoggingUtils.executeWithTryCatch(
        () -> {
          Logger logger = loggerProvider.getLogger();
          if (logger.isDebugEnabled()) {
            logDataBuilder.responseHeaders(headers);
          }
        });
  }

  static <RespT> void recordResponsePayload(
      RespT message, LogData.Builder logDataBuilder, LoggerProvider loggerProvider) {
    LoggingUtils.executeWithTryCatch(
        () -> {
          Logger logger = loggerProvider.getLogger();
          if (logger.isDebugEnabled()) {
            Map<String, Object> messageToMapWithGson =
                Slf4jUtils.messageToMapWithGson((Message) message);

            logDataBuilder.responsePayload(messageToMapWithGson);
          }
        });
  }

  static void logResponse(
      String status, LogData.Builder logDataBuilder, LoggerProvider loggerProvider) {
    LoggingUtils.executeWithTryCatch(
        () -> {
          Logger logger = loggerProvider.getLogger();
          if (logger.isInfoEnabled()) {
            logDataBuilder.responseStatus(status);
          }
          if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
            Map<String, Object> responseData = logDataBuilder.build().toMapResponse();
            Slf4jUtils.log(logger, Level.INFO, responseData, "Received Grpc response");
          }
          if (logger.isDebugEnabled()) {
            Map<String, Object> responsedDetailsMap = logDataBuilder.build().toMapResponse();
            Slf4jUtils.log(logger, Level.DEBUG, responsedDetailsMap, "Received Grpc response");
          }
        });
  }

  static <RespT> void logRequest(
      RespT message, LogData.Builder logDataBuilder, LoggerProvider loggerProvider) {
    LoggingUtils.executeWithTryCatch(
        () -> {
          Logger logger = loggerProvider.getLogger();
          if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
            Slf4jUtils.log(
                logger, Level.INFO, logDataBuilder.build().toMapRequest(), "Sending gRPC request");
          }
          if (logger.isDebugEnabled()) {
            Map<String, Object> messageToMapWithGson =
                Slf4jUtils.messageToMapWithGson((Message) message);

            logDataBuilder.requestPayload(messageToMapWithGson);
            Map<String, Object> requestDetailsMap = logDataBuilder.build().toMapRequest();
            Slf4jUtils.log(logger, Level.DEBUG, requestDetailsMap, "Sending gRPC request");
          }
        });
  }
}
