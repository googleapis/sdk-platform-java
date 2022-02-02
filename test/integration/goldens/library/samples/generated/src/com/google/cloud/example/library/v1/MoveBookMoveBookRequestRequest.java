package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.BookName;
import com.google.example.library.v1.MoveBookRequest;
import com.google.example.library.v1.ShelfName;

public class MoveBookMoveBookRequestRequest {

  public static void main(String[] args) throws Exception {
    moveBookMoveBookRequestRequest();
  }

  public static void moveBookMoveBookRequestRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      MoveBookRequest request =
          MoveBookRequest.newBuilder()
              .setName(BookName.of("[SHELF]", "[BOOK]").toString())
              .setOtherShelfName(ShelfName.of("[SHELF_ID]").toString())
              .build();
      Book response = libraryServiceClient.moveBook(request);
    }
  }
}
