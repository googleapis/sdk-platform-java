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

import com.google.api.gax.logging.LoggingUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
import java.util.HashMap;
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

    Map<String, String> serviceAndRpc = new HashMap<>();
    Map<String, String> requestLogData = new HashMap<>();
    Map<String, String> responseLogData = new HashMap<>();

    // Initialize a JsonArray to hold all responses
    JsonArray responsePayloads = new JsonArray();

    return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
        next.newCall(method, callOptions)) {

      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        if (logger.isInfoEnabled()) {
          String requestId = UUID.randomUUID().toString();
          String serviceName = method.getServiceName();
          String methodName = method.getFullMethodName();

          serviceAndRpc.put("serviceName", serviceName);
          serviceAndRpc.put("rpcName", methodName);
          serviceAndRpc.put("requestId", requestId);
        }
        if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
          LoggingUtils.logWithMDC(logger, Level.INFO, serviceAndRpc, "Sending gRPC request");
        }
        if (logger.isDebugEnabled()) {
          requestLogData.putAll(serviceAndRpc);

          JsonObject requestHeaders = mapHeadersToJsonObject(headers);
          requestLogData.put("request.headers", gson.toJson(requestHeaders));
        }

        SimpleForwardingClientCallListener<RespT> loggingListener =
            new SimpleForwardingClientCallListener<RespT>(responseListener) {
              @Override
              public void onHeaders(Metadata headers) {

                if (logger.isDebugEnabled()) {
                  // Access and add response headers
                  JsonObject responseHeaders = mapHeadersToJsonObject(headers);
                  responseLogData.put("response.headers", gson.toJson(responseHeaders));
                }
                super.onHeaders(headers);
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
              public void onClose(Status status, Metadata trailers) {
                if (logger.isInfoEnabled()) {
                  serviceAndRpc.put("response.status", status.getCode().name());
                  responseLogData.putAll(serviceAndRpc);
                }
                if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
                  LoggingUtils.logWithMDC(logger, Level.INFO, serviceAndRpc, "Received response.");
                }
                if (logger.isDebugEnabled()) {
                  // Add the array of payloads to the responseLogData
                  responseLogData.put("response.payload", gson.toJson(responsePayloads));

                  LoggingUtils.logWithMDC(
                      logger, Level.DEBUG, responseLogData, "Received response.");
                }

                super.onClose(status, trailers);
              }
            };

        super.start(loggingListener, headers);
      }

      @Override
      public void sendMessage(ReqT message) {

        if (logger.isDebugEnabled()) {
          requestLogData.put("request.payload", gson.toJson(message));
          LoggingUtils.logWithMDC(logger, Level.DEBUG, requestLogData, "Sending gRPC request.");
        }

        super.sendMessage(message);
      }
    };
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
