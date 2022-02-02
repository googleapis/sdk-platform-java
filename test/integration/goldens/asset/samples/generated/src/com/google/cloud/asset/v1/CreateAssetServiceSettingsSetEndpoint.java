package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.AssetServiceSettings;
import com.google.cloud.asset.v1.myEndpoint;

public class CreateAssetServiceSettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createAssetServiceSettingsSetEndpoint();
  }

  public static void createAssetServiceSettingsSetEndpoint() throws Exception {
    AssetServiceSettings assetServiceSettings =
        AssetServiceSettings.newBuilder().setEndpoint(myEndpoint).build();
    AssetServiceClient assetServiceClient = AssetServiceClient.create(assetServiceSettings);
  }
}
