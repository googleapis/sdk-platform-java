package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.LogMetricName;

public class GetLogMetricStringMetricName {

  public static void main(String[] args) throws Exception {
    getLogMetricStringMetricName();
  }

  public static void getLogMetricStringMetricName() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      String metricName = LogMetricName.of("[PROJECT]", "[METRIC]").toString();
      LogMetric response = metricsClient.getLogMetric(metricName);
    }
  }
}
