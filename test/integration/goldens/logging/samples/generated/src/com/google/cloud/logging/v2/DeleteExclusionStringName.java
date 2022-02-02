package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusionName;
import com.google.protobuf.Empty;

public class DeleteExclusionStringName {

  public static void main(String[] args) throws Exception {
    deleteExclusionStringName();
  }

  public static void deleteExclusionStringName() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      String name = LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]").toString();
      configClient.deleteExclusion(name);
    }
  }
}
