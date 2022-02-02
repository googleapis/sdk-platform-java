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

// [START REGION TAG]
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.location.ListLocationsRequest;
import com.google.cloud.location.Location;

public class ListLocationsListLocationsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listLocationsListLocationsRequestIterateAll();
  }

  public static void listLocationsListLocationsRequestIterateAll() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      ListLocationsRequest request =
          ListLocationsRequest.newBuilder()
              .setName("name3373707")
              .setFilter("filter-1274492040")
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      for (Location element : keyManagementServiceClient.listLocations(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
// [END REGION TAG]