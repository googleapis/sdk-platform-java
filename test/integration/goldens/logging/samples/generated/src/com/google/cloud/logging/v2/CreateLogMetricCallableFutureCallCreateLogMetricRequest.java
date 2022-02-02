package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.CreateLogMetricRequest;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.ProjectName;

public class CreateLogMetricCallableFutureCallCreateLogMetricRequest {

  public static void main(String[] args) throws Exception {
    createLogMetricCallableFutureCallCreateLogMetricRequest();
  }

  public static void createLogMetricCallableFutureCallCreateLogMetricRequest() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      CreateLogMetricRequest request =
          CreateLogMetricRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setMetric(LogMetric.newBuilder().build())
              .build();
      ApiFuture<LogMetric> future = metricsClient.createLogMetricCallable().futureCall(request);
      // Do something.
      LogMetric response = future.get();
    }
  }
}
