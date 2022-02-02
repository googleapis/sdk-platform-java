package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;
import com.google.cloud.kms.v1.ListCryptoKeysRequest;

public class ListCryptoKeysPagedCallableFutureCallListCryptoKeysRequest {

  public static void main(String[] args) throws Exception {
    listCryptoKeysPagedCallableFutureCallListCryptoKeysRequest();
  }

  public static void listCryptoKeysPagedCallableFutureCallListCryptoKeysRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      ListCryptoKeysRequest request =
          ListCryptoKeysRequest.newBuilder()
              .setParent(KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .setFilter("filter-1274492040")
              .setOrderBy("orderBy-1207110587")
              .build();
      ApiFuture<CryptoKey> future =
          keyManagementServiceClient.listCryptoKeysPagedCallable().futureCall(request);
      // Do something.
      for (CryptoKey element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
