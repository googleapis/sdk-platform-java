package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.ListShelvesRequest;
import com.google.example.library.v1.Shelf;

public class ListShelvesListShelvesRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listShelvesListShelvesRequestIterateAll();
  }

  public static void listShelvesListShelvesRequestIterateAll() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ListShelvesRequest request =
          ListShelvesRequest.newBuilder()
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      for (Shelf element : libraryServiceClient.listShelves(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
