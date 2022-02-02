package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.ProjectName;

public class CreateExclusionProjectNameParentLogExclusionExclusion {

  public static void main(String[] args) throws Exception {
    createExclusionProjectNameParentLogExclusionExclusion();
  }

  public static void createExclusionProjectNameParentLogExclusionExclusion() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      LogExclusion exclusion = LogExclusion.newBuilder().build();
      LogExclusion response = configClient.createExclusion(parent, exclusion);
    }
  }
}
