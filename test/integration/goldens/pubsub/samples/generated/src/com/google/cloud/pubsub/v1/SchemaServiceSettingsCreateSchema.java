package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceSettings;
import java.time.Duration;

public class SchemaServiceSettingsCreateSchema {

  public static void main(String[] args) throws Exception {
    schemaServiceSettingsCreateSchema();
  }

  public static void schemaServiceSettingsCreateSchema() throws Exception {
    SchemaServiceSettings.Builder schemaServiceSettingsBuilder = SchemaServiceSettings.newBuilder();
    schemaServiceSettingsBuilder
        .createSchemaSettings()
        .setRetrySettings(
            schemaServiceSettingsBuilder
                .createSchemaSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    SchemaServiceSettings schemaServiceSettings = schemaServiceSettingsBuilder.build();
  }
}
