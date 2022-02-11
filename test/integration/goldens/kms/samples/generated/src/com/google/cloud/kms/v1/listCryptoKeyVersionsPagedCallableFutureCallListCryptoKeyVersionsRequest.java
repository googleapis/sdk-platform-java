/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.kms.v1.samples;

// [START v1_kms_generated_keymanagementserviceclient_listcryptokeyversions_pagedcallablefuturecalllistcryptokeyversionsrequest]
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
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
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
// [END v1_kms_generated_keymanagementserviceclient_listcryptokeyversions_pagedcallablefuturecalllistcryptokeyversionsrequest]