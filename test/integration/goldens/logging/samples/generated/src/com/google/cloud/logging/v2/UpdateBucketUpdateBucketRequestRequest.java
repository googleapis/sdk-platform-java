package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogBucket;
import com.google.logging.v2.LogBucketName;
import com.google.logging.v2.UpdateBucketRequest;
import com.google.protobuf.FieldMask;

public class UpdateBucketUpdateBucketRequestRequest {

  public static void main(String[] args) throws Exception {
    updateBucketUpdateBucketRequestRequest();
  }

  public static void updateBucketUpdateBucketRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      UpdateBucketRequest request =
          UpdateBucketRequest.newBuilder()
              .setName(
                  LogBucketName.ofProjectLocationBucketName("[PROJECT]", "[LOCATION]", "[BUCKET]")
                      .toString())
              .setBucket(LogBucket.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      LogBucket response = configClient.updateBucket(request);
    }
  }
}
