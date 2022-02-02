package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.DeleteViewRequest;
import com.google.logging.v2.LogViewName;
import com.google.protobuf.Empty;

public class DeleteViewCallableFutureCallDeleteViewRequest {

  public static void main(String[] args) throws Exception {
    deleteViewCallableFutureCallDeleteViewRequest();
  }

  public static void deleteViewCallableFutureCallDeleteViewRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      DeleteViewRequest request =
          DeleteViewRequest.newBuilder()
              .setName(
                  LogViewName.ofProjectLocationBucketViewName(
                          "[PROJECT]", "[LOCATION]", "[BUCKET]", "[VIEW]")
                      .toString())
              .build();
      ApiFuture<Empty> future = configClient.deleteViewCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
