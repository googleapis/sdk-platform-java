package com.google.cloud.example.library.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.ListBooksRequest;
import com.google.example.library.v1.ShelfName;

public class ListBooksPagedCallableFutureCallListBooksRequest {

  public static void main(String[] args) throws Exception {
    listBooksPagedCallableFutureCallListBooksRequest();
  }

  public static void listBooksPagedCallableFutureCallListBooksRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ListBooksRequest request =
          ListBooksRequest.newBuilder()
              .setParent(ShelfName.of("[SHELF_ID]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      ApiFuture<Book> future = libraryServiceClient.listBooksPagedCallable().futureCall(request);
      // Do something.
      for (Book element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
