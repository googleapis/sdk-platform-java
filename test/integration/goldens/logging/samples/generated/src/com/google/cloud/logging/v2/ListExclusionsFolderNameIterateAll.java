package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.FolderName;
import com.google.logging.v2.LogExclusion;

public class ListExclusionsFolderNameIterateAll {

  public static void main(String[] args) throws Exception {
    listExclusionsFolderNameIterateAll();
  }

  public static void listExclusionsFolderNameIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      FolderName parent = FolderName.of("[FOLDER]");
      for (LogExclusion element : configClient.listExclusions(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
