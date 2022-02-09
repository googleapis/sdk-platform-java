/*
 * Copyright 2021 Google LLC
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

// [START v2_logging_generated_metricsclient_create_metricssettings1]
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.logging.v2.MetricsClient;
import com.google.cloud.logging.v2.MetricsSettings;
import com.google.cloud.logging.v2.myCredentials;

public class CreateMetricsSettings1 {

  public static void main(String[] args) throws Exception {
    createMetricsSettings1();
  }

  public static void createMetricsSettings1() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    MetricsSettings metricsSettings =
        MetricsSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    MetricsClient metricsClient = MetricsClient.create(metricsSettings);
  }
}
// [END v2_logging_generated_metricsclient_create_metricssettings1]