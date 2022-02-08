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
package com.google.cloud.compute.v1small.samples;

// [START 10_10_generated_regionOperationsClient_wait_waitRegionOperationRequest]
import com.google.cloud.compute.v1small.Operation;
import com.google.cloud.compute.v1small.RegionOperationsClient;
import com.google.cloud.compute.v1small.WaitRegionOperationRequest;

public class WaitWaitRegionOperationRequest {

  public static void main(String[] args) throws Exception {
    waitWaitRegionOperationRequest();
  }

  public static void waitWaitRegionOperationRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (RegionOperationsClient regionOperationsClient = RegionOperationsClient.create()) {
      WaitRegionOperationRequest request =
          WaitRegionOperationRequest.newBuilder()
              .setOperation("operation1662702951")
              .setProject("project-309310695")
              .setRegion("region-934795532")
              .build();
      Operation response = regionOperationsClient.wait(request);
    }
  }
}
// [END 10_10_generated_regionOperationsClient_wait_waitRegionOperationRequest]