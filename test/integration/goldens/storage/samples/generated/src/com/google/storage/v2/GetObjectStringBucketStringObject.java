package com.google.storage.v2.samples;

import com.google.storage.v2.Object;
import com.google.storage.v2.StorageClient;

public class GetObjectStringBucketStringObject {

  public static void main(String[] args) throws Exception {
    getObjectStringBucketStringObject();
  }

  public static void getObjectStringBucketStringObject() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String bucket = "bucket-1378203158";
      String object = "object-1023368385";
      Object response = storageClient.getObject(bucket, object);
    }
  }
}
