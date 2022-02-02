package com.google.cloud.logging.v2.stub.samples;

import com.google.cloud.logging.v2.stub.LoggingServiceV2StubSettings;
import java.time.Duration;

public class LoggingServiceV2StubSettingsDeleteLog {

  public static void main(String[] args) throws Exception {
    loggingServiceV2StubSettingsDeleteLog();
  }

  public static void loggingServiceV2StubSettingsDeleteLog() throws Exception {
    LoggingServiceV2StubSettings.Builder loggingSettingsBuilder =
        LoggingServiceV2StubSettings.newBuilder();
    loggingSettingsBuilder
        .deleteLogSettings()
        .setRetrySettings(
            loggingSettingsBuilder
                .deleteLogSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    LoggingServiceV2StubSettings loggingSettings = loggingSettingsBuilder.build();
  }
}
