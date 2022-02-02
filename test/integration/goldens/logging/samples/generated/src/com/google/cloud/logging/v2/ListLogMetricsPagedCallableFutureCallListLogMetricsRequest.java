package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.ListLogMetricsRequest;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.ProjectName;

public class ListLogMetricsPagedCallableFutureCallListLogMetricsRequest {

  public static void main(String[] args) throws Exception {
    listLogMetricsPagedCallableFutureCallListLogMetricsRequest();
  }

  public static void listLogMetricsPagedCallableFutureCallListLogMetricsRequest() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      ListLogMetricsRequest request =
          ListLogMetricsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      ApiFuture<LogMetric> future = metricsClient.listLogMetricsPagedCallable().futureCall(request);
      // Do something.
      for (LogMetric element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
