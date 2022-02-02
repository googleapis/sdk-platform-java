package com.google.storage.v2.samples;

import com.google.protobuf.FieldMask;
import com.google.storage.v2.Bucket;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.PredefinedBucketAcl;
import com.google.storage.v2.PredefinedObjectAcl;
import com.google.storage.v2.StorageClient;
import com.google.storage.v2.UpdateBucketRequest;

public class UpdateBucketUpdateBucketRequestRequest {

  public static void main(String[] args) throws Exception {
    updateBucketUpdateBucketRequestRequest();
  }

  public static void updateBucketUpdateBucketRequestRequest() throws Exception {
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
      Bucket response = storageClient.updateBucket(request);
    }
  }
}
