package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.BillingAccountName;
import com.google.logging.v2.LogSink;

public class ListSinksBillingAccountNameIterateAll {

  public static void main(String[] args) throws Exception {
    listSinksBillingAccountNameIterateAll();
  }

  public static void listSinksBillingAccountNameIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      BillingAccountName parent = BillingAccountName.of("[BILLING_ACCOUNT]");
      for (LogSink element : configClient.listSinks(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
