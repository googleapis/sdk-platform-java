package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.CmekSettings;
import com.google.logging.v2.CmekSettingsName;
import com.google.logging.v2.GetCmekSettingsRequest;

public class GetCmekSettingsGetCmekSettingsRequestRequest {

  public static void main(String[] args) throws Exception {
    getCmekSettingsGetCmekSettingsRequestRequest();
  }

  public static void getCmekSettingsGetCmekSettingsRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      GetCmekSettingsRequest request =
          GetCmekSettingsRequest.newBuilder()
              .setName(CmekSettingsName.ofProjectName("[PROJECT]").toString())
              .build();
      CmekSettings response = configClient.getCmekSettings(request);
    }
  }
}
