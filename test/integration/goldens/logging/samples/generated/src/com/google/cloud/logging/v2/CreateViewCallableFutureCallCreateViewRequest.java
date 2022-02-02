package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.CreateViewRequest;
import com.google.logging.v2.LogView;

public class CreateViewCallableFutureCallCreateViewRequest {

  public static void main(String[] args) throws Exception {
    createViewCallableFutureCallCreateViewRequest();
  }

  public static void createViewCallableFutureCallCreateViewRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      CreateViewRequest request =
          CreateViewRequest.newBuilder()
              .setParent("parent-995424086")
              .setViewId("viewId-816632160")
              .setView(LogView.newBuilder().build())
              .build();
      ApiFuture<LogView> future = configClient.createViewCallable().futureCall(request);
      // Do something.
      LogView response = future.get();
    }
  }
}
