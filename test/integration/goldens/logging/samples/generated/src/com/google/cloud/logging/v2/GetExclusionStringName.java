package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.LogExclusionName;

public class GetExclusionStringName {

  public static void main(String[] args) throws Exception {
    getExclusionStringName();
  }

  public static void getExclusionStringName() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      String name = LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]").toString();
      LogExclusion response = configClient.getExclusion(name);
    }
  }
}
