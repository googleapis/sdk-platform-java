package com.google.storage.v2.samples;

import com.google.protobuf.Empty;
import com.google.storage.v2.BucketName;
import com.google.storage.v2.StorageClient;

public class DeleteBucketBucketNameName {

  public static void main(String[] args) throws Exception {
    deleteBucketBucketNameName();
  }

  public static void deleteBucketBucketNameName() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      BucketName name = BucketName.of("[PROJECT]", "[BUCKET]");
      storageClient.deleteBucket(name);
    }
  }
}
