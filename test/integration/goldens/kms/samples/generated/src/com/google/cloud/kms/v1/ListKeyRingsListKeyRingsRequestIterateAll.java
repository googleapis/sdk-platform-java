package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRing;
import com.google.cloud.kms.v1.ListKeyRingsRequest;
import com.google.cloud.kms.v1.LocationName;

public class ListKeyRingsListKeyRingsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listKeyRingsListKeyRingsRequestIterateAll();
  }

  public static void listKeyRingsListKeyRingsRequestIterateAll() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      ListKeyRingsRequest request =
          ListKeyRingsRequest.newBuilder()
              .setParent(LocationName.of("[PROJECT]", "[LOCATION]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .setFilter("filter-1274492040")
              .setOrderBy("orderBy-1207110587")
              .build();
      for (KeyRing element : keyManagementServiceClient.listKeyRings(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
