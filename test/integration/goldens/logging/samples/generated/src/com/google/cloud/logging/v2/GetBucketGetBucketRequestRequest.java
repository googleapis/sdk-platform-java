package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.GetBucketRequest;
import com.google.logging.v2.LogBucket;
import com.google.logging.v2.LogBucketName;

public class GetBucketGetBucketRequestRequest {

  public static void main(String[] args) throws Exception {
    getBucketGetBucketRequestRequest();
  }

  public static void getBucketGetBucketRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      GetBucketRequest request =
          GetBucketRequest.newBuilder()
              .setName(
                  LogBucketName.ofProjectLocationBucketName("[PROJECT]", "[LOCATION]", "[BUCKET]")
                      .toString())
              .build();
      LogBucket response = configClient.getBucket(request);
    }
  }
}
