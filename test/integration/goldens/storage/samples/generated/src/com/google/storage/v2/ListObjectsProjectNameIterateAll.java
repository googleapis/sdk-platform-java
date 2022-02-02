package com.google.storage.v2.samples;

import com.google.storage.v2.Object;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListObjectsProjectNameIterateAll {

  public static void main(String[] args) throws Exception {
    listObjectsProjectNameIterateAll();
  }

  public static void listObjectsProjectNameIterateAll() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      for (Object element : storageClient.listObjects(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
