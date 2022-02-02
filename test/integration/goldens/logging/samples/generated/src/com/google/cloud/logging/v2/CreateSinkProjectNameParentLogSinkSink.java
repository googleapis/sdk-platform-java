package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.ProjectName;

public class CreateSinkProjectNameParentLogSinkSink {

  public static void main(String[] args) throws Exception {
    createSinkProjectNameParentLogSinkSink();
  }

  public static void createSinkProjectNameParentLogSinkSink() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      LogSink sink = LogSink.newBuilder().build();
      LogSink response = configClient.createSink(parent, sink);
    }
  }
}
