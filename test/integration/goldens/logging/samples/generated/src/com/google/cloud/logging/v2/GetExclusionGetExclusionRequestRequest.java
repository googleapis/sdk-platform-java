package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.GetExclusionRequest;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.LogExclusionName;

public class GetExclusionGetExclusionRequestRequest {

  public static void main(String[] args) throws Exception {
    getExclusionGetExclusionRequestRequest();
  }

  public static void getExclusionGetExclusionRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      GetExclusionRequest request =
          GetExclusionRequest.newBuilder()
              .setName(
                  LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]").toString())
              .build();
      LogExclusion response = configClient.getExclusion(request);
    }
  }
}
