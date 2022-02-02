package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.OrganizationName;

public class CreateExclusionOrganizationNameParentLogExclusionExclusion {

  public static void main(String[] args) throws Exception {
    createExclusionOrganizationNameParentLogExclusionExclusion();
  }

  public static void createExclusionOrganizationNameParentLogExclusionExclusion() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      OrganizationName parent = OrganizationName.of("[ORGANIZATION]");
      LogExclusion exclusion = LogExclusion.newBuilder().build();
      LogExclusion response = configClient.createExclusion(parent, exclusion);
    }
  }
}
