package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.BookName;
import com.google.example.library.v1.DeleteBookRequest;
import com.google.protobuf.Empty;

public class DeleteBookDeleteBookRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteBookDeleteBookRequestRequest();
  }

  public static void deleteBookDeleteBookRequestRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      DeleteBookRequest request =
          DeleteBookRequest.newBuilder()
              .setName(BookName.of("[SHELF]", "[BOOK]").toString())
              .build();
      libraryServiceClient.deleteBook(request);
    }
  }
}
