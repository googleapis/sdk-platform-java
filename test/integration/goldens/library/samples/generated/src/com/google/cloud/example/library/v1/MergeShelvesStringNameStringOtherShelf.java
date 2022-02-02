package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Shelf;
import com.google.example.library.v1.ShelfName;

public class MergeShelvesStringNameStringOtherShelf {

  public static void main(String[] args) throws Exception {
    mergeShelvesStringNameStringOtherShelf();
  }

  public static void mergeShelvesStringNameStringOtherShelf() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      String name = ShelfName.of("[SHELF_ID]").toString();
      String otherShelf = ShelfName.of("[SHELF_ID]").toString();
      Shelf response = libraryServiceClient.mergeShelves(name, otherShelf);
    }
  }
}
