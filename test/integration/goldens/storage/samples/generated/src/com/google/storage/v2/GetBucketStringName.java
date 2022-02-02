package com.google.storage.v2.samples;

import com.google.storage.v2.Bucket;
import com.google.storage.v2.BucketName;
import com.google.storage.v2.StorageClient;

public class GetBucketStringName {

  public static void main(String[] args) throws Exception {
    getBucketStringName();
  }

  public static void getBucketStringName() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String name = BucketName.of("[PROJECT]", "[BUCKET]").toString();
      Bucket response = storageClient.getBucket(name);
    }
  }
}
