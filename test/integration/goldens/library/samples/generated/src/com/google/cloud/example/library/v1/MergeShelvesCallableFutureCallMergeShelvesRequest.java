package com.google.cloud.example.library.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.MergeShelvesRequest;
import com.google.example.library.v1.Shelf;
import com.google.example.library.v1.ShelfName;

public class MergeShelvesCallableFutureCallMergeShelvesRequest {

  public static void main(String[] args) throws Exception {
    mergeShelvesCallableFutureCallMergeShelvesRequest();
  }

  public static void mergeShelvesCallableFutureCallMergeShelvesRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      MergeShelvesRequest request =
          MergeShelvesRequest.newBuilder()
              .setName(ShelfName.of("[SHELF_ID]").toString())
              .setOtherShelf(ShelfName.of("[SHELF_ID]").toString())
              .build();
      ApiFuture<Shelf> future = libraryServiceClient.mergeShelvesCallable().futureCall(request);
      // Do something.
      Shelf response = future.get();
    }
  }
}
