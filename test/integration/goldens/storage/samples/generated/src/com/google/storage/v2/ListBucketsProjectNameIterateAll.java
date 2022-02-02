package com.google.storage.v2.samples;

import com.google.storage.v2.Bucket;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListBucketsProjectNameIterateAll {

  public static void main(String[] args) throws Exception {
    listBucketsProjectNameIterateAll();
  }

  public static void listBucketsProjectNameIterateAll() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      for (Bucket element : storageClient.listBuckets(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
