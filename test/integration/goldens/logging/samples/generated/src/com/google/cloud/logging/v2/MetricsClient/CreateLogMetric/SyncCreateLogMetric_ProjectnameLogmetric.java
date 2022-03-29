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

// [START logging_v2_generated_metricsclient_createlogmetric_projectnamelogmetric_sync]
import com.google.cloud.logging.v2.MetricsClient;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.ProjectName;

public class SyncCreateLogMetricProjectnameLogmetric {

  public static void main(String[] args) throws Exception {
    syncCreateLogMetricProjectnameLogmetric();
  }

  public static void syncCreateLogMetricProjectnameLogmetric() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (MetricsClient metricsClient = MetricsClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      LogMetric metric = LogMetric.newBuilder().build();
      LogMetric response = metricsClient.createLogMetric(parent, metric);
    }
  }
}
// [END logging_v2_generated_metricsclient_createlogmetric_projectnamelogmetric_sync]
