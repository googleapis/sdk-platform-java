package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.CreateBucketRequest;
import com.google.logging.v2.LocationName;
import com.google.logging.v2.LogBucket;

public class CreateBucketCallableFutureCallCreateBucketRequest {

  public static void main(String[] args) throws Exception {
    createBucketCallableFutureCallCreateBucketRequest();
  }

  public static void createBucketCallableFutureCallCreateBucketRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      CreateBucketRequest request =
          CreateBucketRequest.newBuilder()
              .setParent(LocationName.of("[PROJECT]", "[LOCATION]").toString())
              .setBucketId("bucketId-1603305307")
              .setBucket(LogBucket.newBuilder().build())
              .build();
      ApiFuture<LogBucket> future = configClient.createBucketCallable().futureCall(request);
      // Do something.
      LogBucket response = future.get();
    }
  }
}
