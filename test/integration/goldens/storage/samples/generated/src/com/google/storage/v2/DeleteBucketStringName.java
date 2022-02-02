package com.google.storage.v2.samples;

import com.google.protobuf.Empty;
import com.google.storage.v2.BucketName;
import com.google.storage.v2.StorageClient;

public class DeleteBucketStringName {

  public static void main(String[] args) throws Exception {
    deleteBucketStringName();
  }

  public static void deleteBucketStringName() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String name = BucketName.of("[PROJECT]", "[BUCKET]").toString();
      storageClient.deleteBucket(name);
    }
  }
}
