package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.LoggingSettings;
import java.time.Duration;

public class LoggingSettingsDeleteLog {

  public static void main(String[] args) throws Exception {
    loggingSettingsDeleteLog();
  }

  public static void loggingSettingsDeleteLog() throws Exception {
    LoggingSettings.Builder loggingSettingsBuilder = LoggingSettings.newBuilder();
    loggingSettingsBuilder
        .deleteLogSettings()
        .setRetrySettings(
            loggingSettingsBuilder
                .deleteLogSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    LoggingSettings loggingSettings = loggingSettingsBuilder.build();
  }
}
