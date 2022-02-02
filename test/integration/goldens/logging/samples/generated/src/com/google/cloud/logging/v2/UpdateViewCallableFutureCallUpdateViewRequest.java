package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogView;
import com.google.logging.v2.UpdateViewRequest;
import com.google.protobuf.FieldMask;

public class UpdateViewCallableFutureCallUpdateViewRequest {

  public static void main(String[] args) throws Exception {
    updateViewCallableFutureCallUpdateViewRequest();
  }

  public static void updateViewCallableFutureCallUpdateViewRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      UpdateViewRequest request =
          UpdateViewRequest.newBuilder()
              .setName("name3373707")
              .setView(LogView.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<LogView> future = configClient.updateViewCallable().futureCall(request);
      // Do something.
      LogView response = future.get();
    }
  }
}
