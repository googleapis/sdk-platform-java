package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.DeleteLogMetricRequest;
import com.google.logging.v2.LogMetricName;
import com.google.protobuf.Empty;

public class DeleteLogMetricDeleteLogMetricRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteLogMetricDeleteLogMetricRequestRequest();
  }

  public static void deleteLogMetricDeleteLogMetricRequestRequest() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      DeleteLogMetricRequest request =
          DeleteLogMetricRequest.newBuilder()
              .setMetricName(LogMetricName.of("[PROJECT]", "[METRIC]").toString())
              .build();
      metricsClient.deleteLogMetric(request);
    }
  }
}
