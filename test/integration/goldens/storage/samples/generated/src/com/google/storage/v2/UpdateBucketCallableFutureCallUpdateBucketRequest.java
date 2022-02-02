package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.protobuf.FieldMask;
import com.google.storage.v2.Bucket;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.PredefinedBucketAcl;
import com.google.storage.v2.PredefinedObjectAcl;
import com.google.storage.v2.StorageClient;
import com.google.storage.v2.UpdateBucketRequest;

public class UpdateBucketCallableFutureCallUpdateBucketRequest {

  public static void main(String[] args) throws Exception {
    updateBucketCallableFutureCallUpdateBucketRequest();
  }

  public static void updateBucketCallableFutureCallUpdateBucketRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      UpdateBucketRequest request =
          UpdateBucketRequest.newBuilder()
              .setBucket(Bucket.newBuilder().build())
              .setIfMetagenerationMatch(1043427781)
              .setIfMetagenerationNotMatch(1025430873)
              .setPredefinedAcl(PredefinedBucketAcl.forNumber(0))
              .setPredefinedDefaultObjectAcl(PredefinedObjectAcl.forNumber(0))
              .setUpdateMask(FieldMask.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ApiFuture<Bucket> future = storageClient.updateBucketCallable().futureCall(request);
      // Do something.
      Bucket response = future.get();
    }
  }
}
