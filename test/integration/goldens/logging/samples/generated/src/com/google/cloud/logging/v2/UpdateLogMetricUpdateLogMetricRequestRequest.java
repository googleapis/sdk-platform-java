package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.LogMetricName;
import com.google.logging.v2.UpdateLogMetricRequest;

public class UpdateLogMetricUpdateLogMetricRequestRequest {

  public static void main(String[] args) throws Exception {
    updateLogMetricUpdateLogMetricRequestRequest();
  }

  public static void updateLogMetricUpdateLogMetricRequestRequest() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      UpdateLogMetricRequest request =
          UpdateLogMetricRequest.newBuilder()
              .setMetricName(LogMetricName.of("[PROJECT]", "[METRIC]").toString())
              .setMetric(LogMetric.newBuilder().build())
              .build();
      LogMetric response = metricsClient.updateLogMetric(request);
    }
  }
}
