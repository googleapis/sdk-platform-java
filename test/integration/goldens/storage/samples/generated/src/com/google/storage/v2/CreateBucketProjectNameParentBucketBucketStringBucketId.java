package com.google.storage.v2.samples;

import com.google.storage.v2.Bucket;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class CreateBucketProjectNameParentBucketBucketStringBucketId {

  public static void main(String[] args) throws Exception {
    createBucketProjectNameParentBucketBucketStringBucketId();
  }

  public static void createBucketProjectNameParentBucketBucketStringBucketId() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      Bucket bucket = Bucket.newBuilder().build();
      String bucketId = "bucketId-1603305307";
      Bucket response = storageClient.createBucket(parent, bucket, bucketId);
    }
  }
}
