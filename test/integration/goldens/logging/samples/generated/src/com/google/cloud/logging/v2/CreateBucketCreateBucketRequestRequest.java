package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.CreateBucketRequest;
import com.google.logging.v2.LocationName;
import com.google.logging.v2.LogBucket;

public class CreateBucketCreateBucketRequestRequest {

  public static void main(String[] args) throws Exception {
    createBucketCreateBucketRequestRequest();
  }

  public static void createBucketCreateBucketRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      CreateBucketRequest request =
          CreateBucketRequest.newBuilder()
              .setParent(LocationName.of("[PROJECT]", "[LOCATION]").toString())
              .setBucketId("bucketId-1603305307")
              .setBucket(LogBucket.newBuilder().build())
              .build();
      LogBucket response = configClient.createBucket(request);
    }
  }
}
