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

// [START 1.0_10_generated_libraryserviceclient_getshelf_getshelfrequest]
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.GetShelfRequest;
import com.google.example.library.v1.Shelf;
import com.google.example.library.v1.ShelfName;

public class GetShelfGetShelfRequest {

  public static void main(String[] args) throws Exception {
    getShelfGetShelfRequest();
  }

  public static void getShelfGetShelfRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      GetShelfRequest request =
          GetShelfRequest.newBuilder().setName(ShelfName.of("[SHELF_ID]").toString()).build();
      Shelf response = libraryServiceClient.getShelf(request);
    }
  }
}
// [END 1.0_10_generated_libraryserviceclient_getshelf_getshelfrequest]