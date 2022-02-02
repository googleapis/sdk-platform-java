package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.ProjectName;

public class ListExclusionsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listExclusionsStringIterateAll();
  }

  public static void listExclusionsStringIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      for (LogExclusion element : configClient.listExclusions(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
