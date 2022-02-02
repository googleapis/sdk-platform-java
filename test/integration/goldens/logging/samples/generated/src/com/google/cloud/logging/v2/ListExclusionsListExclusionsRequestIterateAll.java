package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.ListExclusionsRequest;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.ProjectName;

public class ListExclusionsListExclusionsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listExclusionsListExclusionsRequestIterateAll();
  }

  public static void listExclusionsListExclusionsRequestIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ListExclusionsRequest request =
          ListExclusionsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      for (LogExclusion element : configClient.listExclusions(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
