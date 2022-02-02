package com.google.cloud.example.library.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.CreateBookRequest;
import com.google.example.library.v1.ShelfName;

public class CreateBookCallableFutureCallCreateBookRequest {

  public static void main(String[] args) throws Exception {
    createBookCallableFutureCallCreateBookRequest();
  }

  public static void createBookCallableFutureCallCreateBookRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      CreateBookRequest request =
          CreateBookRequest.newBuilder()
              .setParent(ShelfName.of("[SHELF_ID]").toString())
              .setBook(Book.newBuilder().build())
              .build();
      ApiFuture<Book> future = libraryServiceClient.createBookCallable().futureCall(request);
      // Do something.
      Book response = future.get();
    }
  }
}
