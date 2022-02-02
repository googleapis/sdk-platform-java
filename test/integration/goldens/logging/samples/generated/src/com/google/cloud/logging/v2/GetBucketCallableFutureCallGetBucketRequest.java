package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.GetBucketRequest;
import com.google.logging.v2.LogBucket;
import com.google.logging.v2.LogBucketName;

public class GetBucketCallableFutureCallGetBucketRequest {

  public static void main(String[] args) throws Exception {
    getBucketCallableFutureCallGetBucketRequest();
  }

  public static void getBucketCallableFutureCallGetBucketRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      GetBucketRequest request =
          GetBucketRequest.newBuilder()
              .setName(
                  LogBucketName.ofProjectLocationBucketName("[PROJECT]", "[LOCATION]", "[BUCKET]")
                      .toString())
              .build();
      ApiFuture<LogBucket> future = configClient.getBucketCallable().futureCall(request);
      // Do something.
      LogBucket response = future.get();
    }
  }
}
