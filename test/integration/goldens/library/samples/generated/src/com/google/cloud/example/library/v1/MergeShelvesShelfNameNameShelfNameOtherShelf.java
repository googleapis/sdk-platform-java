package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Shelf;
import com.google.example.library.v1.ShelfName;

public class MergeShelvesShelfNameNameShelfNameOtherShelf {

  public static void main(String[] args) throws Exception {
    mergeShelvesShelfNameNameShelfNameOtherShelf();
  }

  public static void mergeShelvesShelfNameNameShelfNameOtherShelf() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ShelfName name = ShelfName.of("[SHELF_ID]");
      ShelfName otherShelf = ShelfName.of("[SHELF_ID]");
      Shelf response = libraryServiceClient.mergeShelves(name, otherShelf);
    }
  }
}
