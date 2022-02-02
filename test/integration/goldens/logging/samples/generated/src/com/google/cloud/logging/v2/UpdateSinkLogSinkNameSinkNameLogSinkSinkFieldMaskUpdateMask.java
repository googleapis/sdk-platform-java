package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.LogSinkName;
import com.google.protobuf.FieldMask;

public class UpdateSinkLogSinkNameSinkNameLogSinkSinkFieldMaskUpdateMask {

  public static void main(String[] args) throws Exception {
    updateSinkLogSinkNameSinkNameLogSinkSinkFieldMaskUpdateMask();
  }

  public static void updateSinkLogSinkNameSinkNameLogSinkSinkFieldMaskUpdateMask()
      throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      LogSinkName sinkName = LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]");
      LogSink sink = LogSink.newBuilder().build();
      FieldMask updateMask = FieldMask.newBuilder().build();
      LogSink response = configClient.updateSink(sinkName, sink, updateMask);
    }
  }
}
