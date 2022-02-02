package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.CreateViewRequest;
import com.google.logging.v2.LogView;

public class CreateViewCreateViewRequestRequest {

  public static void main(String[] args) throws Exception {
    createViewCreateViewRequestRequest();
  }

  public static void createViewCreateViewRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      CreateViewRequest request =
          CreateViewRequest.newBuilder()
              .setParent("parent-995424086")
              .setViewId("viewId-816632160")
              .setView(LogView.newBuilder().build())
              .build();
      LogView response = configClient.createView(request);
    }
  }
}
