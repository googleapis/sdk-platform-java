package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.LogExclusionName;
import com.google.logging.v2.UpdateExclusionRequest;
import com.google.protobuf.FieldMask;

public class UpdateExclusionCallableFutureCallUpdateExclusionRequest {

  public static void main(String[] args) throws Exception {
    updateExclusionCallableFutureCallUpdateExclusionRequest();
  }

  public static void updateExclusionCallableFutureCallUpdateExclusionRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      UpdateExclusionRequest request =
          UpdateExclusionRequest.newBuilder()
              .setName(
                  LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]").toString())
              .setExclusion(LogExclusion.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<LogExclusion> future = configClient.updateExclusionCallable().futureCall(request);
      // Do something.
      LogExclusion response = future.get();
    }
  }
}
