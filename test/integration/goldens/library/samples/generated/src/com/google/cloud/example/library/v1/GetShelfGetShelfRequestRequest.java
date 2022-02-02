package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.GetShelfRequest;
import com.google.example.library.v1.Shelf;
import com.google.example.library.v1.ShelfName;

public class GetShelfGetShelfRequestRequest {

  public static void main(String[] args) throws Exception {
    getShelfGetShelfRequestRequest();
  }

  public static void getShelfGetShelfRequestRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      GetShelfRequest request =
          GetShelfRequest.newBuilder().setName(ShelfName.of("[SHELF_ID]").toString()).build();
      Shelf response = libraryServiceClient.getShelf(request);
    }
  }
}
