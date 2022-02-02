package com.google.cloud.example.library.v1.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.cloud.example.library.v1.LibraryServiceSettings;
import com.google.cloud.example.library.v1.myCredentials;

public class CreateLibraryServiceSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createLibraryServiceSettingsSetCredentialsProvider();
  }

  public static void createLibraryServiceSettingsSetCredentialsProvider() throws Exception {
    LibraryServiceSettings libraryServiceSettings =
        LibraryServiceSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    LibraryServiceClient libraryServiceClient = LibraryServiceClient.create(libraryServiceSettings);
  }
}
