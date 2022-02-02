package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.ProjectName;

public class CreateLogMetricProjectNameParentLogMetricMetric {

  public static void main(String[] args) throws Exception {
    createLogMetricProjectNameParentLogMetricMetric();
  }

  public static void createLogMetricProjectNameParentLogMetricMetric() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      LogMetric metric = LogMetric.newBuilder().build();
      LogMetric response = metricsClient.createLogMetric(parent, metric);
    }
  }
}
