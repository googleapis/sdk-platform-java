package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.ProjectName;

public class ListLogMetricsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listLogMetricsStringIterateAll();
  }

  public static void listLogMetricsStringIterateAll() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      for (LogMetric element : metricsClient.listLogMetrics(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
