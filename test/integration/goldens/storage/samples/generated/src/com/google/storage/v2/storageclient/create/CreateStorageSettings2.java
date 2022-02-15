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

// [START storage_v2_generated_storageclient_create_storagesettings2]
import com.google.storage.v2.StorageClient;
import com.google.storage.v2.StorageSettings;
import com.google.storage.v2.myEndpoint;

public class CreateStorageSettings2 {

  public static void main(String[] args) throws Exception {
    createStorageSettings2();
  }

  public static void createStorageSettings2() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    StorageSettings storageSettings = StorageSettings.newBuilder().setEndpoint(myEndpoint).build();
    StorageClient storageClient = StorageClient.create(storageSettings);
  }
}
// [END storage_v2_generated_storageclient_create_storagesettings2]