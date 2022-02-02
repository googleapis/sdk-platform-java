package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.BookName;
import com.google.example.library.v1.GetBookRequest;

public class GetBookGetBookRequestRequest {

  public static void main(String[] args) throws Exception {
    getBookGetBookRequestRequest();
  }

  public static void getBookGetBookRequestRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      GetBookRequest request =
          GetBookRequest.newBuilder().setName(BookName.of("[SHELF]", "[BOOK]").toString()).build();
      Book response = libraryServiceClient.getBook(request);
    }
  }
}
