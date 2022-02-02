package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.OrganizationName;

public class ListExclusionsOrganizationNameIterateAll {

  public static void main(String[] args) throws Exception {
    listExclusionsOrganizationNameIterateAll();
  }

  public static void listExclusionsOrganizationNameIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      OrganizationName parent = OrganizationName.of("[ORGANIZATION]");
      for (LogExclusion element : configClient.listExclusions(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
