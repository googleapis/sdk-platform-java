package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.LogSinkName;

public class UpdateSinkLogSinkNameSinkNameLogSinkSink {

  public static void main(String[] args) throws Exception {
    updateSinkLogSinkNameSinkNameLogSinkSink();
  }

  public static void updateSinkLogSinkNameSinkNameLogSinkSink() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      LogSinkName sinkName = LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]");
      LogSink sink = LogSink.newBuilder().build();
      LogSink response = configClient.updateSink(sinkName, sink);
    }
  }
}
