package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.CreateExclusionRequest;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.ProjectName;

public class CreateExclusionCreateExclusionRequestRequest {

  public static void main(String[] args) throws Exception {
    createExclusionCreateExclusionRequestRequest();
  }

  public static void createExclusionCreateExclusionRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      CreateExclusionRequest request =
          CreateExclusionRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setExclusion(LogExclusion.newBuilder().build())
              .build();
      LogExclusion response = configClient.createExclusion(request);
    }
  }
}
