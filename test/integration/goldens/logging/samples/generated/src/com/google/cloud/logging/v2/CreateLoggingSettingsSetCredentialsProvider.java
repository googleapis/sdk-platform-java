package com.google.cloud.logging.v2.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.logging.v2.LoggingClient;
import com.google.cloud.logging.v2.LoggingSettings;
import com.google.cloud.logging.v2.myCredentials;

public class CreateLoggingSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createLoggingSettingsSetCredentialsProvider();
  }

  public static void createLoggingSettingsSetCredentialsProvider() throws Exception {
    LoggingSettings loggingSettings =
        LoggingSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    LoggingClient loggingClient = LoggingClient.create(loggingSettings);
  }
}
