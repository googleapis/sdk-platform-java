package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.CreateBookRequest;
import com.google.example.library.v1.ShelfName;

public class CreateBookCreateBookRequestRequest {

  public static void main(String[] args) throws Exception {
    createBookCreateBookRequestRequest();
  }

  public static void createBookCreateBookRequestRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      CreateBookRequest request =
          CreateBookRequest.newBuilder()
              .setParent(ShelfName.of("[SHELF_ID]").toString())
              .setBook(Book.newBuilder().build())
              .build();
      Book response = libraryServiceClient.createBook(request);
    }
  }
}
