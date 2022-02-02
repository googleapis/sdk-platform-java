package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Shelf;

public class CreateShelfShelfShelf {

  public static void main(String[] args) throws Exception {
    createShelfShelfShelf();
  }

  public static void createShelfShelfShelf() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      Shelf shelf = Shelf.newBuilder().build();
      Shelf response = libraryServiceClient.createShelf(shelf);
    }
  }
}
