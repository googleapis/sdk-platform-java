package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.ProjectName;

public class CreateExclusionStringParentLogExclusionExclusion {

  public static void main(String[] args) throws Exception {
    createExclusionStringParentLogExclusionExclusion();
  }

  public static void createExclusionStringParentLogExclusionExclusion() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      LogExclusion exclusion = LogExclusion.newBuilder().build();
      LogExclusion response = configClient.createExclusion(parent, exclusion);
    }
  }
}
