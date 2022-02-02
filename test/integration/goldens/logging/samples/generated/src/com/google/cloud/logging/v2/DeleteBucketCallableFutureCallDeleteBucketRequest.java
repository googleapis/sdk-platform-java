package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.DeleteBucketRequest;
import com.google.logging.v2.LogBucketName;
import com.google.protobuf.Empty;

public class DeleteBucketCallableFutureCallDeleteBucketRequest {

  public static void main(String[] args) throws Exception {
    deleteBucketCallableFutureCallDeleteBucketRequest();
  }

  public static void deleteBucketCallableFutureCallDeleteBucketRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      DeleteBucketRequest request =
          DeleteBucketRequest.newBuilder()
              .setName(
                  LogBucketName.ofProjectLocationBucketName("[PROJECT]", "[LOCATION]", "[BUCKET]")
                      .toString())
              .build();
      ApiFuture<Empty> future = configClient.deleteBucketCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
