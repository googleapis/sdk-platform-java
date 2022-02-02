package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRing;
import com.google.cloud.kms.v1.ListKeyRingsRequest;
import com.google.cloud.kms.v1.ListKeyRingsResponse;
import com.google.cloud.kms.v1.LocationName;
import com.google.common.base.Strings;

public class ListKeyRingsCallableCallListKeyRingsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listKeyRingsCallableCallListKeyRingsRequestSetPageToken();
  }

  public static void listKeyRingsCallableCallListKeyRingsRequestSetPageToken() throws Exception {
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
      while (true) {
        ListKeyRingsResponse response =
            keyManagementServiceClient.listKeyRingsCallable().call(request);
        for (KeyRing element : response.getResponsesList()) {
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
