package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.ListBooksRequest;
import com.google.example.library.v1.ShelfName;

public class ListBooksListBooksRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listBooksListBooksRequestIterateAll();
  }

  public static void listBooksListBooksRequestIterateAll() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ListBooksRequest request =
          ListBooksRequest.newBuilder()
              .setParent(ShelfName.of("[SHELF_ID]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      for (Book element : libraryServiceClient.listBooks(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
