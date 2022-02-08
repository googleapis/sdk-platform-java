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
package com.google.storage.v2.samples;

// [START 10_10_generated_storageClient_composeObject_composeObjectRequest]
import com.google.storage.v2.CommonObjectRequestParams;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.ComposeObjectRequest;
import com.google.storage.v2.CryptoKeyName;
import com.google.storage.v2.Object;
import com.google.storage.v2.PredefinedObjectAcl;
import com.google.storage.v2.StorageClient;
import java.util.ArrayList;

public class ComposeObjectComposeObjectRequest {

  public static void main(String[] args) throws Exception {
    composeObjectComposeObjectRequest();
  }

  public static void composeObjectComposeObjectRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (StorageClient storageClient = StorageClient.create()) {
      ComposeObjectRequest request =
          ComposeObjectRequest.newBuilder()
              .setDestination(Object.newBuilder().build())
              .addAllSourceObjects(new ArrayList<ComposeObjectRequest.SourceObject>())
              .setDestinationPredefinedAcl(PredefinedObjectAcl.forNumber(0))
              .setIfGenerationMatch(-1086241088)
              .setIfMetagenerationMatch(1043427781)
              .setKmsKey(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setCommonObjectRequestParams(CommonObjectRequestParams.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      Object response = storageClient.composeObject(request);
    }
  }
}
// [END 10_10_generated_storageClient_composeObject_composeObjectRequest]