package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.DeleteSinkRequest;
import com.google.logging.v2.LogSinkName;
import com.google.protobuf.Empty;

public class DeleteSinkCallableFutureCallDeleteSinkRequest {

  public static void main(String[] args) throws Exception {
    deleteSinkCallableFutureCallDeleteSinkRequest();
  }

  public static void deleteSinkCallableFutureCallDeleteSinkRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      DeleteSinkRequest request =
          DeleteSinkRequest.newBuilder()
              .setSinkName(LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]").toString())
              .build();
      ApiFuture<Empty> future = configClient.deleteSinkCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
