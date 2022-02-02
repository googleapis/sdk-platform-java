package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.CreateSinkRequest;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.ProjectName;

public class CreateSinkCreateSinkRequestRequest {

  public static void main(String[] args) throws Exception {
    createSinkCreateSinkRequestRequest();
  }

  public static void createSinkCreateSinkRequestRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      CreateSinkRequest request =
          CreateSinkRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setSink(LogSink.newBuilder().build())
              .setUniqueWriterIdentity(true)
              .build();
      LogSink response = configClient.createSink(request);
    }
  }
}
