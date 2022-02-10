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

// [START 1.0_10_generated_regionOperationsClient_create_regionOperationsSettings2]
import com.google.cloud.compute.v1small.RegionOperationsClient;
import com.google.cloud.compute.v1small.RegionOperationsSettings;
import com.google.cloud.compute.v1small.myEndpoint;

public class CreateRegionOperationsSettings2 {

  public static void main(String[] args) throws Exception {
    createRegionOperationsSettings2();
  }

  public static void createRegionOperationsSettings2() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    RegionOperationsSettings regionOperationsSettings =
        RegionOperationsSettings.newBuilder().setEndpoint(myEndpoint).build();
    RegionOperationsClient regionOperationsClient =
        RegionOperationsClient.create(regionOperationsSettings);
  }
}
// [END 1.0_10_generated_regionOperationsClient_create_regionOperationsSettings2]