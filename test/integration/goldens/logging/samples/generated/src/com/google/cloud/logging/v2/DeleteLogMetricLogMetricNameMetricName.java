package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.LogMetricName;
import com.google.protobuf.Empty;

public class DeleteLogMetricLogMetricNameMetricName {

  public static void main(String[] args) throws Exception {
    deleteLogMetricLogMetricNameMetricName();
  }

  public static void deleteLogMetricLogMetricNameMetricName() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      LogMetricName metricName = LogMetricName.of("[PROJECT]", "[METRIC]");
      metricsClient.deleteLogMetric(metricName);
    }
  }
}
