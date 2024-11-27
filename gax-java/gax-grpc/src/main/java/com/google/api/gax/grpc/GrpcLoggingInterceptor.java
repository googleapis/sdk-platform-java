package com.google.api.gax.grpc;

import com.google.api.gax.logging.LoggingUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.grpc.*;
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

        super.start(
            new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(
                responseListener) {
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
                  // Access and add response headers
                  JsonObject responseHeaders = mapHeadersToJsonObject(trailers);
                  responseLogData.put("response.headers", gson.toJson(responseHeaders));
                  // Add the array of payloads to the responseLogData
                  responseLogData.put("response.payload", gson.toJson(responsePayloads));

                  LoggingUtils.logWithMDC(
                      logger, Level.DEBUG, responseLogData, "Received response.");
                }

                super.onClose(status, trailers);
              }
            },
            headers);
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
