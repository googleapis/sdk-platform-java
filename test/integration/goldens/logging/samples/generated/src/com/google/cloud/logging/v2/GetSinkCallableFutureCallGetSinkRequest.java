package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.GetSinkRequest;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.LogSinkName;

public class GetSinkCallableFutureCallGetSinkRequest {

  public static void main(String[] args) throws Exception {
    getSinkCallableFutureCallGetSinkRequest();
  }

  public static void getSinkCallableFutureCallGetSinkRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      GetSinkRequest request =
          GetSinkRequest.newBuilder()
              .setSinkName(LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]").toString())
              .build();
      ApiFuture<LogSink> future = configClient.getSinkCallable().futureCall(request);
      // Do something.
      LogSink response = future.get();
    }
  }
}
