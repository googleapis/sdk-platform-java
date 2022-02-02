package com.google.cloud.compute.v1small.samples;

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
