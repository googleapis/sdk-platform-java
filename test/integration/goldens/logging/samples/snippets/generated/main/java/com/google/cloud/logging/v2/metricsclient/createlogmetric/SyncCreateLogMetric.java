/*
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.logging.v2.samples;

// [START logging_v2_generated_metricsclient_createlogmetric_sync]
import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.CreateLogMetricRequest;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.ProjectName;

public class SyncCreateLogMetric {

  public static void main(String[] args) throws Exception {
    syncCreateLogMetric();
  }

  public static void syncCreateLogMetric() throws Exception {
    // This snippet has been automatically generated and should be regarded as a code template only.
    // It will require modifications to work:
    // - It may require correct/in-range values for request initialization.
    // - It may require specifying regional endpoints when creating the service client as shown in
    // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
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
// [END logging_v2_generated_metricsclient_createlogmetric_sync]
