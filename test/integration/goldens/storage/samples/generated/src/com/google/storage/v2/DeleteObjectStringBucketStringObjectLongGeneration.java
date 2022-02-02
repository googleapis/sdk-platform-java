package com.google.storage.v2.samples;

import com.google.protobuf.Empty;
import com.google.storage.v2.StorageClient;

public class DeleteObjectStringBucketStringObjectLongGeneration {

  public static void main(String[] args) throws Exception {
    deleteObjectStringBucketStringObjectLongGeneration();
  }

  public static void deleteObjectStringBucketStringObjectLongGeneration() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String bucket = "bucket-1378203158";
      String object = "object-1023368385";
      long generation = 305703192;
      storageClient.deleteObject(bucket, object, generation);
    }
  }
}
