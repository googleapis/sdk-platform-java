package com.google.storage.v2.samples;

import com.google.protobuf.Empty;
import com.google.storage.v2.StorageClient;

public class DeleteObjectStringBucketStringObject {

  public static void main(String[] args) throws Exception {
    deleteObjectStringBucketStringObject();
  }

  public static void deleteObjectStringBucketStringObject() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String bucket = "bucket-1378203158";
      String object = "object-1023368385";
      storageClient.deleteObject(bucket, object);
    }
  }
}
