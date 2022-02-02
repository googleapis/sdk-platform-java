package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.CreateLogMetricRequest;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.ProjectName;

public class CreateLogMetricCreateLogMetricRequestRequest {

  public static void main(String[] args) throws Exception {
    createLogMetricCreateLogMetricRequestRequest();
  }

  public static void createLogMetricCreateLogMetricRequestRequest() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      CreateLogMetricRequest request =
          CreateLogMetricRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setMetric(LogMetric.newBuilder().build())
              .build();
      LogMetric response = metricsClient.createLogMetric(request);
    }
  }
}
