package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.location.ListLocationsRequest;
import com.google.cloud.location.ListLocationsResponse;
import com.google.cloud.location.Location;
import com.google.common.base.Strings;

public class ListLocationsCallableCallListLocationsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listLocationsCallableCallListLocationsRequestSetPageToken();
  }

  public static void listLocationsCallableCallListLocationsRequestSetPageToken() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      ListLocationsRequest request =
          ListLocationsRequest.newBuilder()
              .setName("name3373707")
              .setFilter("filter-1274492040")
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      while (true) {
        ListLocationsResponse response =
            keyManagementServiceClient.listLocationsCallable().call(request);
        for (Location element : response.getResponsesList()) {
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
