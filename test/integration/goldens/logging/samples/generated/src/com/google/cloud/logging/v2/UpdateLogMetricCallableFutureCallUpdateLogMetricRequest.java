package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.LogMetricName;
import com.google.logging.v2.UpdateLogMetricRequest;

public class UpdateLogMetricCallableFutureCallUpdateLogMetricRequest {

  public static void main(String[] args) throws Exception {
    updateLogMetricCallableFutureCallUpdateLogMetricRequest();
  }

  public static void updateLogMetricCallableFutureCallUpdateLogMetricRequest() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      UpdateLogMetricRequest request =
          UpdateLogMetricRequest.newBuilder()
              .setMetricName(LogMetricName.of("[PROJECT]", "[METRIC]").toString())
              .setMetric(LogMetric.newBuilder().build())
              .build();
      ApiFuture<LogMetric> future = metricsClient.updateLogMetricCallable().futureCall(request);
      // Do something.
      LogMetric response = future.get();
    }
  }
}
