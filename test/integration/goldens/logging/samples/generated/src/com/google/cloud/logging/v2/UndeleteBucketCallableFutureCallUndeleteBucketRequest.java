package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogBucketName;
import com.google.logging.v2.UndeleteBucketRequest;
import com.google.protobuf.Empty;

public class UndeleteBucketCallableFutureCallUndeleteBucketRequest {

  public static void main(String[] args) throws Exception {
    undeleteBucketCallableFutureCallUndeleteBucketRequest();
  }

  public static void undeleteBucketCallableFutureCallUndeleteBucketRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      UndeleteBucketRequest request =
          UndeleteBucketRequest.newBuilder()
              .setName(
                  LogBucketName.ofProjectLocationBucketName("[PROJECT]", "[LOCATION]", "[BUCKET]")
                      .toString())
              .build();
      ApiFuture<Empty> future = configClient.undeleteBucketCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
