package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.GetLogMetricRequest;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.LogMetricName;

public class GetLogMetricGetLogMetricRequestRequest {

  public static void main(String[] args) throws Exception {
    getLogMetricGetLogMetricRequestRequest();
  }

  public static void getLogMetricGetLogMetricRequestRequest() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      GetLogMetricRequest request =
          GetLogMetricRequest.newBuilder()
              .setMetricName(LogMetricName.of("[PROJECT]", "[METRIC]").toString())
              .build();
      LogMetric response = metricsClient.getLogMetric(request);
    }
  }
}
