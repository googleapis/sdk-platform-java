package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.LogSinkName;
import com.google.protobuf.FieldMask;

public class UpdateSinkStringSinkNameLogSinkSinkFieldMaskUpdateMask {

  public static void main(String[] args) throws Exception {
    updateSinkStringSinkNameLogSinkSinkFieldMaskUpdateMask();
  }

  public static void updateSinkStringSinkNameLogSinkSinkFieldMaskUpdateMask() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      String sinkName = LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]").toString();
      LogSink sink = LogSink.newBuilder().build();
      FieldMask updateMask = FieldMask.newBuilder().build();
      LogSink response = configClient.updateSink(sinkName, sink, updateMask);
    }
  }
}
