package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.GetViewRequest;
import com.google.logging.v2.LogView;
import com.google.logging.v2.LogViewName;

public class GetViewGetViewRequestRequest {

  public static void main(String[] args) throws Exception {
    getViewGetViewRequestRequest();
  }

  public static void getViewGetViewRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      GetViewRequest request =
          GetViewRequest.newBuilder()
              .setName(
                  LogViewName.ofProjectLocationBucketViewName(
                          "[PROJECT]", "[LOCATION]", "[BUCKET]", "[VIEW]")
                      .toString())
              .build();
      LogView response = configClient.getView(request);
    }
  }
}
