package com.google.api.gax.grpc;

import com.google.api.gax.logging.LoggingUtils;
import com.google.gson.Gson;
import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.MDC;

public class GrpcLoggingInterceptor implements ClientInterceptor {

  private static final Logger logger = LoggingUtils.getLogger(GrpcLoggingInterceptor.class);
  private static final Gson gson = new Gson();

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
      MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {

    return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
        next.newCall(method, callOptions)) {

      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        if (LoggingUtils.isLoggingEnabled()) {
          // Capture request details
          String serviceName = method.getServiceName();
          String methodName = method.getFullMethodName();

          // Add request details to MDC// Example system
          MDC.put("serviceName", serviceName);
          MDC.put("rpcName", methodName);
          // Capture and log headers
          headers
              .keys()
              .forEach(
                  key -> {
                    Metadata.Key<String> metadataKey =
                        Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER);
                    String headerValue = headers.get(metadataKey);
                    MDC.put("request.headers:" + key, headerValue);
                  });

          logger.debug("Sending gRPC request");
        }

        super.start(
            new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(
                responseListener) {
              @Override
              public void onMessage(RespT message) {
                MDC.put("response.payload", gson.toJson(message));
                logger.debug("Received gRPC response.");

                super.onMessage(message);
              }

              @Override
              public void onClose(Status status, Metadata trailers) {
                MDC.put("response.status", status.getCode().name());
                logger.info("gRPC request finished with status: {}", status);
                MDC.clear(); // Clear MDC after the request
                super.onClose(status, trailers);
              }
            },
            headers);
      }

      @Override
      public void sendMessage(ReqT message) {
        MDC.put("request.payload", gson.toJson(message));
        super.sendMessage(message);
      }
    };
  }
}
