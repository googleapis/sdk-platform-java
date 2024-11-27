package com.google.api.gax.httpjson;

import com.google.api.gax.httpjson.ForwardingHttpJsonClientCall.SimpleForwardingHttpJsonClientCall;
import com.google.api.gax.logging.LoggingUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.HashMap;
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
        super.start(
            new HttpJsonClientCall.Listener<RespT>() {
              @Override
              public void onMessage(RespT message) {
                if (logger.isDebugEnabled()) {
                  // Add each message to the array
                  responsePayloads.add(gson.toJsonTree(message));
                }
                responseListener.onMessage(message);
              }

              @Override
              public void onClose(int status, HttpJsonMetadata trailers) {
                if (logger.isInfoEnabled()) {

                  serviceAndRpc.put("response.status", String.valueOf(status));
                  responseLogData.putAll(serviceAndRpc);
                }
                if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
                  LoggingUtils.logWithMDC(
                      logger, Level.INFO, serviceAndRpc, "HTTP request finished.");
                }
                if (logger.isDebugEnabled()) {

                  JsonObject jsonHeaders = new JsonObject();
                  headers
                      .getHeaders()
                      .forEach((key, value) -> jsonHeaders.addProperty(key, value.toString()));
                  responseLogData.put("response.headers", gson.toJson(jsonHeaders));

                  // Add the array of payloads to the responseLogData
                  responseLogData.put("response.payload", gson.toJson(responsePayloads));
                  LoggingUtils.logWithMDC(
                      logger,
                      Level.DEBUG,
                      responseLogData,
                      "Received response header and payload.");
                }

                responseListener.onClose(status, trailers);
              }
            },
            headers);
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
