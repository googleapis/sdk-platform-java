package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.BillingAccountName;
import com.google.logging.v2.LogExclusion;

public class CreateExclusionBillingAccountNameParentLogExclusionExclusion {

  public static void main(String[] args) throws Exception {
    createExclusionBillingAccountNameParentLogExclusionExclusion();
  }

  public static void createExclusionBillingAccountNameParentLogExclusionExclusion()
      throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      BillingAccountName parent = BillingAccountName.of("[BILLING_ACCOUNT]");
      LogExclusion exclusion = LogExclusion.newBuilder().build();
      LogExclusion response = configClient.createExclusion(parent, exclusion);
    }
  }
}
