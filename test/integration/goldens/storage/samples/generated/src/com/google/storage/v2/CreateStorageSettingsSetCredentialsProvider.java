package com.google.storage.v2.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.storage.v2.StorageClient;
import com.google.storage.v2.StorageSettings;
import com.google.storage.v2.myCredentials;

public class CreateStorageSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createStorageSettingsSetCredentialsProvider();
  }

  public static void createStorageSettingsSetCredentialsProvider() throws Exception {
    StorageSettings storageSettings =
        StorageSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    StorageClient storageClient = StorageClient.create(storageSettings);
  }
}
