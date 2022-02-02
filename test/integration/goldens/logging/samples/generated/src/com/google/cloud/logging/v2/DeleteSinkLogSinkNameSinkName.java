package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogSinkName;
import com.google.protobuf.Empty;

public class DeleteSinkLogSinkNameSinkName {

  public static void main(String[] args) throws Exception {
    deleteSinkLogSinkNameSinkName();
  }

  public static void deleteSinkLogSinkNameSinkName() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      LogSinkName sinkName = LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]");
      configClient.deleteSink(sinkName);
    }
  }
}
