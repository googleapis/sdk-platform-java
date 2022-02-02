package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogBucketName;
import com.google.logging.v2.UndeleteBucketRequest;
import com.google.protobuf.Empty;

public class UndeleteBucketUndeleteBucketRequestRequest {

  public static void main(String[] args) throws Exception {
    undeleteBucketUndeleteBucketRequestRequest();
  }

  public static void undeleteBucketUndeleteBucketRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      UndeleteBucketRequest request =
          UndeleteBucketRequest.newBuilder()
              .setName(
                  LogBucketName.ofProjectLocationBucketName("[PROJECT]", "[LOCATION]", "[BUCKET]")
                      .toString())
              .build();
      configClient.undeleteBucket(request);
    }
  }
}
