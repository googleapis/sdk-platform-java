package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.ShelfName;

public class ListBooksShelfNameIterateAll {

  public static void main(String[] args) throws Exception {
    listBooksShelfNameIterateAll();
  }

  public static void listBooksShelfNameIterateAll() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ShelfName parent = ShelfName.of("[SHELF_ID]");
      for (Book element : libraryServiceClient.listBooks(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
