package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.CreateShelfRequest;
import com.google.example.library.v1.Shelf;

public class CreateShelfCreateShelfRequestRequest {

  public static void main(String[] args) throws Exception {
    createShelfCreateShelfRequestRequest();
  }

  public static void createShelfCreateShelfRequestRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      CreateShelfRequest request =
          CreateShelfRequest.newBuilder().setShelf(Shelf.newBuilder().build()).build();
      Shelf response = libraryServiceClient.createShelf(request);
    }
  }
}
