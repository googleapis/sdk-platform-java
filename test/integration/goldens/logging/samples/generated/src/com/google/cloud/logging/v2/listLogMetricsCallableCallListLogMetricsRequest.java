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

// [START logging_v2_generated_metricsclient_listlogmetrics_callablecalllistlogmetricsrequest]
import com.google.cloud.logging.v2.MetricsClient;
import com.google.common.base.Strings;
import com.google.logging.v2.ListLogMetricsRequest;
import com.google.logging.v2.ListLogMetricsResponse;
import com.google.logging.v2.LogMetric;
import com.google.logging.v2.ProjectName;

public class ListLogMetricsCallableCallListLogMetricsRequest {

  public static void main(String[] args) throws Exception {
    listLogMetricsCallableCallListLogMetricsRequest();
  }

  public static void listLogMetricsCallableCallListLogMetricsRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (MetricsClient metricsClient = MetricsClient.create()) {
      ListLogMetricsRequest request =
          ListLogMetricsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      while (true) {
        ListLogMetricsResponse response = metricsClient.listLogMetricsCallable().call(request);
        for (LogMetric element : response.getResponsesList()) {
          // doThingsWith(element);
        }
        String nextPageToken = response.getNextPageToken();
        if (!Strings.isNullOrEmpty(nextPageToken)) {
          request = request.toBuilder().setPageToken(nextPageToken).build();
        } else {
          break;
        }
      }
    }
  }
}
// [END logging_v2_generated_metricsclient_listlogmetrics_callablecalllistlogmetricsrequest]