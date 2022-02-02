package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.protobuf.Empty;
import com.google.storage.v2.BucketName;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.DeleteBucketRequest;
import com.google.storage.v2.StorageClient;

public class DeleteBucketCallableFutureCallDeleteBucketRequest {

  public static void main(String[] args) throws Exception {
    deleteBucketCallableFutureCallDeleteBucketRequest();
  }

  public static void deleteBucketCallableFutureCallDeleteBucketRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      DeleteBucketRequest request =
          DeleteBucketRequest.newBuilder()
              .setName(BucketName.of("[PROJECT]", "[BUCKET]").toString())
              .setIfMetagenerationMatch(1043427781)
              .setIfMetagenerationNotMatch(1025430873)
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ApiFuture<Empty> future = storageClient.deleteBucketCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
