package com.google.storage.v2.samples;

import com.google.storage.v2.StorageClient;
import com.google.storage.v2.StorageSettings;
import com.google.storage.v2.myEndpoint;

public class CreateStorageSettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createStorageSettingsSetEndpoint();
  }

  public static void createStorageSettingsSetEndpoint() throws Exception {
    StorageSettings storageSettings = StorageSettings.newBuilder().setEndpoint(myEndpoint).build();
    StorageClient storageClient = StorageClient.create(storageSettings);
  }
}
