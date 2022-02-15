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

// [START library_v1_generated_libraryserviceclient_deletebook_callablefuturecalldeletebookrequest]
import com.google.api.core.ApiFuture;
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.BookName;
import com.google.example.library.v1.DeleteBookRequest;
import com.google.protobuf.Empty;

public class DeleteBookCallableFutureCallDeleteBookRequest {

  public static void main(String[] args) throws Exception {
    deleteBookCallableFutureCallDeleteBookRequest();
  }

  public static void deleteBookCallableFutureCallDeleteBookRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      DeleteBookRequest request =
          DeleteBookRequest.newBuilder()
              .setName(BookName.of("[SHELF]", "[BOOK]").toString())
              .build();
      ApiFuture<Empty> future = libraryServiceClient.deleteBookCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
// [END library_v1_generated_libraryserviceclient_deletebook_callablefuturecalldeletebookrequest]