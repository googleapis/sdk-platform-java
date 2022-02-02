package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.ShelfName;

public class ListBooksStringIterateAll {

  public static void main(String[] args) throws Exception {
    listBooksStringIterateAll();
  }

  public static void listBooksStringIterateAll() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      String parent = ShelfName.of("[SHELF_ID]").toString();
      for (Book element : libraryServiceClient.listBooks(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
