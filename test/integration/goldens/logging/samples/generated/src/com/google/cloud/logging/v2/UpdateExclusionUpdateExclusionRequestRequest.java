package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.LogExclusionName;
import com.google.logging.v2.UpdateExclusionRequest;
import com.google.protobuf.FieldMask;

public class UpdateExclusionUpdateExclusionRequestRequest {

  public static void main(String[] args) throws Exception {
    updateExclusionUpdateExclusionRequestRequest();
  }

  public static void updateExclusionUpdateExclusionRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      UpdateExclusionRequest request =
          UpdateExclusionRequest.newBuilder()
              .setName(
                  LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]").toString())
              .setExclusion(LogExclusion.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      LogExclusion response = configClient.updateExclusion(request);
    }
  }
}
