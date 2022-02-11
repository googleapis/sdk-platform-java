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

// [START logging_v2_generated_configclient_listbuckets_pagedcallablefuturecalllistbucketsrequest]
import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.ListBucketsRequest;
import com.google.logging.v2.LocationName;
import com.google.logging.v2.LogBucket;

public class ListBucketsPagedCallableFutureCallListBucketsRequest {

  public static void main(String[] args) throws Exception {
    listBucketsPagedCallableFutureCallListBucketsRequest();
  }

  public static void listBucketsPagedCallableFutureCallListBucketsRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (ConfigClient configClient = ConfigClient.create()) {
      ListBucketsRequest request =
          ListBucketsRequest.newBuilder()
              .setParent(LocationName.of("[PROJECT]", "[LOCATION]").toString())
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      ApiFuture<LogBucket> future = configClient.listBucketsPagedCallable().futureCall(request);
      // Do something.
      for (LogBucket element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
// [END logging_v2_generated_configclient_listbuckets_pagedcallablefuturecalllistbucketsrequest]