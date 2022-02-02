package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.LogSinkName;

public class GetSinkStringSinkName {

  public static void main(String[] args) throws Exception {
    getSinkStringSinkName();
  }

  public static void getSinkStringSinkName() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      String sinkName = LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]").toString();
      LogSink response = configClient.getSink(sinkName);
    }
  }
}
