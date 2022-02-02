package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.LogMetricName;

public class GetLogMetricLogMetricNameMetricName {

  public static void main(String[] args) throws Exception {
    getLogMetricLogMetricNameMetricName();
  }

  public static void getLogMetricLogMetricNameMetricName() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      LogMetricName metricName = LogMetricName.of("[PROJECT]", "[METRIC]");
      LogMetric response = metricsClient.getLogMetric(metricName);
    }
  }
}
