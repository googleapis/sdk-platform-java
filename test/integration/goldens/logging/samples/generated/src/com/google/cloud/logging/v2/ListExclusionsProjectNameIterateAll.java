package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.ProjectName;

public class ListExclusionsProjectNameIterateAll {

  public static void main(String[] args) throws Exception {
    listExclusionsProjectNameIterateAll();
  }

  public static void listExclusionsProjectNameIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      for (LogExclusion element : configClient.listExclusions(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
