package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.BillingAccountName;
import com.google.logging.v2.LogSink;

public class CreateSinkBillingAccountNameParentLogSinkSink {

  public static void main(String[] args) throws Exception {
    createSinkBillingAccountNameParentLogSinkSink();
  }

  public static void createSinkBillingAccountNameParentLogSinkSink() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      BillingAccountName parent = BillingAccountName.of("[BILLING_ACCOUNT]");
      LogSink sink = LogSink.newBuilder().build();
      LogSink response = configClient.createSink(parent, sink);
    }
  }
}
