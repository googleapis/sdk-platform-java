package com.google.cloud.example.library.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.CreateShelfRequest;
import com.google.example.library.v1.Shelf;

public class CreateShelfCallableFutureCallCreateShelfRequest {

  public static void main(String[] args) throws Exception {
    createShelfCallableFutureCallCreateShelfRequest();
  }

  public static void createShelfCallableFutureCallCreateShelfRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      CreateShelfRequest request =
          CreateShelfRequest.newBuilder().setShelf(Shelf.newBuilder().build()).build();
      ApiFuture<Shelf> future = libraryServiceClient.createShelfCallable().futureCall(request);
      // Do something.
      Shelf response = future.get();
    }
  }
}
