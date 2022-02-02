package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.LogMetricName;

public class UpdateLogMetricLogMetricNameMetricNameLogMetricMetric {

  public static void main(String[] args) throws Exception {
    updateLogMetricLogMetricNameMetricNameLogMetricMetric();
  }

  public static void updateLogMetricLogMetricNameMetricNameLogMetricMetric() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      LogMetricName metricName = LogMetricName.of("[PROJECT]", "[METRIC]");
      LogMetric metric = LogMetric.newBuilder().build();
      LogMetric response = metricsClient.updateLogMetric(metricName, metric);
    }
  }
}
