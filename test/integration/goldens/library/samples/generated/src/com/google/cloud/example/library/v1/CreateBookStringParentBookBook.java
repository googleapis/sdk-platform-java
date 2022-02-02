package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.ShelfName;

public class CreateBookStringParentBookBook {

  public static void main(String[] args) throws Exception {
    createBookStringParentBookBook();
  }

  public static void createBookStringParentBookBook() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      String parent = ShelfName.of("[SHELF_ID]").toString();
      Book book = Book.newBuilder().build();
      Book response = libraryServiceClient.createBook(parent, book);
    }
  }
}
