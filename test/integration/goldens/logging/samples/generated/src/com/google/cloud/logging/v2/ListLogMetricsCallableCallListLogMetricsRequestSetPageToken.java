package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.common.base.Strings;
import com.google.logging.v2.ListLogMetricsRequest;
import com.google.logging.v2.ListLogMetricsResponse;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.ProjectName;

public class ListLogMetricsCallableCallListLogMetricsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listLogMetricsCallableCallListLogMetricsRequestSetPageToken();
  }

  public static void listLogMetricsCallableCallListLogMetricsRequestSetPageToken()
      throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      ListLogMetricsRequest request =
          ListLogMetricsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      while (true) {
        ListLogMetricsResponse response = metricsClient.listLogMetricsCallable().call(request);
        for (LogMetric element : response.getResponsesList()) {
          // doThingsWith(element);
        }
        String nextPageToken = response.getNextPageToken();
        if (!Strings.isNullOrEmpty(nextPageToken)) {
          request = request.toBuilder().setPageToken(nextPageToken).build();
        } else {
          break;
        }
      }
    }
  }
}
