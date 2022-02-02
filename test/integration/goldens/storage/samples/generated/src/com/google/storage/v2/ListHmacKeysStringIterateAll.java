package com.google.storage.v2.samples;

import com.google.storage.v2.HmacKeyMetadata;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListHmacKeysStringIterateAll {

  public static void main(String[] args) throws Exception {
    listHmacKeysStringIterateAll();
  }

  public static void listHmacKeysStringIterateAll() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String project = ProjectName.of("[PROJECT]").toString();
      for (HmacKeyMetadata element : storageClient.listHmacKeys(project).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
