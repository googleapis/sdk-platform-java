package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.AddressAggregatedList;
import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.AddressesScopedList;
import com.google.cloud.compute.v1small.AggregatedListAddressesRequest;
import com.google.common.base.Strings;
import java.util.Map;

public class AggregatedListCallableCallAggregatedListAddressesRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    aggregatedListCallableCallAggregatedListAddressesRequestSetPageToken();
  }

  public static void aggregatedListCallableCallAggregatedListAddressesRequestSetPageToken()
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
      while (true) {
        AddressAggregatedList response = addressesClient.aggregatedListCallable().call(request);
        for (Map.Entry<String, AddressesScopedList> element : response.getResponsesList()) {
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
