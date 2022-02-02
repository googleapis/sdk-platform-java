package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.LogMetricName;
import com.google.protobuf.Empty;

public class DeleteLogMetricStringMetricName {

  public static void main(String[] args) throws Exception {
    deleteLogMetricStringMetricName();
  }

  public static void deleteLogMetricStringMetricName() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      String metricName = LogMetricName.of("[PROJECT]", "[METRIC]").toString();
      metricsClient.deleteLogMetric(metricName);
    }
  }
}
