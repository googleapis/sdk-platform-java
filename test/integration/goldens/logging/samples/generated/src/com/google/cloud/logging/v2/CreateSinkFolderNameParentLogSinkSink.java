package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.FolderName;
import com.google.logging.v2.LogSink;

public class CreateSinkFolderNameParentLogSinkSink {

  public static void main(String[] args) throws Exception {
    createSinkFolderNameParentLogSinkSink();
  }

  public static void createSinkFolderNameParentLogSinkSink() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      FolderName parent = FolderName.of("[FOLDER]");
      LogSink sink = LogSink.newBuilder().build();
      LogSink response = configClient.createSink(parent, sink);
    }
  }
}
