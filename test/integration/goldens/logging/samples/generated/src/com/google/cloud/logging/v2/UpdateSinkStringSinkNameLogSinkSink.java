package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.LogSinkName;

public class UpdateSinkStringSinkNameLogSinkSink {

  public static void main(String[] args) throws Exception {
    updateSinkStringSinkNameLogSinkSink();
  }

  public static void updateSinkStringSinkNameLogSinkSink() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      String sinkName = LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]").toString();
      LogSink sink = LogSink.newBuilder().build();
      LogSink response = configClient.updateSink(sinkName, sink);
    }
  }
}
