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
package com.google.cloud.asset.v1.samples;

// [START v1_asset_generated_assetserviceclient_analyzemove_callablefuturecallanalyzemoverequest]
import com.google.api.core.ApiFuture;
import com.google.cloud.asset.v1.AnalyzeMoveRequest;
import com.google.cloud.asset.v1.AnalyzeMoveResponse;
import com.google.cloud.asset.v1.AssetServiceClient;

public class AnalyzeMoveCallableFutureCallAnalyzeMoveRequest {

  public static void main(String[] args) throws Exception {
    analyzeMoveCallableFutureCallAnalyzeMoveRequest();
  }

  public static void analyzeMoveCallableFutureCallAnalyzeMoveRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      AnalyzeMoveRequest request =
          AnalyzeMoveRequest.newBuilder()
              .setResource("resource-341064690")
              .setDestinationParent("destinationParent-1733659048")
              .build();
      ApiFuture<AnalyzeMoveResponse> future =
          assetServiceClient.analyzeMoveCallable().futureCall(request);
      // Do something.
      AnalyzeMoveResponse response = future.get();
    }
  }
}
// [END v1_asset_generated_assetserviceclient_analyzemove_callablefuturecallanalyzemoverequest]