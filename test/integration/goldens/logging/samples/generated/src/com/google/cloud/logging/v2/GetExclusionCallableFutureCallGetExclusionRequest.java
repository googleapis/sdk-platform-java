package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.GetExclusionRequest;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.LogExclusionName;

public class GetExclusionCallableFutureCallGetExclusionRequest {

  public static void main(String[] args) throws Exception {
    getExclusionCallableFutureCallGetExclusionRequest();
  }

  public static void getExclusionCallableFutureCallGetExclusionRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      GetExclusionRequest request =
          GetExclusionRequest.newBuilder()
              .setName(
                  LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]").toString())
              .build();
      ApiFuture<LogExclusion> future = configClient.getExclusionCallable().futureCall(request);
      // Do something.
      LogExclusion response = future.get();
    }
  }
}
