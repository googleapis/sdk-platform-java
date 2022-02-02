package com.google.cloud.example.library.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.ListShelvesRequest;
import com.google.example.library.v1.Shelf;

public class ListShelvesPagedCallableFutureCallListShelvesRequest {

  public static void main(String[] args) throws Exception {
    listShelvesPagedCallableFutureCallListShelvesRequest();
  }

  public static void listShelvesPagedCallableFutureCallListShelvesRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ListShelvesRequest request =
          ListShelvesRequest.newBuilder()
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      ApiFuture<Shelf> future = libraryServiceClient.listShelvesPagedCallable().futureCall(request);
      // Do something.
      for (Shelf element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
