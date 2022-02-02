package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.BillingAccountLocationName;
import com.google.logging.v2.LogBucket;

public class ListBucketsBillingAccountLocationNameIterateAll {

  public static void main(String[] args) throws Exception {
    listBucketsBillingAccountLocationNameIterateAll();
  }

  public static void listBucketsBillingAccountLocationNameIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      BillingAccountLocationName parent =
          BillingAccountLocationName.of("[BILLING_ACCOUNT]", "[LOCATION]");
      for (LogBucket element : configClient.listBuckets(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
