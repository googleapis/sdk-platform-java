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

import com.google.api.core.InternalApi;
import com.google.api.gax.httpjson.ForwardingHttpJsonClientCall.SimpleForwardingHttpJsonClientCall;
import com.google.api.gax.httpjson.ForwardingHttpJsonClientCallListener.SimpleForwardingHttpJsonClientCallListener;
import com.google.api.gax.logging.LogData;
import com.google.api.gax.logging.LoggingUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.event.Level;

@InternalApi
public class HttpJsonLoggingInterceptor implements HttpJsonClientInterceptor {

  private static final Logger LOGGER = LoggingUtils.getLogger(HttpJsonLoggingInterceptor.class);
  private static final Gson GSON = new Gson();

  @Override
  public <ReqT, RespT> HttpJsonClientCall<ReqT, RespT> interceptCall(
      ApiMethodDescriptor<ReqT, RespT> method,
      HttpJsonCallOptions callOptions,
      HttpJsonChannel next) {

    String endpoint = ((ManagedHttpJsonChannel) next).getEndpoint();

    return new SimpleForwardingHttpJsonClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

      LogData.Builder logDataBuilder = LogData.builder();

      @Override
      public void start(
          HttpJsonClientCall.Listener<RespT> responseListener, HttpJsonMetadata headers) {

        logRequestInfo(method, endpoint, logDataBuilder, LOGGER);
        recordRequestHeaders(headers, logDataBuilder);

        Listener<RespT> forwardingResponseListener =
            new SimpleForwardingHttpJsonClientCallListener<RespT>(responseListener) {

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
                logResponse(statusCode, logDataBuilder, LOGGER);
                super.onClose(statusCode, trailers);
              }
            };
        super.start(forwardingResponseListener, headers);
      }

      @Override
      public void sendMessage(ReqT message) {
        logRequestDetails(message, logDataBuilder);
        super.sendMessage(message);
      }
    };
  }

  // Helper methods for logging,
  // some duplications with grpc equivalent to avoid exposing as public method
  <ReqT, RespT> void logRequestInfo(
      ApiMethodDescriptor<ReqT, RespT> method,
      String endpoint,
      LogData.Builder logDataBuilder,
      Logger logger) {
    try {
      if (logger.isInfoEnabled()) {
        logDataBuilder
            .rpcName(method.getFullMethodName())
            .httpMethod(method.getHttpMethod())
            .httpUrl(endpoint);

        if (!logger.isDebugEnabled()) {
          LoggingUtils.logWithMDC(
              logger, Level.INFO, logDataBuilder.build().toMapRequest(), "Sending HTTP request");
        }
      }
    } catch (Exception e) {
      logger.error("Error logging request info (and headers)", e);
    }
  }

  private void recordRequestHeaders(HttpJsonMetadata headers, LogData.Builder logDataBuilder) {
    try {
      if (LOGGER.isDebugEnabled()) {
        JsonObject requestHeaders = new JsonObject();
        headers
            .getHeaders()
            .forEach((key, value) -> requestHeaders.addProperty(key, value.toString()));
        logDataBuilder.requestHeaders(GSON.toJson(requestHeaders));
      }
    } catch (Exception e) {
      LOGGER.error("Error recording request headers", e);
    }
  }

  private void recordResponseHeaders(
      HttpJsonMetadata responseHeaders, LogData.Builder logDataBuilder) {
    try {
      if (LOGGER.isDebugEnabled()) {

        Map<String, List<String>> map = new HashMap<>();
        responseHeaders.getHeaders().forEach((key, value) -> map.put(key, (List<String>) value));
        logDataBuilder.responseHeaders(GSON.toJson(map));
      }
    } catch (Exception e) {
      LOGGER.error("Error recording response headers", e);
    }
  }

  private <RespT> void recordResponsePayload(RespT message, LogData.Builder logDataBuilder) {
    try {
      if (LOGGER.isDebugEnabled()) {
        logDataBuilder.responsePayload(GSON.toJsonTree(message));
      }
    } catch (Exception e) {
      LOGGER.error("Error recording response payload", e);
    }
  }

  void logResponse(int statusCode, LogData.Builder logDataBuilder, Logger logger) {
    try {
      if (logger.isInfoEnabled()) {
        logDataBuilder.responseStatus(String.valueOf(statusCode));
      }
      if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
        Map<String, String> responseData = logDataBuilder.build().toMapResponse();
        LoggingUtils.logWithMDC(logger, Level.INFO, responseData, "Received HTTP response");
      }
      if (logger.isDebugEnabled()) {
        Map<String, String> responsedDetailsMap = logDataBuilder.build().toMapResponse();
        LoggingUtils.logWithMDC(logger, Level.DEBUG, responsedDetailsMap, "Received HTTP response");
      }
    } catch (Exception e) {
      logger.error("Error logging request response", e);
    }
  }

  private <RespT> void logRequestDetails(RespT message, LogData.Builder logDataBuilder) {
    try {
      if (LOGGER.isDebugEnabled()) {
        logDataBuilder.requestPayload(GSON.toJsonTree(message));
        Map<String, String> requestDetailsMap = logDataBuilder.build().toMapRequest();
        LoggingUtils.logWithMDC(
            LOGGER, Level.DEBUG, requestDetailsMap, "Sending HTTP request: request payload");
      }
    } catch (Exception e) {
      LOGGER.error("Error logging request details", e);
    }
  }
}