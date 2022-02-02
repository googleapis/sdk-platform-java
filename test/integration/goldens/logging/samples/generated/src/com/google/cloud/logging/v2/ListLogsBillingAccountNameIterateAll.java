package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.BillingAccountName;

public class ListLogsBillingAccountNameIterateAll {

  public static void main(String[] args) throws Exception {
    listLogsBillingAccountNameIterateAll();
  }

  public static void listLogsBillingAccountNameIterateAll() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      BillingAccountName parent = BillingAccountName.of("[BILLING_ACCOUNT]");
      for (String element : loggingClient.listLogs(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
