package com.google.cloud.kms.v1.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyManagementServiceSettings;
import com.google.cloud.kms.v1.myCredentials;

public class CreateKeyManagementServiceSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createKeyManagementServiceSettingsSetCredentialsProvider();
  }

  public static void createKeyManagementServiceSettingsSetCredentialsProvider() throws Exception {
    KeyManagementServiceSettings keyManagementServiceSettings =
        KeyManagementServiceSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create(keyManagementServiceSettings);
  }
}
