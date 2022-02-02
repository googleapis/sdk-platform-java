package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsSettings;
import java.time.Duration;

public class MetricsSettingsGetLogMetric {

  public static void main(String[] args) throws Exception {
    metricsSettingsGetLogMetric();
  }

  public static void metricsSettingsGetLogMetric() throws Exception {
    MetricsSettings.Builder metricsSettingsBuilder = MetricsSettings.newBuilder();
    metricsSettingsBuilder
        .getLogMetricSettings()
        .setRetrySettings(
            metricsSettingsBuilder
                .getLogMetricSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    MetricsSettings metricsSettings = metricsSettingsBuilder.build();
  }
}
