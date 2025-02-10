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

import java.util.Map;

public class LoggingHelpers {

  private static boolean loggingEnabled = isLoggingEnabled();
  static final String GOOGLE_SDK_JAVA_LOGGING = "GOOGLE_SDK_JAVA_LOGGING";

  static boolean isLoggingEnabled() {
    String enableLogging = System.getenv(GOOGLE_SDK_JAVA_LOGGING);
    return "true".equalsIgnoreCase(enableLogging);
  }

  public static void recordServiceRpcAndRequestHeaders(
      String serviceName,
      String rpcName,
      String endpoint,
      Map<String, String> requestHeaders,
      LogData.Builder logDataBuilder,
      LoggerProvider loggerProvider) {
    if (loggingEnabled) {
      LoggingUtils.recordServiceRpcAndRequestHeaders(
          serviceName, rpcName, endpoint, requestHeaders, logDataBuilder, loggerProvider);
    }
  }

  public static void recordResponseHeaders(
      Map<String, String> headers, LogData.Builder logDataBuilder, LoggerProvider loggerProvider) {
    if (loggingEnabled) {
      LoggingUtils.recordResponseHeaders(headers, logDataBuilder, loggerProvider);
    }
  }

  public static <RespT> void recordResponsePayload(
      RespT message, LogData.Builder logDataBuilder, LoggerProvider loggerProvider) {
    if (loggingEnabled) {
      LoggingUtils.recordResponsePayload(message, logDataBuilder, loggerProvider);
    }
  }

  public static void logResponse(
      String status, LogData.Builder logDataBuilder, LoggerProvider loggerProvider) {
    if (loggingEnabled) {
      LoggingUtils.logResponse(status, logDataBuilder, loggerProvider);
    }
  }

  public static <RespT> void logRequest(
      RespT message, LogData.Builder logDataBuilder, LoggerProvider loggerProvider) {
    if (loggingEnabled) {
      LoggingUtils.logRequest(message, logDataBuilder, loggerProvider);
    }
  }

  public static void executeWithTryCatch(ThrowingRunnable action) {
    try {
      action.run();
    } catch (Throwable t) {
      // let logging exceptions fail silently
    }
  }

  @FunctionalInterface
  public interface ThrowingRunnable {
    void run() throws Throwable;
  }
}
