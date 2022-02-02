package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.BillingAccountName;
import com.google.logging.v2.LogExclusion;

public class ListExclusionsBillingAccountNameIterateAll {

  public static void main(String[] args) throws Exception {
    listExclusionsBillingAccountNameIterateAll();
  }

  public static void listExclusionsBillingAccountNameIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      BillingAccountName parent = BillingAccountName.of("[BILLING_ACCOUNT]");
      for (LogExclusion element : configClient.listExclusions(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
