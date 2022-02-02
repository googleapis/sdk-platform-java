package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.FolderLocationName;
import com.google.logging.v2.LogBucket;

public class ListBucketsFolderLocationNameIterateAll {

  public static void main(String[] args) throws Exception {
    listBucketsFolderLocationNameIterateAll();
  }

  public static void listBucketsFolderLocationNameIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      FolderLocationName parent = FolderLocationName.of("[FOLDER]", "[LOCATION]");
      for (LogBucket element : configClient.listBuckets(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
