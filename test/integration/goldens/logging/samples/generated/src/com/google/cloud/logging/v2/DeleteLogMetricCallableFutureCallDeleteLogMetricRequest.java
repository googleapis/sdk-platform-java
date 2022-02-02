package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.DeleteLogMetricRequest;
import com.google.logging.v2.LogMetricName;
import com.google.protobuf.Empty;

public class DeleteLogMetricCallableFutureCallDeleteLogMetricRequest {

  public static void main(String[] args) throws Exception {
    deleteLogMetricCallableFutureCallDeleteLogMetricRequest();
  }

  public static void deleteLogMetricCallableFutureCallDeleteLogMetricRequest() throws Exception {
    try (MetricsClient metricsClient = MetricsClient.create()) {
      DeleteLogMetricRequest request =
          DeleteLogMetricRequest.newBuilder()
              .setMetricName(LogMetricName.of("[PROJECT]", "[METRIC]").toString())
              .build();
      ApiFuture<Empty> future = metricsClient.deleteLogMetricCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
