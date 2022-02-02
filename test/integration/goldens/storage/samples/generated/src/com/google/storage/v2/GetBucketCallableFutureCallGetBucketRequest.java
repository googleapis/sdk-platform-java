package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.protobuf.FieldMask;
import com.google.storage.v2.Bucket;
import com.google.storage.v2.BucketName;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.GetBucketRequest;
import com.google.storage.v2.StorageClient;

public class GetBucketCallableFutureCallGetBucketRequest {

  public static void main(String[] args) throws Exception {
    getBucketCallableFutureCallGetBucketRequest();
  }

  public static void getBucketCallableFutureCallGetBucketRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      GetBucketRequest request =
          GetBucketRequest.newBuilder()
              .setName(BucketName.of("[PROJECT]", "[BUCKET]").toString())
              .setIfMetagenerationMatch(1043427781)
              .setIfMetagenerationNotMatch(1025430873)
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .setReadMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<Bucket> future = storageClient.getBucketCallable().futureCall(request);
      // Do something.
      Bucket response = future.get();
    }
  }
}
