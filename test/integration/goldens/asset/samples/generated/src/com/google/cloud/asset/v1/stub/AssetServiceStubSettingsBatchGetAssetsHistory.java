package com.google.cloud.asset.v1.stub.samples;

import com.google.cloud.asset.v1.stub.AssetServiceStubSettings;
import java.time.Duration;

public class AssetServiceStubSettingsBatchGetAssetsHistory {

  public static void main(String[] args) throws Exception {
    assetServiceStubSettingsBatchGetAssetsHistory();
  }

  public static void assetServiceStubSettingsBatchGetAssetsHistory() throws Exception {
    AssetServiceStubSettings.Builder assetServiceSettingsBuilder =
        AssetServiceStubSettings.newBuilder();
    assetServiceSettingsBuilder
        .batchGetAssetsHistorySettings()
        .setRetrySettings(
            assetServiceSettingsBuilder
                .batchGetAssetsHistorySettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    AssetServiceStubSettings assetServiceSettings = assetServiceSettingsBuilder.build();
  }
}
