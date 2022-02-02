package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.common.base.Strings;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.ListBooksRequest;
import com.google.example.library.v1.ListBooksResponse;
import com.google.example.library.v1.ShelfName;

public class ListBooksCallableCallListBooksRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listBooksCallableCallListBooksRequestSetPageToken();
  }

  public static void listBooksCallableCallListBooksRequestSetPageToken() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ListBooksRequest request =
          ListBooksRequest.newBuilder()
              .setParent(ShelfName.of("[SHELF_ID]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      while (true) {
        ListBooksResponse response = libraryServiceClient.listBooksCallable().call(request);
        for (Book element : response.getResponsesList()) {
          // doThingsWith(element);
        }
        String nextPageToken = response.getNextPageToken();
        if (!Strings.isNullOrEmpty(nextPageToken)) {
          request = request.toBuilder().setPageToken(nextPageToken).build();
        } else {
          break;
        }
      }
    }
  }
}
