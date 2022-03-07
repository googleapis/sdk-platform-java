/*
 * Copyright 2022 Google LLC
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

package com.google.cloud.compute.v1small.samples;

// [START compute_v1small_generated_regionoperationsclient_get_callablefuturecallgetregionoperationrequest_sync]
import com.google.api.core.ApiFuture;
import com.google.cloud.compute.v1small.GetRegionOperationRequest;
import com.google.cloud.compute.v1small.Operation;
import com.google.cloud.compute.v1small.RegionOperationsClient;

public class GetCallableFutureCallGetRegionOperationRequest {

  public static void main(String[] args) throws Exception {
    getCallableFutureCallGetRegionOperationRequest();
  }

  public static void getCallableFutureCallGetRegionOperationRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (RegionOperationsClient regionOperationsClient = RegionOperationsClient.create()) {
      GetRegionOperationRequest request =
          GetRegionOperationRequest.newBuilder()
              .setOperation("operation1662702951")
              .setProject("project-309310695")
              .setRegion("region-934795532")
              .build();
      ApiFuture<Operation> future = regionOperationsClient.getCallable().futureCall(request);
      // Do something.
      Operation response = future.get();
    }
  }
}
// [END compute_v1small_generated_regionoperationsclient_get_callablefuturecallgetregionoperationrequest_sync]
