package com.google.storage.v2.samples;

import com.google.storage.v2.Bucket;
import com.google.storage.v2.BucketName;
import com.google.storage.v2.StorageClient;

public class LockBucketRetentionPolicyStringBucket {

  public static void main(String[] args) throws Exception {
    lockBucketRetentionPolicyStringBucket();
  }

  public static void lockBucketRetentionPolicyStringBucket() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String bucket = BucketName.of("[PROJECT]", "[BUCKET]").toString();
      Bucket response = storageClient.lockBucketRetentionPolicy(bucket);
    }
  }
}
