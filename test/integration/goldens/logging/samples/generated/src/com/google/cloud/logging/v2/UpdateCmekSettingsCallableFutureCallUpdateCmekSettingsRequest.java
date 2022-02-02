package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.CmekSettings;
import com.google.logging.v2.UpdateCmekSettingsRequest;
import com.google.protobuf.FieldMask;

public class UpdateCmekSettingsCallableFutureCallUpdateCmekSettingsRequest {

  public static void main(String[] args) throws Exception {
    updateCmekSettingsCallableFutureCallUpdateCmekSettingsRequest();
  }

  public static void updateCmekSettingsCallableFutureCallUpdateCmekSettingsRequest()
      throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      UpdateCmekSettingsRequest request =
          UpdateCmekSettingsRequest.newBuilder()
              .setName("name3373707")
              .setCmekSettings(CmekSettings.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<CmekSettings> future =
          configClient.updateCmekSettingsCallable().futureCall(request);
      // Do something.
      CmekSettings response = future.get();
    }
  }
}
