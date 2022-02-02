package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Shelf;
import com.google.example.library.v1.ShelfName;

public class MergeShelvesShelfNameNameStringOtherShelf {

  public static void main(String[] args) throws Exception {
    mergeShelvesShelfNameNameStringOtherShelf();
  }

  public static void mergeShelvesShelfNameNameStringOtherShelf() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ShelfName name = ShelfName.of("[SHELF_ID]");
      String otherShelf = ShelfName.of("[SHELF_ID]").toString();
      Shelf response = libraryServiceClient.mergeShelves(name, otherShelf);
    }
  }
}
