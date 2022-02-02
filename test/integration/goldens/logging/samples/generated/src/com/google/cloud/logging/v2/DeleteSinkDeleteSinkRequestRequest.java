package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.DeleteSinkRequest;
import com.google.logging.v2.LogSinkName;
import com.google.protobuf.Empty;

public class DeleteSinkDeleteSinkRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteSinkDeleteSinkRequestRequest();
  }

  public static void deleteSinkDeleteSinkRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      DeleteSinkRequest request =
          DeleteSinkRequest.newBuilder()
              .setSinkName(LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]").toString())
              .build();
      configClient.deleteSink(request);
    }
  }
}
