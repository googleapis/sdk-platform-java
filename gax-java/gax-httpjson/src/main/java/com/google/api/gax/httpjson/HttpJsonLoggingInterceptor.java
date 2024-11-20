package com.google.api.gax.httpjson;

import com.google.api.gax.httpjson.ForwardingHttpJsonClientCall.SimpleForwardingHttpJsonClientCall;
import com.google.gson.Gson;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class HttpJsonLoggingInterceptor implements HttpJsonClientInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(HttpJsonLoggingInterceptor.class);
  private static final Gson gson = new Gson();

  @Override
  public <ReqT, RespT> HttpJsonClientCall<ReqT, RespT> interceptCall(
      ApiMethodDescriptor<ReqT, RespT> method,
      HttpJsonCallOptions callOptions,
      HttpJsonChannel next) {

    return new SimpleForwardingHttpJsonClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
      @Override
      public void start(
          HttpJsonClientCall.Listener<RespT> responseListener, HttpJsonMetadata headers) {
        // Capture request details
        String methodName = method.getFullMethodName();

        // Add request details to MDC
        MDC.put("method", methodName);

        // Capture and log headers
        for (Map.Entry<String, Object> header : headers.getHeaders().entrySet()) {
          MDC.put("header." + header.getKey(), header.getValue().toString());
        }

        logger.info("Sending HTTP request");

        super.start(
            new HttpJsonClientCall.Listener<RespT>() {
              @Override
              public void onMessage(RespT message) {
                MDC.put("response.payload", gson.toJson(message));
                logger.debug("Received HTTP response.");

                responseListener.onMessage(message);
              }

              @Override
              public void onClose(int status, HttpJsonMetadata trailers) {
                MDC.put("response.status", String.valueOf(status));
                logger.info("HTTP request finished with status: {}", status);
                MDC.clear(); // Clear MDC after the request
                responseListener.onClose(status, trailers);
              }
            },
            headers);
      }
    };
  }
}
