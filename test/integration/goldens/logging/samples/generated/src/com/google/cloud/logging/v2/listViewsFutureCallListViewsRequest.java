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

// [START 10_10_generated_configClient_listViews_futureCallListViewsRequest]
import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.ListViewsRequest;
import com.google.logging.v2.LogView;

public class ListViewsFutureCallListViewsRequest {

  public static void main(String[] args) throws Exception {
    listViewsFutureCallListViewsRequest();
  }

  public static void listViewsFutureCallListViewsRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (ConfigClient configClient = ConfigClient.create()) {
      ListViewsRequest request =
          ListViewsRequest.newBuilder()
              .setParent("parent-995424086")
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      ApiFuture<LogView> future = configClient.listViewsPagedCallable().futureCall(request);
      // Do something.
      for (LogView element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
// [END 10_10_generated_configClient_listViews_futureCallListViewsRequest]