package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.FolderName;
import com.google.logging.v2.LogExclusion;

public class CreateExclusionFolderNameParentLogExclusionExclusion {

  public static void main(String[] args) throws Exception {
    createExclusionFolderNameParentLogExclusionExclusion();
  }

  public static void createExclusionFolderNameParentLogExclusionExclusion() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      FolderName parent = FolderName.of("[FOLDER]");
      LogExclusion exclusion = LogExclusion.newBuilder().build();
      LogExclusion response = configClient.createExclusion(parent, exclusion);
    }
  }
}
