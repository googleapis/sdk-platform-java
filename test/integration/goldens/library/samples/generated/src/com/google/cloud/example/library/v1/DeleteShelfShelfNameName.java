package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.ShelfName;
import com.google.protobuf.Empty;

public class DeleteShelfShelfNameName {

  public static void main(String[] args) throws Exception {
    deleteShelfShelfNameName();
  }

  public static void deleteShelfShelfNameName() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ShelfName name = ShelfName.of("[SHELF_ID]");
      libraryServiceClient.deleteShelf(name);
    }
  }
}
