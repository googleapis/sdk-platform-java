package com.google.storage.v2.samples;

import com.google.protobuf.FieldMask;
import com.google.storage.v2.Bucket;
import com.google.storage.v2.StorageClient;

public class UpdateBucketBucketBucketFieldMaskUpdateMask {

  public static void main(String[] args) throws Exception {
    updateBucketBucketBucketFieldMaskUpdateMask();
  }

  public static void updateBucketBucketBucketFieldMaskUpdateMask() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      Bucket bucket = Bucket.newBuilder().build();
      FieldMask updateMask = FieldMask.newBuilder().build();
      Bucket response = storageClient.updateBucket(bucket, updateMask);
    }
  }
}
