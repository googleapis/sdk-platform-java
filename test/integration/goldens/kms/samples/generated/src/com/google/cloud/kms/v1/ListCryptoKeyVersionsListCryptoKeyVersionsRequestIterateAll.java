package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.ListCryptoKeyVersionsRequest;

public class ListCryptoKeyVersionsListCryptoKeyVersionsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listCryptoKeyVersionsListCryptoKeyVersionsRequestIterateAll();
  }

  public static void listCryptoKeyVersionsListCryptoKeyVersionsRequestIterateAll()
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
      for (CryptoKeyVersion element :
          keyManagementServiceClient.listCryptoKeyVersions(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
