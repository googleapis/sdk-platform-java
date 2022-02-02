package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.LogMetricName;

public class UpdateLogMetricStringMetricNameLogMetricMetric {

  public static void main(String[] args) throws Exception {
    updateLogMetricStringMetricNameLogMetricMetric();
  }

  public static void updateLogMetricStringMetricNameLogMetricMetric() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      String metricName = LogMetricName.of("[PROJECT]", "[METRIC]").toString();
      LogMetric metric = LogMetric.newBuilder().build();
      LogMetric response = metricsClient.updateLogMetric(metricName, metric);
    }
  }
}
