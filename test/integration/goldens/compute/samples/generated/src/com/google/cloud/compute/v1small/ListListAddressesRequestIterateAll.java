package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.Address;
import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.ListAddressesRequest;

public class ListListAddressesRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listListAddressesRequestIterateAll();
  }

  public static void listListAddressesRequestIterateAll() throws Exception {
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
      for (Address element : addressesClient.list(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
