package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.Address;
import com.google.cloud.compute.v1small.AddressList;
import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.ListAddressesRequest;
import com.google.common.base.Strings;

public class ListCallableCallListAddressesRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listCallableCallListAddressesRequestSetPageToken();
  }

  public static void listCallableCallListAddressesRequestSetPageToken() throws Exception {
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
      while (true) {
        AddressList response = addressesClient.listCallable().call(request);
        for (Address element : response.getResponsesList()) {
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
