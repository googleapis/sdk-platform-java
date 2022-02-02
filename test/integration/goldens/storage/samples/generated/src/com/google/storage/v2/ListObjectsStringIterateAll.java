package com.google.storage.v2.samples;

import com.google.storage.v2.Object;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListObjectsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listObjectsStringIterateAll();
  }

  public static void listObjectsStringIterateAll() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      for (Object element : storageClient.listObjects(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
