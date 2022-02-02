package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.ShelfName;

public class CreateBookShelfNameParentBookBook {

  public static void main(String[] args) throws Exception {
    createBookShelfNameParentBookBook();
  }

  public static void createBookShelfNameParentBookBook() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ShelfName parent = ShelfName.of("[SHELF_ID]");
      Book book = Book.newBuilder().build();
      Book response = libraryServiceClient.createBook(parent, book);
    }
  }
}
