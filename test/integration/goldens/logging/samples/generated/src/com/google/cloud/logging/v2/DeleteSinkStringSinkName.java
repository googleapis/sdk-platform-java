package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogSinkName;
import com.google.protobuf.Empty;

public class DeleteSinkStringSinkName {

  public static void main(String[] args) throws Exception {
    deleteSinkStringSinkName();
  }

  public static void deleteSinkStringSinkName() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      String sinkName = LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]").toString();
      configClient.deleteSink(sinkName);
    }
  }
}
