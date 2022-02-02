package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.GetSinkRequest;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.LogSinkName;

public class GetSinkGetSinkRequestRequest {

  public static void main(String[] args) throws Exception {
    getSinkGetSinkRequestRequest();
  }

  public static void getSinkGetSinkRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      GetSinkRequest request =
          GetSinkRequest.newBuilder()
              .setSinkName(LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]").toString())
              .build();
      LogSink response = configClient.getSink(request);
    }
  }
}
