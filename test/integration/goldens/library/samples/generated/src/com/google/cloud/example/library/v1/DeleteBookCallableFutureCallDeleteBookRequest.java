package com.google.cloud.example.library.v1.samples;

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
