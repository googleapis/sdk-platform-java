package com.google.storage.v2.samples;

import com.google.storage.v2.Bucket;
import com.google.storage.v2.BucketName;
import com.google.storage.v2.StorageClient;

public class GetBucketBucketNameName {

  public static void main(String[] args) throws Exception {
    getBucketBucketNameName();
  }

  public static void getBucketBucketNameName() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      BucketName name = BucketName.of("[PROJECT]", "[BUCKET]");
      Bucket response = storageClient.getBucket(name);
    }
  }
}
