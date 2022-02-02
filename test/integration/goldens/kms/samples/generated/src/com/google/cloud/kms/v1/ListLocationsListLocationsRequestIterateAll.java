package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.location.ListLocationsRequest;
import com.google.cloud.location.Location;

public class ListLocationsListLocationsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listLocationsListLocationsRequestIterateAll();
  }

  public static void listLocationsListLocationsRequestIterateAll() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      ListLocationsRequest request =
          ListLocationsRequest.newBuilder()
              .setName("name3373707")
              .setFilter("filter-1274492040")
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      for (Location element : keyManagementServiceClient.listLocations(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
