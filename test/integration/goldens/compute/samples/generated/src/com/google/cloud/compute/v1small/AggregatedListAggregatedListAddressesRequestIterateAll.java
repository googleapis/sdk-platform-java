package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.AddressesScopedList;
import com.google.cloud.compute.v1small.AggregatedListAddressesRequest;
import java.util.Map;

public class AggregatedListAggregatedListAddressesRequestIterateAll {

  public static void main(String[] args) throws Exception {
    aggregatedListAggregatedListAddressesRequestIterateAll();
  }

  public static void aggregatedListAggregatedListAddressesRequestIterateAll() throws Exception {
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
