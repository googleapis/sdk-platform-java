package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.LogExclusionName;

public class GetExclusionLogExclusionNameName {

  public static void main(String[] args) throws Exception {
    getExclusionLogExclusionNameName();
  }

  public static void getExclusionLogExclusionNameName() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      LogExclusionName name = LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]");
      LogExclusion response = configClient.getExclusion(name);
    }
  }
}
