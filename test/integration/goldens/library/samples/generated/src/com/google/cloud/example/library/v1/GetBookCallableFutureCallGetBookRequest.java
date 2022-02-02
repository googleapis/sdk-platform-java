package com.google.cloud.example.library.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.BookName;
import com.google.example.library.v1.GetBookRequest;

public class GetBookCallableFutureCallGetBookRequest {

  public static void main(String[] args) throws Exception {
    getBookCallableFutureCallGetBookRequest();
  }

  public static void getBookCallableFutureCallGetBookRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      GetBookRequest request =
          GetBookRequest.newBuilder().setName(BookName.of("[SHELF]", "[BOOK]").toString()).build();
      ApiFuture<Book> future = libraryServiceClient.getBookCallable().futureCall(request);
      // Do something.
      Book response = future.get();
    }
  }
}
