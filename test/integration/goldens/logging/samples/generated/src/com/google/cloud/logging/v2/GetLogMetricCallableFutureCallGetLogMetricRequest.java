package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.GetLogMetricRequest;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.LogMetricName;

public class GetLogMetricCallableFutureCallGetLogMetricRequest {

  public static void main(String[] args) throws Exception {
    getLogMetricCallableFutureCallGetLogMetricRequest();
  }

  public static void getLogMetricCallableFutureCallGetLogMetricRequest() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      GetLogMetricRequest request =
          GetLogMetricRequest.newBuilder()
              .setMetricName(LogMetricName.of("[PROJECT]", "[METRIC]").toString())
              .build();
      ApiFuture<LogMetric> future = metricsClient.getLogMetricCallable().futureCall(request);
      // Do something.
      LogMetric response = future.get();
    }
  }
}
