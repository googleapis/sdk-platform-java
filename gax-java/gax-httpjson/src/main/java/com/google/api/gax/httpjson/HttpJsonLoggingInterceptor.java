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
import com.google.api.gax.logging.LoggingUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.event.Level;

public class HttpJsonLoggingInterceptor implements HttpJsonClientInterceptor {

  private static final Logger logger = LoggingUtils.getLogger(HttpJsonLoggingInterceptor.class);
  private static final Gson gson = new Gson();

  @Override
  public <ReqT, RespT> HttpJsonClientCall<ReqT, RespT> interceptCall(
      ApiMethodDescriptor<ReqT, RespT> method,
      HttpJsonCallOptions callOptions,
      HttpJsonChannel next) {
    Map<String, String> requestLogData = new HashMap<>();

    // Initialize a JsonArray to hold all responses
    JsonArray responsePayloads = new JsonArray();
    String endpoint = ((ManagedHttpJsonChannel) next).getEndpoint();
    return new SimpleForwardingHttpJsonClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
      @Override
      public void start(
          HttpJsonClientCall.Listener<RespT> responseListener, HttpJsonMetadata headers) {

        Map<String, String> serviceAndRpc = new HashMap<>();
        if (logger.isInfoEnabled()) {
          String requestId = UUID.randomUUID().toString();
          // Capture request details
          String methodName = method.getFullMethodName();
          String httpMethod = method.getHttpMethod();
          serviceAndRpc.put("rpcName", methodName);
          serviceAndRpc.put("requestId", requestId);
          requestLogData.putAll(serviceAndRpc);
          requestLogData.put("request.url", endpoint);
          requestLogData.put("request.method", httpMethod);
        }
        if (logger.isDebugEnabled()) {
          // Capture and log headers
          JsonObject jsonHeaders = new JsonObject();
          headers
              .getHeaders()
              .forEach((key, value) -> jsonHeaders.addProperty(key, value.toString()));
          requestLogData.put("request.headers", gson.toJson(jsonHeaders));
        }

        if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
          LoggingUtils.logWithMDC(logger, Level.INFO, serviceAndRpc, "Sending HTTP request");
        }
        Map<String, String> responseLogData = new HashMap<>();
        Listener<RespT> forwardingResponseListener =
            new SimpleForwardingHttpJsonClientCallListener<RespT>(responseListener) {
              @Override
              public void onHeaders(HttpJsonMetadata responseHeaders) {

                if (logger.isDebugEnabled()) {

                  Map<String, List<String>> map = new HashMap<>();
                  responseHeaders
                      .getHeaders()
                      .forEach((key, value) -> map.put(key, (List<String>) value));
                  responseLogData.put("response.headers", gson.toJson(map));
                }
                super.onHeaders(responseHeaders);
              }

              @Override
              public void onMessage(RespT message) {

                if (logger.isDebugEnabled()) {
                  // Add each message to the array
                  responsePayloads.add(gson.toJsonTree(message));
                }
                super.onMessage(message);
              }

              @Override
              public void onClose(int statusCode, HttpJsonMetadata trailers) {

                if (logger.isInfoEnabled()) {

                  serviceAndRpc.put("response.status", String.valueOf(statusCode));
                  responseLogData.putAll(serviceAndRpc);
                }
                if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
                  LoggingUtils.logWithMDC(
                      logger, Level.INFO, serviceAndRpc, "HTTP request finished.");
                }
                if (logger.isDebugEnabled()) {
                  // Add the array of payloads to the responseLogData
                  responseLogData.put("response.payload", gson.toJson(responsePayloads));
                  LoggingUtils.logWithMDC(
                      logger,
                      Level.DEBUG,
                      responseLogData,
                      "Received response header and payload.");
                }
                super.onClose(statusCode, trailers);
              }
            };

        super.start(forwardingResponseListener, headers);
      }

      @Override
      public void sendMessage(ReqT message) {
        if (logger.isDebugEnabled()) {
          requestLogData.put("request.payload", gson.toJson(message));
          LoggingUtils.logWithMDC(
              logger, Level.DEBUG, requestLogData, "HTTP request header and payload.");
        }
        super.sendMessage(message);
      }
    };
  }
}
