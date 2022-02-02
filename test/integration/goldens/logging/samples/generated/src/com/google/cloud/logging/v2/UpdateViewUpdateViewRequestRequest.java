package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogView;
import com.google.logging.v2.UpdateViewRequest;
import com.google.protobuf.FieldMask;

public class UpdateViewUpdateViewRequestRequest {

  public static void main(String[] args) throws Exception {
    updateViewUpdateViewRequestRequest();
  }

  public static void updateViewUpdateViewRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      UpdateViewRequest request =
          UpdateViewRequest.newBuilder()
              .setName("name3373707")
              .setView(LogView.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      LogView response = configClient.updateView(request);
    }
  }
}
