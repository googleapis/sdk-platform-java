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
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.event.Level;

class GrpcLoggingInterceptor implements ClientInterceptor {

  private static final Logger logger = LoggingUtils.getLogger(GrpcLoggingInterceptor.class);
  private static final Gson gson = new Gson();

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
      MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
    return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
        next.newCall(method, callOptions)) {

      String requestId = UUID.randomUUID().toString();

      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        logRequestInfoAndHeaders(method, headers, requestId);
        SimpleForwardingClientCallListener<RespT> responseLoggingListener =
            new SimpleForwardingClientCallListener<RespT>(responseListener) {
              LogData.Builder logDataBuilder = LogData.builder();

              @Override
              public void onHeaders(Metadata headers) {
                recordResponseHeaders(headers, logDataBuilder);
                super.onHeaders(headers);
              }

              @Override
              public void onMessage(RespT message) {
                recordResponsePayload(message, logDataBuilder);
                super.onMessage(message);
              }

              @Override
              public void onClose(Status status, Metadata trailers) {
                try {
                  logResponse(status.getCode().value(), logDataBuilder, requestId);
                } finally {
                  logDataBuilder = null; // release resource
                }
                super.onClose(status, trailers);
              }
            };
        super.start(responseLoggingListener, headers);
      }

      @Override
      public void sendMessage(ReqT message) {
        logRequestDetails(message, requestId);
        super.sendMessage(message);
      }
    };
  }

  // Helper methods for logging
  // some duplications with http equivalent to avoid exposing as public method
  private <ReqT, RespT> void logRequestInfoAndHeaders(
      MethodDescriptor<ReqT, RespT> method, Metadata headers, String requestId) {
    try {
      if (logger.isInfoEnabled()) {
        LogData.Builder logDataBuilder = LogData.builder();
        logDataBuilder
            .serviceName(method.getServiceName())
            .rpcName(method.getFullMethodName())
            .requestId(requestId);

        if (logger.isDebugEnabled()) {
          JsonObject requestHeaders = mapHeadersToJsonObject(headers);
          logDataBuilder.requestHeaders(gson.toJson(requestHeaders));
          LoggingUtils.logWithMDC(
              logger, Level.DEBUG, logDataBuilder.build().toMap(), "Sending gRPC request");
        } else {
          LoggingUtils.logWithMDC(
              logger, Level.INFO, logDataBuilder.build().toMap(), "Sending gRPC request");
        }
      }
    } catch (Exception e) {
      logger.error("Error logging request info (and headers)", e);
    }
  }

  private void recordResponseHeaders(Metadata headers, LogData.Builder logDataBuilder) {
    if (logger.isDebugEnabled()) {
      JsonObject responseHeaders = mapHeadersToJsonObject(headers);
      logDataBuilder.responseHeaders(gson.toJson(responseHeaders));
    }
  }

  private <RespT> void recordResponsePayload(RespT message, LogData.Builder logDataBuilder) {
    if (logger.isDebugEnabled()) {
      logDataBuilder.responsePayload(gson.toJsonTree(message));
    }
  }

  private void logResponse(int statusCode, LogData.Builder logDataBuilder, String requestId) {
    try {

      if (logger.isInfoEnabled()) {
        logDataBuilder.responseStatus(String.valueOf(statusCode)).requestId(requestId);
      }
      if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
        Map<String, String> responseData = logDataBuilder.build().toMap();
        LoggingUtils.logWithMDC(logger, Level.INFO, responseData, "Received Grpc response");
      }
      if (logger.isDebugEnabled()) {
        Map<String, String> responsedDetailsMap = logDataBuilder.build().toMap();
        LoggingUtils.logWithMDC(logger, Level.DEBUG, responsedDetailsMap, "Received Grpc response");
      }
    } catch (Exception e) {
      logger.error("Error logging request response", e);
    }
  }

  private <RespT> void logRequestDetails(RespT message, String requestId) {
    try {
      if (logger.isDebugEnabled()) {
        LogData.Builder logDataBuilder = LogData.builder();
        logDataBuilder.requestPayload(gson.toJson(message)).requestId(requestId);
        Map<String, String> requestDetailsMap = logDataBuilder.build().toMap();
        LoggingUtils.logWithMDC(
            logger, Level.DEBUG, requestDetailsMap, "Sending gRPC request: request payload");
      }
    } catch (Exception e) {
      logger.error("Error logging request details", e);
    }
  }

  private static JsonObject mapHeadersToJsonObject(Metadata headers) {
    JsonObject jsonHeaders = new JsonObject();
    headers
        .keys()
        .forEach(
            key -> {
              Metadata.Key<String> metadataKey =
                  Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER);
              String headerValue = headers.get(metadataKey);
              jsonHeaders.addProperty(key, headerValue);
            });
    return jsonHeaders;
  }
}
