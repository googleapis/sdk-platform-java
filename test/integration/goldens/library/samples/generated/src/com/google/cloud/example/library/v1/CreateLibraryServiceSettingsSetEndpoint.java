package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.cloud.example.library.v1.LibraryServiceSettings;
import com.google.cloud.example.library.v1.myEndpoint;

public class CreateLibraryServiceSettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createLibraryServiceSettingsSetEndpoint();
  }

  public static void createLibraryServiceSettingsSetEndpoint() throws Exception {
    LibraryServiceSettings libraryServiceSettings =
        LibraryServiceSettings.newBuilder().setEndpoint(myEndpoint).build();
    LibraryServiceClient libraryServiceClient = LibraryServiceClient.create(libraryServiceSettings);
  }
}
