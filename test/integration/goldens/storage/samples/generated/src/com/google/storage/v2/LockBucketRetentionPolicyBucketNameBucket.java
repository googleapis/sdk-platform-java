package com.google.storage.v2.samples;

import com.google.storage.v2.Bucket;
import com.google.storage.v2.BucketName;
import com.google.storage.v2.StorageClient;

public class LockBucketRetentionPolicyBucketNameBucket {

  public static void main(String[] args) throws Exception {
    lockBucketRetentionPolicyBucketNameBucket();
  }

  public static void lockBucketRetentionPolicyBucketNameBucket() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      BucketName bucket = BucketName.of("[PROJECT]", "[BUCKET]");
      Bucket response = storageClient.lockBucketRetentionPolicy(bucket);
    }
  }
}
