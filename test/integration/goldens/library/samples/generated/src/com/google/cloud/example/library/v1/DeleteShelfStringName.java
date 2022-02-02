package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.ShelfName;
import com.google.protobuf.Empty;

public class DeleteShelfStringName {

  public static void main(String[] args) throws Exception {
    deleteShelfStringName();
  }

  public static void deleteShelfStringName() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      String name = ShelfName.of("[SHELF_ID]").toString();
      libraryServiceClient.deleteShelf(name);
    }
  }
}
