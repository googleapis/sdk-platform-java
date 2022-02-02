package com.google.storage.v2.samples;

import com.google.protobuf.FieldMask;
import com.google.storage.v2.Bucket;
import com.google.storage.v2.BucketName;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.GetBucketRequest;
import com.google.storage.v2.StorageClient;

public class GetBucketGetBucketRequestRequest {

  public static void main(String[] args) throws Exception {
    getBucketGetBucketRequestRequest();
  }

  public static void getBucketGetBucketRequestRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      GetBucketRequest request =
          GetBucketRequest.newBuilder()
              .setName(BucketName.of("[PROJECT]", "[BUCKET]").toString())
              .setIfMetagenerationMatch(1043427781)
              .setIfMetagenerationNotMatch(1025430873)
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .setReadMask(FieldMask.newBuilder().build())
              .build();
      Bucket response = storageClient.getBucket(request);
    }
  }
}
