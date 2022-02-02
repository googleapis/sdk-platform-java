package com.google.cloud.compute.v1small.samples;

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
