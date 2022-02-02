package com.google.storage.v2.samples;

import com.google.storage.v2.Bucket;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListBucketsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listBucketsStringIterateAll();
  }

  public static void listBucketsStringIterateAll() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      for (Bucket element : storageClient.listBuckets(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
