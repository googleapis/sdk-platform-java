package com.google.api.gax.grpc;

import com.google.api.gax.logging.LoggingUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.grpc.*;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

public class GrpcLoggingInterceptor implements ClientInterceptor {

  private static final Logger logger = LoggingUtils.getLogger(GrpcLoggingInterceptor.class);
  private static final Gson gson = new Gson();
  private JsonObject serviceAndRpc = new JsonObject();
  private JsonObject requestLogData = new JsonObject();

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
      MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {

    return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
        next.newCall(method, callOptions)) {

      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        // if (LoggingUtils.isLoggingEnabled()) {
        // Capture request details
        String serviceName = method.getServiceName();
        String methodName = method.getFullMethodName();

        serviceAndRpc.addProperty("serviceName", serviceName);
        serviceAndRpc.addProperty("rpcName", methodName);

        JsonObject responseLogData = new JsonObject();
        // Add request details to MDC// Example system
        // MDC.put("serviceName", serviceName);
        // MDC.put("rpcName", methodName);
        // Capture and log headers
        headers
            .keys()
            .forEach(
                key -> {
                  Metadata.Key<String> metadataKey =
                      Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER);
                  String headerValue = headers.get(metadataKey);
                  // MDC.put("request.headers:" + key, headerValue);
                  requestLogData.addProperty("request.headers:" + key, headerValue);
                });

        requestLogData.addProperty("message", "Sending gRPC request");
        // logger.debug("Sending gRPC request");
        // }

        super.start(
            new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(
                responseListener) {
              @Override
              public void onMessage(RespT message) {

                responseLogData.addProperty("response.payload", gson.toJson(message));
                super.onMessage(message);
              }

              @Override
              public void onClose(Status status, Metadata trailers) {
                responseLogData.addProperty("response.status", status.getCode().name());
                responseLogData.addProperty(
                    "message", String.format("gRPC request finished with status: %s", status));

                // Create a JsonObject for response headers
                JsonObject responseHeaders = new JsonObject();

                // Access and add response headers to the JsonObject
                trailers
                    .keys()
                    .forEach(
                        key -> {
                          Metadata.Key<String> metadataKey =
                              Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER);
                          String headerValue = trailers.get(metadataKey);
                          responseHeaders.addProperty(key, headerValue);
                        });
                responseLogData.add("response.headers", responseHeaders);

                logger.debug(
                    gson.toJson(LoggingUtils.mergeJsonObject(serviceAndRpc, responseLogData)));
                // logger.info("gRPC request finished with status: {}", status);
                // MDC.clear(); // Clear MDC after the request
                super.onClose(status, trailers);
              }
            },
            headers);
      }

      @Override
      public void sendMessage(ReqT message) {
        // MDC.put("request.payload", gson.toJson(message));
        requestLogData.addProperty("request.payload", gson.toJson(message));

        Map<String, String> map = new HashMap<>();
        serviceAndRpc
            .entrySet()
            .forEach(entry -> map.put(entry.getKey(), entry.getValue().getAsString()));
        // MDC.setContextMap(map);
        // logger.info(new MapMessage(map));
        logger.info(gson.toJson(LoggingUtils.mergeJsonObject(serviceAndRpc, requestLogData)));
        // MDC.clear();
        super.sendMessage(message);
      }
    };
  }
}
