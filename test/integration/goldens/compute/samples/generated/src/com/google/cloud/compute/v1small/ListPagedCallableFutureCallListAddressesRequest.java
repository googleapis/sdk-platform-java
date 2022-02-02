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

// [START REGION TAG]
import com.google.api.core.ApiFuture;
import com.google.cloud.compute.v1small.Address;
import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.ListAddressesRequest;

public class ListPagedCallableFutureCallListAddressesRequest {

  public static void main(String[] args) throws Exception {
    listPagedCallableFutureCallListAddressesRequest();
  }

  public static void listPagedCallableFutureCallListAddressesRequest() throws Exception {
    try (AddressesClient addressesClient = AddressesClient.create()) {
      ListAddressesRequest request =
          ListAddressesRequest.newBuilder()
              .setFilter("filter-1274492040")
              .setMaxResults(1128457243)
              .setOrderBy("orderBy-1207110587")
              .setPageToken("pageToken873572522")
              .setProject("project-309310695")
              .setRegion("region-934795532")
              .build();
      ApiFuture<Address> future = addressesClient.listPagedCallable().futureCall(request);
      // Do something.
      for (Address element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
// [END REGION TAG]