package com.google.storage.v2.samples;

import com.google.storage.v2.HmacKeyMetadata;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListHmacKeysProjectNameIterateAll {

  public static void main(String[] args) throws Exception {
    listHmacKeysProjectNameIterateAll();
  }

  public static void listHmacKeysProjectNameIterateAll() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ProjectName project = ProjectName.of("[PROJECT]");
      for (HmacKeyMetadata element : storageClient.listHmacKeys(project).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
