package com.google.storage.v2.stub.samples;

import com.google.storage.v2.stub.StorageStubSettings;
import java.time.Duration;

public class StorageStubSettingsDeleteBucket {

  public static void main(String[] args) throws Exception {
    storageStubSettingsDeleteBucket();
  }

  public static void storageStubSettingsDeleteBucket() throws Exception {
    StorageStubSettings.Builder storageSettingsBuilder = StorageStubSettings.newBuilder();
    storageSettingsBuilder
        .deleteBucketSettings()
        .setRetrySettings(
            storageSettingsBuilder
                .deleteBucketSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    StorageStubSettings storageSettings = storageSettingsBuilder.build();
  }
}
