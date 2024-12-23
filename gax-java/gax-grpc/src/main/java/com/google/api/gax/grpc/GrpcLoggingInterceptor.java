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

package com.google.api.gax.grpc;

import com.google.api.core.InternalApi;
import com.google.api.gax.logging.LogData;
import com.google.api.gax.logging.LoggingUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.ForwardingClientCallListener.SimpleForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.event.Level;

@InternalApi
public class GrpcLoggingInterceptor implements ClientInterceptor {

  private static final Logger LOGGER = LoggingUtils.getLogger(GrpcLoggingInterceptor.class);
  private static final Gson GSON = new Gson();

  ClientCall.Listener<?> currentListener; // expose for test setup

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
      MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {

    return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
        next.newCall(method, callOptions)) {
      LogData.Builder logDataBuilder = LogData.builder();

      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        recordServiceRpcAndRequestHeaders(method, headers, logDataBuilder, LOGGER);
        SimpleForwardingClientCallListener<RespT> responseLoggingListener =
            new SimpleForwardingClientCallListener<RespT>(responseListener) {
              @Override
              public void onHeaders(Metadata headers) {
                recordResponseHeaders(headers, logDataBuilder, LOGGER);
                super.onHeaders(headers);
              }

              @Override
              public void onMessage(RespT message) {
                recordResponsePayload(message, logDataBuilder, LOGGER);
                super.onMessage(message);
              }

              @Override
              public void onClose(Status status, Metadata trailers) {
                logResponse(status, logDataBuilder, LOGGER);
                super.onClose(status, trailers);
              }
            };
        currentListener = responseLoggingListener;
        super.start(responseLoggingListener, headers);
      }

      @Override
      public void sendMessage(ReqT message) {
        logRequest(message, logDataBuilder, LOGGER);
        super.sendMessage(message);
      }
    };
  }

  // Helper methods for logging
  // some duplications with http equivalent to avoid exposing as public method for now

  private <ReqT, RespT> void recordServiceRpcAndRequestHeaders(
      MethodDescriptor<ReqT, RespT> method,
      Metadata headers,
      LogData.Builder logDataBuilder,
      Logger logger) {
    executeWithTryCatch(
        () -> {
          if (logger.isInfoEnabled()) {
            logDataBuilder.serviceName(method.getServiceName()).rpcName(method.getFullMethodName());
          }
          if (logger.isDebugEnabled()) {
            JsonObject requestHeaders = mapHeadersToJsonObject(headers);
            logDataBuilder.requestHeaders(GSON.toJson(requestHeaders));
          }
        });
  }

  void recordResponseHeaders(Metadata headers, LogData.Builder logDataBuilder, Logger logger) {
    executeWithTryCatch(
        () -> {
          if (logger.isDebugEnabled()) {
            JsonObject responseHeaders = mapHeadersToJsonObject(headers);
            logDataBuilder.responseHeaders(GSON.toJson(responseHeaders));
          }
        });
  }

  <RespT> void recordResponsePayload(RespT message, LogData.Builder logDataBuilder, Logger logger) {
    executeWithTryCatch(
        () -> {
          if (logger.isDebugEnabled()) {
            logDataBuilder.responsePayload(GSON.toJsonTree(message));
          }
        });
  }

  void logResponse(Status status, LogData.Builder logDataBuilder, Logger logger) {
    executeWithTryCatch(
        () -> {
          if (logger.isInfoEnabled()) {
            logDataBuilder.responseStatus(status.getCode().toString());
          }
          if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
            Map<String, String> responseData = logDataBuilder.build().toMapResponse();
            LoggingUtils.logWithMDC(logger, Level.INFO, responseData, "Received Grpc response");
          }
          if (logger.isDebugEnabled()) {
            Map<String, String> responsedDetailsMap = logDataBuilder.build().toMapResponse();
            LoggingUtils.logWithMDC(
                logger, Level.DEBUG, responsedDetailsMap, "Received Grpc response");
          }
        });
  }

  <RespT> void logRequest(RespT message, LogData.Builder logDataBuilder, Logger logger) {
    executeWithTryCatch(
        () -> {
          if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
            LoggingUtils.logWithMDC(
                logger, Level.INFO, logDataBuilder.build().toMapRequest(), "Sending gRPC request");
          }
          if (logger.isDebugEnabled()) {
            logDataBuilder.requestPayload(GSON.toJsonTree(message));
            Map<String, String> requestDetailsMap = logDataBuilder.build().toMapRequest();
            LoggingUtils.logWithMDC(
                logger, Level.DEBUG, requestDetailsMap, "Sending gRPC request: request payload");
          }
        });
  }

  static void executeWithTryCatch(Runnable action) {
    try {
      action.run();
    } catch (Exception | NoSuchMethodError e) {
      // should fail silently
    }
  }

  private static JsonObject mapHeadersToJsonObject(Metadata headers) {
    JsonObject jsonHeaders = new JsonObject();
    for (String key : headers.keys()) {

      if (key.endsWith(Metadata.BINARY_HEADER_SUFFIX)) {
        continue;
      }
      Metadata.Key<String> metadataKey = Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER);
      String headerValue = headers.get(metadataKey);

      jsonHeaders.addProperty(key, headerValue);
    }
    return jsonHeaders;
  }
}
