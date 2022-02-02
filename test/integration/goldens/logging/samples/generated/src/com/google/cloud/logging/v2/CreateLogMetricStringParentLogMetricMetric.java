package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.ProjectName;

public class CreateLogMetricStringParentLogMetricMetric {

  public static void main(String[] args) throws Exception {
    createLogMetricStringParentLogMetricMetric();
  }

  public static void createLogMetricStringParentLogMetricMetric() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      LogMetric metric = LogMetric.newBuilder().build();
      LogMetric response = metricsClient.createLogMetric(parent, metric);
    }
  }
}
