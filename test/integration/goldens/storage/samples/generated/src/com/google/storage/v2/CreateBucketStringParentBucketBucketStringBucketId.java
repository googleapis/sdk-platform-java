package com.google.storage.v2.samples;

import com.google.storage.v2.Bucket;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class CreateBucketStringParentBucketBucketStringBucketId {

  public static void main(String[] args) throws Exception {
    createBucketStringParentBucketBucketStringBucketId();
  }

  public static void createBucketStringParentBucketBucketStringBucketId() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      Bucket bucket = Bucket.newBuilder().build();
      String bucketId = "bucketId-1603305307";
      Bucket response = storageClient.createBucket(parent, bucket, bucketId);
    }
  }
}
