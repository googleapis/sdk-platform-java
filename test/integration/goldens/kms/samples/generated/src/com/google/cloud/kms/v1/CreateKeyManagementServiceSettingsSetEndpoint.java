package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyManagementServiceSettings;
import com.google.cloud.kms.v1.myEndpoint;

public class CreateKeyManagementServiceSettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createKeyManagementServiceSettingsSetEndpoint();
  }

  public static void createKeyManagementServiceSettingsSetEndpoint() throws Exception {
    KeyManagementServiceSettings keyManagementServiceSettings =
        KeyManagementServiceSettings.newBuilder().setEndpoint(myEndpoint).build();
    KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create(keyManagementServiceSettings);
  }
}
