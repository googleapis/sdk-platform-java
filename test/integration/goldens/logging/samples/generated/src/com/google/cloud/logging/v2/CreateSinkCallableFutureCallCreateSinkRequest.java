package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.CreateSinkRequest;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.ProjectName;

public class CreateSinkCallableFutureCallCreateSinkRequest {

  public static void main(String[] args) throws Exception {
    createSinkCallableFutureCallCreateSinkRequest();
  }

  public static void createSinkCallableFutureCallCreateSinkRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      CreateSinkRequest request =
          CreateSinkRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setSink(LogSink.newBuilder().build())
              .setUniqueWriterIdentity(true)
              .build();
      ApiFuture<LogSink> future = configClient.createSinkCallable().futureCall(request);
      // Do something.
      LogSink response = future.get();
    }
  }
}
