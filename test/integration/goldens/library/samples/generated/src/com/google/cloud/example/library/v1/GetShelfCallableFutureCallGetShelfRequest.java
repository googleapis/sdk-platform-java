package com.google.cloud.example.library.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.GetShelfRequest;
import com.google.example.library.v1.Shelf;
import com.google.example.library.v1.ShelfName;

public class GetShelfCallableFutureCallGetShelfRequest {

  public static void main(String[] args) throws Exception {
    getShelfCallableFutureCallGetShelfRequest();
  }

  public static void getShelfCallableFutureCallGetShelfRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      GetShelfRequest request =
          GetShelfRequest.newBuilder().setName(ShelfName.of("[SHELF_ID]").toString()).build();
      ApiFuture<Shelf> future = libraryServiceClient.getShelfCallable().futureCall(request);
      // Do something.
      Shelf response = future.get();
    }
  }
}
