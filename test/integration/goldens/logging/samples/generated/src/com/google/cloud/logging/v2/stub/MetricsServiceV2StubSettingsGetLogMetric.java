package com.google.cloud.logging.v2.stub.samples;

import com.google.cloud.logging.v2.stub.MetricsServiceV2StubSettings;
import java.time.Duration;

public class MetricsServiceV2StubSettingsGetLogMetric {

  public static void main(String[] args) throws Exception {
    metricsServiceV2StubSettingsGetLogMetric();
  }

  public static void metricsServiceV2StubSettingsGetLogMetric() throws Exception {
    MetricsServiceV2StubSettings.Builder metricsSettingsBuilder =
        MetricsServiceV2StubSettings.newBuilder();
    metricsSettingsBuilder
        .getLogMetricSettings()
        .setRetrySettings(
            metricsSettingsBuilder
                .getLogMetricSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    MetricsServiceV2StubSettings metricsSettings = metricsSettingsBuilder.build();
  }
}
