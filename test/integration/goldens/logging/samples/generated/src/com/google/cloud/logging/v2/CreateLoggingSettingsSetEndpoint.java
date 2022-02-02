package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.LoggingClient;
import com.google.cloud.logging.v2.LoggingSettings;
import com.google.cloud.logging.v2.myEndpoint;

public class CreateLoggingSettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createLoggingSettingsSetEndpoint();
  }

  public static void createLoggingSettingsSetEndpoint() throws Exception {
    LoggingSettings loggingSettings = LoggingSettings.newBuilder().setEndpoint(myEndpoint).build();
    LoggingClient loggingClient = LoggingClient.create(loggingSettings);
  }
}
