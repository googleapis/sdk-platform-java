package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.location.ListLocationsRequest;
import com.google.cloud.location.Location;

public class ListLocationsPagedCallableFutureCallListLocationsRequest {

  public static void main(String[] args) throws Exception {
    listLocationsPagedCallableFutureCallListLocationsRequest();
  }

  public static void listLocationsPagedCallableFutureCallListLocationsRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      ListLocationsRequest request =
          ListLocationsRequest.newBuilder()
              .setName("name3373707")
              .setFilter("filter-1274492040")
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      ApiFuture<Location> future =
          keyManagementServiceClient.listLocationsPagedCallable().futureCall(request);
      // Do something.
      for (Location element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
