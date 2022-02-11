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
package com.google.cloud.compute.v1small.samples;

// [START 1.0_10_generated_addressesclient_aggregatedlist_pagedcallablefuturecallaggregatedlistaddressesrequest]
import com.google.api.core.ApiFuture;
import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.AddressesScopedList;
import com.google.cloud.compute.v1small.AggregatedListAddressesRequest;
import java.util.Map;

public class AggregatedListPagedCallableFutureCallAggregatedListAddressesRequest {

  public static void main(String[] args) throws Exception {
    aggregatedListPagedCallableFutureCallAggregatedListAddressesRequest();
  }

  public static void aggregatedListPagedCallableFutureCallAggregatedListAddressesRequest()
      throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (AddressesClient addressesClient = AddressesClient.create()) {
      AggregatedListAddressesRequest request =
          AggregatedListAddressesRequest.newBuilder()
              .setFilter("filter-1274492040")
              .setIncludeAllScopes(true)
              .setMaxResults(1128457243)
              .setOrderBy("orderBy-1207110587")
              .setPageToken("pageToken873572522")
              .setProject("project-309310695")
              .build();
      ApiFuture<Map.Entry<String, AddressesScopedList>> future =
          addressesClient.aggregatedListPagedCallable().futureCall(request);
      // Do something.
      for (Map.Entry<String, AddressesScopedList> element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
// [END 1.0_10_generated_addressesclient_aggregatedlist_pagedcallablefuturecallaggregatedlistaddressesrequest]