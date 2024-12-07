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

package com.google.api.gax.httpjson;

import com.google.api.gax.httpjson.ForwardingHttpJsonClientCall.SimpleForwardingHttpJsonClientCall;
import com.google.api.gax.httpjson.ForwardingHttpJsonClientCallListener.SimpleForwardingHttpJsonClientCallListener;
import com.google.api.gax.logging.LogData;
import com.google.api.gax.logging.LoggingUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.event.Level;

class HttpJsonLoggingInterceptor implements HttpJsonClientInterceptor {

  private static final Logger logger = LoggingUtils.getLogger(HttpJsonLoggingInterceptor.class);
  private static final Gson gson = new Gson();

  @Override
  public <ReqT, RespT> HttpJsonClientCall<ReqT, RespT> interceptCall(
      ApiMethodDescriptor<ReqT, RespT> method,
      HttpJsonCallOptions callOptions,
      HttpJsonChannel next) {

    String requestId = UUID.randomUUID().toString();
    String endpoint = ((ManagedHttpJsonChannel) next).getEndpoint();

    return new SimpleForwardingHttpJsonClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
      @Override
      public void start(
          HttpJsonClientCall.Listener<RespT> responseListener, HttpJsonMetadata headers) {

        logRequestInfoAndHeaders(method, headers, endpoint, requestId);

        Listener<RespT> forwardingResponseListener =
            new SimpleForwardingHttpJsonClientCallListener<RespT>(responseListener) {
              LogData.Builder logDataBuilder = LogData.builder();

              @Override
              public void onHeaders(HttpJsonMetadata responseHeaders) {
                recordResponseHeaders(responseHeaders, logDataBuilder);
                super.onHeaders(responseHeaders);
              }

              @Override
              public void onMessage(RespT message) {
                recordResponsePayload(message, logDataBuilder);
                super.onMessage(message);
              }

              @Override
              public void onClose(int statusCode, HttpJsonMetadata trailers) {
                try {
                  logResponse(statusCode, logDataBuilder, requestId);
                } finally {
                  logDataBuilder = null; // release resource
                }
                super.onClose(statusCode, trailers);
              }
            };
        super.start(forwardingResponseListener, headers);
      }

      @Override
      public void sendMessage(ReqT message) {
        logRequestDetails(message, requestId);
        super.sendMessage(message);
      }
    };
  }

  // Helper methods for logging,
  // some duplications with grpc equivalent to avoid exposing as public method
  private <ReqT, RespT> void logRequestInfoAndHeaders(
      ApiMethodDescriptor<ReqT, RespT> method,
      HttpJsonMetadata headers,
      String endpoint,
      String requestId) {
    try {
      if (logger.isInfoEnabled()) {
        LogData.Builder logDataBuilder = LogData.builder();
        logDataBuilder
            .rpcName(method.getFullMethodName())
            .httpMethod(method.getHttpMethod())
            .httpUrl(endpoint)
            .requestId(requestId);

        if (logger.isDebugEnabled()) {
          JsonObject requestHeaders = new JsonObject();
          headers
              .getHeaders()
              .forEach((key, value) -> requestHeaders.addProperty(key, value.toString()));
          logDataBuilder.requestHeaders(gson.toJson(requestHeaders));
          LoggingUtils.logWithMDC(
              logger, Level.DEBUG, logDataBuilder.build().toMap(), "Sending HTTP request");
        } else {
          LoggingUtils.logWithMDC(
              logger, Level.INFO, logDataBuilder.build().toMap(), "Sending HTTP request");
        }
      }
    } catch (Exception e) {
      logger.error("Error logging request info (and headers)", e);
    }
  }

  private void recordResponseHeaders(
      HttpJsonMetadata responseHeaders, LogData.Builder logDataBuilder) {

    if (logger.isDebugEnabled()) {

      Map<String, List<String>> map = new HashMap<>();
      responseHeaders.getHeaders().forEach((key, value) -> map.put(key, (List<String>) value));
      logDataBuilder.responseHeaders(gson.toJson(map));
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
        LoggingUtils.logWithMDC(logger, Level.INFO, responseData, "Received HTTP response");
      }
      if (logger.isDebugEnabled()) {
        Map<String, String> responsedDetailsMap = logDataBuilder.build().toMap();
        LoggingUtils.logWithMDC(logger, Level.DEBUG, responsedDetailsMap, "Received HTTP response");
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
            logger, Level.DEBUG, requestDetailsMap, "Sending HTTP request: request payload");
      }
    } catch (Exception e) {
      logger.error("Error logging request details", e);
    }
  }
}
