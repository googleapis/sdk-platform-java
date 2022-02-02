package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.ListCryptoKeyVersionsRequest;
import com.google.cloud.kms.v1.ListCryptoKeyVersionsResponse;
import com.google.common.base.Strings;

public class ListCryptoKeyVersionsCallableCallListCryptoKeyVersionsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listCryptoKeyVersionsCallableCallListCryptoKeyVersionsRequestSetPageToken();
  }

  public static void listCryptoKeyVersionsCallableCallListCryptoKeyVersionsRequestSetPageToken()
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
      while (true) {
        ListCryptoKeyVersionsResponse response =
            keyManagementServiceClient.listCryptoKeyVersionsCallable().call(request);
        for (CryptoKeyVersion element : response.getResponsesList()) {
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
