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

  private static boolean hasAddKeyValue;

  static {
    try {
      // try to find this class
      Class.forName("org.slf4j.event.KeyValuePair");
      // If no exception, it means SLF4j 2.x or later is present
      hasAddKeyValue = true;
    } catch (ClassNotFoundException e) {
      // If ClassNotFoundException, it's likely SLF4j 1.x
      hasAddKeyValue = false;
    }
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

  public static void log(
      Logger logger, org.slf4j.event.Level level, Map<String, Object> contextMap, String message) {
    if (hasAddKeyValue) {
      logWithKeyValuePair(logger, level, contextMap, message);
    } else {
      logWithMDC(logger, level, contextMap, message);
    }
  }

  private static void logWithMDC(
      Logger logger, org.slf4j.event.Level level, Map<String, Object> contextMap, String message) {
    if (!contextMap.isEmpty()) {
      for (Entry<String, Object> entry : contextMap.entrySet()) {
        String key = entry.getKey();
        Object value = entry.getValue();
        MDC.put(key, value.toString());
      }
      // contextMap.put("message", message);
      // message = gson.toJson(contextMap);
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

  // logging helper methods
  public static Map<String, Object> messageToMapWithGson(Message message)
      throws InvalidProtocolBufferException {
    String json = JsonFormat.printer().print(message);
    return gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
  }

  public static void recordServiceRpcAndRequestHeaders(
      String serviceName,
      String rpcName,
      String endpoint,
      Map<String, String> requestHeaders,
      LogData.Builder logDataBuilder,
      Logger logger) {
    executeWithTryCatch(
        () -> {
          if (logger.isInfoEnabled()) {
            addIfNotEmpty(logDataBuilder::serviceName, serviceName);
            addIfNotEmpty(logDataBuilder::rpcName, rpcName);
            addIfNotEmpty(logDataBuilder::httpUrl, endpoint);
          }
          if (logger.isInfoEnabled()) {
            logDataBuilder.requestHeaders(requestHeaders);
          }
        });
  }

  private static void addIfNotEmpty(Consumer<String> setter, String value) {
    if (value != null && !value.isEmpty()) {
      setter.accept(value);
    }
  }

  public static void recordResponseHeaders(
      Map<String, String> headers, LogData.Builder logDataBuilder, Logger logger) {
    executeWithTryCatch(
        () -> {
          if (logger.isDebugEnabled()) {
            logDataBuilder.responseHeaders(headers);
          }
        });
  }

  public static <RespT> void recordResponsePayload(
      RespT message, LogData.Builder logDataBuilder, Logger logger) {
    executeWithTryCatch(
        () -> {
          if (logger.isInfoEnabled()) {
            Map<String, Object> messageToMapWithGson =
                LoggingUtils.messageToMapWithGson((Message) message);

            logDataBuilder.responsePayload(messageToMapWithGson);
          }
        });
  }

  public static void logResponse(String status, LogData.Builder logDataBuilder, Logger logger) {
    executeWithTryCatch(
        () -> {
          if (logger.isInfoEnabled()) {
            logDataBuilder.responseStatus(status);
          }
          if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
            Map<String, Object> responseData = logDataBuilder.build().toMapResponse();
            LoggingUtils.log(logger, Level.INFO, responseData, "Received Grpc response");
          }
          if (logger.isInfoEnabled()) {
            Map<String, Object> responsedDetailsMap = logDataBuilder.build().toMapResponse();
            LoggingUtils.log(
                logger, Level.DEBUG, responsedDetailsMap, "Received Grpc response");
          }
        });
  }

  public static <RespT> void logRequest(
      RespT message, LogData.Builder logDataBuilder, Logger logger) {
    executeWithTryCatch(
        () -> {
          if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
            LoggingUtils.log(
                logger, Level.INFO, logDataBuilder.build().toMapRequest(), "Sending gRPC request");
          }
          if (logger.isDebugEnabled()) {
            Map<String, Object> messageToMapWithGson =
                LoggingUtils.messageToMapWithGson((Message) message);

            logDataBuilder.requestPayload(messageToMapWithGson);
            Map<String, Object> requestDetailsMap = logDataBuilder.build().toMapRequest();
            LoggingUtils.log(logger, Level.DEBUG, requestDetailsMap, "Sending gRPC request");
          }
        });
  }

  public static void executeWithTryCatch(ThrowingRunnable action) {
    try {
      action.run();
    } catch (Exception e) {
      // let logging exceptions fail silently
    }
  }

  @FunctionalInterface
  public interface ThrowingRunnable {
    void run() throws Exception;
  }
}
