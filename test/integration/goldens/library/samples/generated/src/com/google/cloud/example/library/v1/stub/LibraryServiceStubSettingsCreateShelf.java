package com.google.cloud.example.library.v1.stub.samples;

import com.google.cloud.example.library.v1.stub.LibraryServiceStubSettings;
import java.time.Duration;

public class LibraryServiceStubSettingsCreateShelf {

  public static void main(String[] args) throws Exception {
    libraryServiceStubSettingsCreateShelf();
  }

  public static void libraryServiceStubSettingsCreateShelf() throws Exception {
    LibraryServiceStubSettings.Builder libraryServiceSettingsBuilder =
        LibraryServiceStubSettings.newBuilder();
    libraryServiceSettingsBuilder
        .createShelfSettings()
        .setRetrySettings(
            libraryServiceSettingsBuilder
                .createShelfSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    LibraryServiceStubSettings libraryServiceSettings = libraryServiceSettingsBuilder.build();
  }
}
