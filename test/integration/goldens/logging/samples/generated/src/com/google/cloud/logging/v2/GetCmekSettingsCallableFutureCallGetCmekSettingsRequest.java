package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.CmekSettings;
import com.google.logging.v2.CmekSettingsName;
import com.google.logging.v2.GetCmekSettingsRequest;

public class GetCmekSettingsCallableFutureCallGetCmekSettingsRequest {

  public static void main(String[] args) throws Exception {
    getCmekSettingsCallableFutureCallGetCmekSettingsRequest();
  }

  public static void getCmekSettingsCallableFutureCallGetCmekSettingsRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      GetCmekSettingsRequest request =
          GetCmekSettingsRequest.newBuilder()
              .setName(CmekSettingsName.ofProjectName("[PROJECT]").toString())
              .build();
      ApiFuture<CmekSettings> future = configClient.getCmekSettingsCallable().futureCall(request);
      // Do something.
      CmekSettings response = future.get();
    }
  }
}
