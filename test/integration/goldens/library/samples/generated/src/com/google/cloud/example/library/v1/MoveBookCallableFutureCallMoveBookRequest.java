package com.google.cloud.example.library.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.BookName;
import com.google.example.library.v1.MoveBookRequest;
import com.google.example.library.v1.ShelfName;

public class MoveBookCallableFutureCallMoveBookRequest {

  public static void main(String[] args) throws Exception {
    moveBookCallableFutureCallMoveBookRequest();
  }

  public static void moveBookCallableFutureCallMoveBookRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      MoveBookRequest request =
          MoveBookRequest.newBuilder()
              .setName(BookName.of("[SHELF]", "[BOOK]").toString())
              .setOtherShelfName(ShelfName.of("[SHELF_ID]").toString())
              .build();
      ApiFuture<Book> future = libraryServiceClient.moveBookCallable().futureCall(request);
      // Do something.
      Book response = future.get();
    }
  }
}
