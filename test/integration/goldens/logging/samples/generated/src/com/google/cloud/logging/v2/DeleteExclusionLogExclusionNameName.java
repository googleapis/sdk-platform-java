package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusionName;
import com.google.protobuf.Empty;

public class DeleteExclusionLogExclusionNameName {

  public static void main(String[] args) throws Exception {
    deleteExclusionLogExclusionNameName();
  }

  public static void deleteExclusionLogExclusionNameName() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      LogExclusionName name = LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]");
      configClient.deleteExclusion(name);
    }
  }
}
