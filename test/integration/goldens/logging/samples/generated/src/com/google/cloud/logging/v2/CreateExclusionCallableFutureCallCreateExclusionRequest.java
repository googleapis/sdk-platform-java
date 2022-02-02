package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.CreateExclusionRequest;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.ProjectName;

public class CreateExclusionCallableFutureCallCreateExclusionRequest {

  public static void main(String[] args) throws Exception {
    createExclusionCallableFutureCallCreateExclusionRequest();
  }

  public static void createExclusionCallableFutureCallCreateExclusionRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      CreateExclusionRequest request =
          CreateExclusionRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setExclusion(LogExclusion.newBuilder().build())
              .build();
      ApiFuture<LogExclusion> future = configClient.createExclusionCallable().futureCall(request);
      // Do something.
      LogExclusion response = future.get();
    }
  }
}
