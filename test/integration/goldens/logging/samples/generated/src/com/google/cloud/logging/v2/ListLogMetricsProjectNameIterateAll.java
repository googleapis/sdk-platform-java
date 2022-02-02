package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.ProjectName;

public class ListLogMetricsProjectNameIterateAll {

  public static void main(String[] args) throws Exception {
    listLogMetricsProjectNameIterateAll();
  }

  public static void listLogMetricsProjectNameIterateAll() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      for (LogMetric element : metricsClient.listLogMetrics(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
