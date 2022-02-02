package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.ListLogMetricsRequest;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.ProjectName;

public class ListLogMetricsListLogMetricsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listLogMetricsListLogMetricsRequestIterateAll();
  }

  public static void listLogMetricsListLogMetricsRequestIterateAll() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      ListLogMetricsRequest request =
          ListLogMetricsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      for (LogMetric element : metricsClient.listLogMetrics(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
