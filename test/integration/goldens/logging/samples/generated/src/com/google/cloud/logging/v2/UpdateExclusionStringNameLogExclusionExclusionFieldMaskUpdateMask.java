package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.LogExclusionName;
import com.google.protobuf.FieldMask;

public class UpdateExclusionStringNameLogExclusionExclusionFieldMaskUpdateMask {

  public static void main(String[] args) throws Exception {
    updateExclusionStringNameLogExclusionExclusionFieldMaskUpdateMask();
  }

  public static void updateExclusionStringNameLogExclusionExclusionFieldMaskUpdateMask()
      throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      String name = LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]").toString();
      LogExclusion exclusion = LogExclusion.newBuilder().build();
      FieldMask updateMask = FieldMask.newBuilder().build();
      LogExclusion response = configClient.updateExclusion(name, exclusion, updateMask);
    }
  }
}
