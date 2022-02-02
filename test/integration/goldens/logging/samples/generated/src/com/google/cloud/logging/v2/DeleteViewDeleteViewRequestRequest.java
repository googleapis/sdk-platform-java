package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.DeleteViewRequest;
import com.google.logging.v2.LogViewName;
import com.google.protobuf.Empty;

public class DeleteViewDeleteViewRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteViewDeleteViewRequestRequest();
  }

  public static void deleteViewDeleteViewRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      DeleteViewRequest request =
          DeleteViewRequest.newBuilder()
              .setName(
                  LogViewName.ofProjectLocationBucketViewName(
                          "[PROJECT]", "[LOCATION]", "[BUCKET]", "[VIEW]")
                      .toString())
              .build();
      configClient.deleteView(request);
    }
  }
}
