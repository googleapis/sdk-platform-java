package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.ListCryptoKeyVersionsRequest;

public class ListCryptoKeyVersionsPagedCallableFutureCallListCryptoKeyVersionsRequest {

  public static void main(String[] args) throws Exception {
    listCryptoKeyVersionsPagedCallableFutureCallListCryptoKeyVersionsRequest();
  }

  public static void listCryptoKeyVersionsPagedCallableFutureCallListCryptoKeyVersionsRequest()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      ListCryptoKeyVersionsRequest request =
          ListCryptoKeyVersionsRequest.newBuilder()
              .setParent(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .setFilter("filter-1274492040")
              .setOrderBy("orderBy-1207110587")
              .build();
      ApiFuture<CryptoKeyVersion> future =
          keyManagementServiceClient.listCryptoKeyVersionsPagedCallable().futureCall(request);
      // Do something.
      for (CryptoKeyVersion element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
