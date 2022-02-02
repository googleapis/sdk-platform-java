package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Shelf;
import com.google.example.library.v1.ShelfName;

public class MergeShelvesStringNameShelfNameOtherShelf {

  public static void main(String[] args) throws Exception {
    mergeShelvesStringNameShelfNameOtherShelf();
  }

  public static void mergeShelvesStringNameShelfNameOtherShelf() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      String name = ShelfName.of("[SHELF_ID]").toString();
      ShelfName otherShelf = ShelfName.of("[SHELF_ID]");
      Shelf response = libraryServiceClient.mergeShelves(name, otherShelf);
    }
  }
}
