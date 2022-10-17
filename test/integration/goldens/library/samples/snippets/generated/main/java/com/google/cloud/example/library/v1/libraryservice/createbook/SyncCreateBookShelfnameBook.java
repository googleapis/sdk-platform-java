/*
 * Copyright 2022 Google LLC
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

// [START example_v1_generated_LibraryService_CreateBook_ShelfnameBook_sync]
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.ShelfName;

public class SyncCreateBookShelfnameBook {

  public static void main(String[] args) throws Exception {
    syncCreateBookShelfnameBook();
  }

  public static void syncCreateBookShelfnameBook() throws Exception {
    // This snippet has been automatically generated and should be regarded as a code template only.
    // It will require modifications to work:
    // - It may require correct/in-range values for request initialization.
    // - It may require specifying regional endpoints when creating the service client as shown in
    // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ShelfName parent = ShelfName.of("[SHELF_ID]");
      Book book = Book.newBuilder().build();
      Book response = libraryServiceClient.createBook(parent, book);
    }
  }
}
// [END example_v1_generated_LibraryService_CreateBook_ShelfnameBook_sync]
