package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.LogExclusionName;
import com.google.protobuf.FieldMask;

public class UpdateExclusionLogExclusionNameNameLogExclusionExclusionFieldMaskUpdateMask {

  public static void main(String[] args) throws Exception {
    updateExclusionLogExclusionNameNameLogExclusionExclusionFieldMaskUpdateMask();
  }

  public static void updateExclusionLogExclusionNameNameLogExclusionExclusionFieldMaskUpdateMask()
      throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      LogExclusionName name = LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]");
      LogExclusion exclusion = LogExclusion.newBuilder().build();
      FieldMask updateMask = FieldMask.newBuilder().build();
      LogExclusion response = configClient.updateExclusion(name, exclusion, updateMask);
    }
  }
}
