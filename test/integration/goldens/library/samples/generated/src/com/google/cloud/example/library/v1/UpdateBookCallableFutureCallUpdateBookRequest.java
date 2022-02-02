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
import com.google.example.library.v1.Book;
import com.google.example.library.v1.UpdateBookRequest;
import com.google.protobuf.FieldMask;

public class UpdateBookCallableFutureCallUpdateBookRequest {

  public static void main(String[] args) throws Exception {
    updateBookCallableFutureCallUpdateBookRequest();
  }

  public static void updateBookCallableFutureCallUpdateBookRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      UpdateBookRequest request =
          UpdateBookRequest.newBuilder()
              .setBook(Book.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<Book> future = libraryServiceClient.updateBookCallable().futureCall(request);
      // Do something.
      Book response = future.get();
    }
  }
}
// [END REGION TAG]