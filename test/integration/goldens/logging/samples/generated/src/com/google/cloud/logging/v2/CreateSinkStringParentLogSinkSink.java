package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.ProjectName;

public class CreateSinkStringParentLogSinkSink {

  public static void main(String[] args) throws Exception {
    createSinkStringParentLogSinkSink();
  }

  public static void createSinkStringParentLogSinkSink() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      LogSink sink = LogSink.newBuilder().build();
      LogSink response = configClient.createSink(parent, sink);
    }
  }
}
