package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.OrganizationName;

public class CreateSinkOrganizationNameParentLogSinkSink {

  public static void main(String[] args) throws Exception {
    createSinkOrganizationNameParentLogSinkSink();
  }

  public static void createSinkOrganizationNameParentLogSinkSink() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      OrganizationName parent = OrganizationName.of("[ORGANIZATION]");
      LogSink sink = LogSink.newBuilder().build();
      LogSink response = configClient.createSink(parent, sink);
    }
  }
}
