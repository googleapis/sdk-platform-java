package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Shelf;
import com.google.example.library.v1.ShelfName;

public class GetShelfShelfNameName {

  public static void main(String[] args) throws Exception {
    getShelfShelfNameName();
  }

  public static void getShelfShelfNameName() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ShelfName name = ShelfName.of("[SHELF_ID]");
      Shelf response = libraryServiceClient.getShelf(name);
    }
  }
}
