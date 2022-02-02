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
package com.google.cloud.example.library.v1.samples;

// [START REGION TAG]
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.common.base.Strings;
import com.google.example.library.v1.ListShelvesRequest;
import com.google.example.library.v1.ListShelvesResponse;
import com.google.example.library.v1.Shelf;

public class ListShelvesCallableCallListShelvesRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listShelvesCallableCallListShelvesRequestSetPageToken();
  }

  public static void listShelvesCallableCallListShelvesRequestSetPageToken() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ListShelvesRequest request =
          ListShelvesRequest.newBuilder()
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      while (true) {
        ListShelvesResponse response = libraryServiceClient.listShelvesCallable().call(request);
        for (Shelf element : response.getResponsesList()) {
          // doThingsWith(element);
        }
        String nextPageToken = response.getNextPageToken();
        if (!Strings.isNullOrEmpty(nextPageToken)) {
          request = request.toBuilder().setPageToken(nextPageToken).build();
        } else {
          break;
        }
      }
    }
  }
}
// [END REGION TAG]