package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.GetViewRequest;
import com.google.logging.v2.LogView;
import com.google.logging.v2.LogViewName;

public class GetViewCallableFutureCallGetViewRequest {

  public static void main(String[] args) throws Exception {
    getViewCallableFutureCallGetViewRequest();
  }

  public static void getViewCallableFutureCallGetViewRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      GetViewRequest request =
          GetViewRequest.newBuilder()
              .setName(
                  LogViewName.ofProjectLocationBucketViewName(
                          "[PROJECT]", "[LOCATION]", "[BUCKET]", "[VIEW]")
                      .toString())
              .build();
      ApiFuture<LogView> future = configClient.getViewCallable().futureCall(request);
      // Do something.
      LogView response = future.get();
    }
  }
}
