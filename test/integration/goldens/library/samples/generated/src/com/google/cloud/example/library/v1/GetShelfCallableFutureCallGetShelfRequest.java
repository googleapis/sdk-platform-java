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
import com.google.example.library.v1.GetShelfRequest;
import com.google.example.library.v1.Shelf;
import com.google.example.library.v1.ShelfName;

public class GetShelfCallableFutureCallGetShelfRequest {

  public static void main(String[] args) throws Exception {
    getShelfCallableFutureCallGetShelfRequest();
  }

  public static void getShelfCallableFutureCallGetShelfRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      GetShelfRequest request =
          GetShelfRequest.newBuilder().setName(ShelfName.of("[SHELF_ID]").toString()).build();
      ApiFuture<Shelf> future = libraryServiceClient.getShelfCallable().futureCall(request);
      // Do something.
      Shelf response = future.get();
    }
  }
}
// [END REGION TAG]