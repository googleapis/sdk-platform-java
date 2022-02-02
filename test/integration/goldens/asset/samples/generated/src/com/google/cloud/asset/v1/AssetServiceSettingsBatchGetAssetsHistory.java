package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceSettings;
import java.time.Duration;

public class AssetServiceSettingsBatchGetAssetsHistory {

  public static void main(String[] args) throws Exception {
    assetServiceSettingsBatchGetAssetsHistory();
  }

  public static void assetServiceSettingsBatchGetAssetsHistory() throws Exception {
    AssetServiceSettings.Builder assetServiceSettingsBuilder = AssetServiceSettings.newBuilder();
    assetServiceSettingsBuilder
        .batchGetAssetsHistorySettings()
        .setRetrySettings(
            assetServiceSettingsBuilder
                .batchGetAssetsHistorySettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    AssetServiceSettings assetServiceSettings = assetServiceSettingsBuilder.build();
  }
}
