package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.LogSinkName;
import com.google.logging.v2.UpdateSinkRequest;
import com.google.protobuf.FieldMask;

public class UpdateSinkCallableFutureCallUpdateSinkRequest {

  public static void main(String[] args) throws Exception {
    updateSinkCallableFutureCallUpdateSinkRequest();
  }

  public static void updateSinkCallableFutureCallUpdateSinkRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      UpdateSinkRequest request =
          UpdateSinkRequest.newBuilder()
              .setSinkName(LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]").toString())
              .setSink(LogSink.newBuilder().build())
              .setUniqueWriterIdentity(true)
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<LogSink> future = configClient.updateSinkCallable().futureCall(request);
      // Do something.
      LogSink response = future.get();
    }
  }
}
