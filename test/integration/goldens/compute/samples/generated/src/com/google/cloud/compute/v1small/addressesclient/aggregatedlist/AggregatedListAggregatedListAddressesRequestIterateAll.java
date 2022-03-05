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

// [START compute_v1small_generated_addressesclient_aggregatedlist_aggregatedlistaddressesrequestiterateall]
import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.AddressesScopedList;
import com.google.cloud.compute.v1small.AggregatedListAddressesRequest;
import java.util.Map;

public class AggregatedListAggregatedListAddressesRequestIterateAll {

  public static void main(String[] args) throws Exception {
    aggregatedListAggregatedListAddressesRequestIterateAll();
  }

  public static void aggregatedListAggregatedListAddressesRequestIterateAll() throws Exception {
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
      for (Map.Entry<String, AddressesScopedList> element :
          addressesClient.aggregatedList(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
// [END compute_v1small_generated_addressesclient_aggregatedlist_aggregatedlistaddressesrequestiterateall]
