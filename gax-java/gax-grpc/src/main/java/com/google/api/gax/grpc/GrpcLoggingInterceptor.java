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

public class GrpcLoggingInterceptor implements ClientInterceptor {

  private static final Logger logger = LoggingUtils.getLogger(GrpcLoggingInterceptor.class);
  private static final Gson gson = new Gson();

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
      MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {

    LogData.Builder logDataBuilder = LogData.builder();

    return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
        next.newCall(method, callOptions)) {

      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        logRequestInfo(method, logDataBuilder);
        recordRequestHeaders(logDataBuilder, headers);
        SimpleForwardingClientCallListener<RespT> responseLoggingListener =
            new SimpleForwardingClientCallListener<RespT>(responseListener) {
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
                logResponse(status, logDataBuilder);
                super.onClose(status, trailers);
              }
            };
        super.start(responseLoggingListener, headers);
      }
      @Override
      public void sendMessage(ReqT message) {
        logRequestDetails(message, logDataBuilder);
        super.sendMessage(message);
      }
    };
  }

  // --- Helper methods for logging ---
  private <ReqT, RespT> void logRequestInfo(
      MethodDescriptor<ReqT, RespT> method, LogData.Builder logDataBuilder) {
    if (logger.isInfoEnabled()) {
      String requestId = UUID.randomUUID().toString();
      logDataBuilder
          .serviceName(method.getServiceName())
          .rpcName(method.getFullMethodName())
          .requestId(requestId);

      if (!logger.isDebugEnabled()) {
        LoggingUtils.logWithMDC(
            logger,
            Level.INFO,
            logDataBuilder.build().serviceAndRpcToMap(),
            "Sending gRPC request");
      }
    }
  }
  private void recordRequestHeaders(LogData.Builder logDataBuilder, Metadata headers) {
    if (logger.isDebugEnabled()) {
      JsonObject requestHeaders = mapHeadersToJsonObject(headers);
      logDataBuilder.requestHeaders(gson.toJson(requestHeaders));
    }
  }
  private void recordResponseHeaders(Metadata headers, LogData.Builder logDataBuilder) {
    if (logger.isDebugEnabled()) {
      // Access and add response headers
      JsonObject responseHeaders = mapHeadersToJsonObject(headers);
      logDataBuilder.responseHeaders(gson.toJson(responseHeaders));
    }
  }

  private <RespT> void recordResponsePayload(RespT message, LogData.Builder logDataBuilder) {
    if (logger.isDebugEnabled()) {
      // Add each message to the array
      logDataBuilder.responsePayload(gson.toJsonTree(message));
    }
  }

  private void logResponse(Status status, LogData.Builder logDataBuilder) {
    if (logger.isInfoEnabled()) {
      logDataBuilder.responseStatus(status.getCode().name());
    }
    if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
      Map<String, String> responseData = logDataBuilder.build().responseInfoToMap();
      LoggingUtils.logWithMDC(logger, Level.INFO, responseData, "Received response.");
    }
    if (logger.isDebugEnabled()) {
      Map<String, String> responsedDetailsMap = logDataBuilder.build().responseDetailsToMap();
      LoggingUtils.logWithMDC(logger, Level.DEBUG, responsedDetailsMap, "Received response.");
    }
  }

  private <RespT> void logRequestDetails(RespT message, LogData.Builder logDataBuilder) {
    if (logger.isDebugEnabled()) {
      logDataBuilder.requestPayload(gson.toJson(message));
      Map<String, String> requestDetailsMap = logDataBuilder.build().requestDetailsToMap();
      LoggingUtils.logWithMDC(logger, Level.DEBUG, requestDetailsMap, "Sending gRPC request.");
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
