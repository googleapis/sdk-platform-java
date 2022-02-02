package com.google.cloud.asset.v1.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.AssetServiceSettings;
import com.google.cloud.asset.v1.myCredentials;

public class CreateAssetServiceSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createAssetServiceSettingsSetCredentialsProvider();
  }

  public static void createAssetServiceSettingsSetCredentialsProvider() throws Exception {
    AssetServiceSettings assetServiceSettings =
        AssetServiceSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    AssetServiceClient assetServiceClient = AssetServiceClient.create(assetServiceSettings);
  }
}
