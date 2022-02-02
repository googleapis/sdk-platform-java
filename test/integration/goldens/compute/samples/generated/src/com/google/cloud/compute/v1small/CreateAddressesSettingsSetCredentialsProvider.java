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

// [START REGION TAG]
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.AddressesSettings;
import com.google.cloud.compute.v1small.myCredentials;

public class CreateAddressesSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createAddressesSettingsSetCredentialsProvider();
  }

  public static void createAddressesSettingsSetCredentialsProvider() throws Exception {
    AddressesSettings addressesSettings =
        AddressesSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    AddressesClient addressesClient = AddressesClient.create(addressesSettings);
  }
}
// [END REGION TAG]