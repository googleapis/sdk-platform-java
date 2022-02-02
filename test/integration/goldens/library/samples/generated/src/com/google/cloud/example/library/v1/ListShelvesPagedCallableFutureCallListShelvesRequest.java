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
import com.google.api.core.ApiFuture;
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.ListShelvesRequest;
import com.google.example.library.v1.Shelf;

public class ListShelvesPagedCallableFutureCallListShelvesRequest {

  public static void main(String[] args) throws Exception {
    listShelvesPagedCallableFutureCallListShelvesRequest();
  }

  public static void listShelvesPagedCallableFutureCallListShelvesRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ListShelvesRequest request =
          ListShelvesRequest.newBuilder()
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      ApiFuture<Shelf> future = libraryServiceClient.listShelvesPagedCallable().futureCall(request);
      // Do something.
      for (Shelf element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
// [END REGION TAG]