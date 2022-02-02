package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.DeleteBucketRequest;
import com.google.logging.v2.LogBucketName;
import com.google.protobuf.Empty;

public class DeleteBucketDeleteBucketRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteBucketDeleteBucketRequestRequest();
  }

  public static void deleteBucketDeleteBucketRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      DeleteBucketRequest request =
          DeleteBucketRequest.newBuilder()
              .setName(
                  LogBucketName.ofProjectLocationBucketName("[PROJECT]", "[LOCATION]", "[BUCKET]")
                      .toString())
              .build();
      configClient.deleteBucket(request);
    }
  }
}
