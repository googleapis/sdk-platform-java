package com.google.storage.v2.samples;

import com.google.storage.v2.StorageSettings;
import java.time.Duration;

public class StorageSettingsDeleteBucket {

  public static void main(String[] args) throws Exception {
    storageSettingsDeleteBucket();
  }

  public static void storageSettingsDeleteBucket() throws Exception {
    StorageSettings.Builder storageSettingsBuilder = StorageSettings.newBuilder();
    storageSettingsBuilder
        .deleteBucketSettings()
        .setRetrySettings(
            storageSettingsBuilder
                .deleteBucketSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    StorageSettings storageSettings = storageSettingsBuilder.build();
  }
}
