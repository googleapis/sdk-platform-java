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

// [START v1small_compute_generated_regionoperationsclient_create_regionoperationssettings1]
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.compute.v1small.RegionOperationsClient;
import com.google.cloud.compute.v1small.RegionOperationsSettings;
import com.google.cloud.compute.v1small.myCredentials;

public class CreateRegionOperationsSettings1 {

  public static void main(String[] args) throws Exception {
    createRegionOperationsSettings1();
  }

  public static void createRegionOperationsSettings1() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    RegionOperationsSettings regionOperationsSettings =
        RegionOperationsSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    RegionOperationsClient regionOperationsClient =
        RegionOperationsClient.create(regionOperationsSettings);
  }
}
// [END v1small_compute_generated_regionoperationsclient_create_regionoperationssettings1]