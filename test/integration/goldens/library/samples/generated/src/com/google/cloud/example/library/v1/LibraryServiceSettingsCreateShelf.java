package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceSettings;
import java.time.Duration;

public class LibraryServiceSettingsCreateShelf {

  public static void main(String[] args) throws Exception {
    libraryServiceSettingsCreateShelf();
  }

  public static void libraryServiceSettingsCreateShelf() throws Exception {
    LibraryServiceSettings.Builder libraryServiceSettingsBuilder =
        LibraryServiceSettings.newBuilder();
    libraryServiceSettingsBuilder
        .createShelfSettings()
        .setRetrySettings(
            libraryServiceSettingsBuilder
                .createShelfSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    LibraryServiceSettings libraryServiceSettings = libraryServiceSettingsBuilder.build();
  }
}
