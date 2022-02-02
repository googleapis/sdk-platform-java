package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.DeleteExclusionRequest;
import com.google.logging.v2.LogExclusionName;
import com.google.protobuf.Empty;

public class DeleteExclusionCallableFutureCallDeleteExclusionRequest {

  public static void main(String[] args) throws Exception {
    deleteExclusionCallableFutureCallDeleteExclusionRequest();
  }

  public static void deleteExclusionCallableFutureCallDeleteExclusionRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      DeleteExclusionRequest request =
          DeleteExclusionRequest.newBuilder()
              .setName(
                  LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]").toString())
              .build();
      ApiFuture<Empty> future = configClient.deleteExclusionCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
