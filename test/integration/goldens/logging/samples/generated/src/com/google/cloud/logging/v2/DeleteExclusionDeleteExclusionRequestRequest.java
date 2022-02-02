package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.DeleteExclusionRequest;
import com.google.logging.v2.LogExclusionName;
import com.google.protobuf.Empty;

public class DeleteExclusionDeleteExclusionRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteExclusionDeleteExclusionRequestRequest();
  }

  public static void deleteExclusionDeleteExclusionRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      DeleteExclusionRequest request =
          DeleteExclusionRequest.newBuilder()
              .setName(
                  LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]").toString())
              .build();
      configClient.deleteExclusion(request);
    }
  }
}
