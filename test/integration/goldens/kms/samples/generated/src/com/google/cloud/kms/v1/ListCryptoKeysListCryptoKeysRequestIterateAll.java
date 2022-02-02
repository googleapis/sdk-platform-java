package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;
import com.google.cloud.kms.v1.ListCryptoKeysRequest;

public class ListCryptoKeysListCryptoKeysRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listCryptoKeysListCryptoKeysRequestIterateAll();
  }

  public static void listCryptoKeysListCryptoKeysRequestIterateAll() throws Exception {
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
      for (CryptoKey element : keyManagementServiceClient.listCryptoKeys(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
