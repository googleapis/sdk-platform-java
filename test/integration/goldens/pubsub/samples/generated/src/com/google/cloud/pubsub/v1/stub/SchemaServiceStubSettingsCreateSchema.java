package com.google.cloud.pubsub.v1.stub.samples;

import com.google.cloud.pubsub.v1.stub.SchemaServiceStubSettings;
import java.time.Duration;

public class SchemaServiceStubSettingsCreateSchema {

  public static void main(String[] args) throws Exception {
    schemaServiceStubSettingsCreateSchema();
  }

  public static void schemaServiceStubSettingsCreateSchema() throws Exception {
    SchemaServiceStubSettings.Builder schemaServiceSettingsBuilder =
        SchemaServiceStubSettings.newBuilder();
    schemaServiceSettingsBuilder
        .createSchemaSettings()
        .setRetrySettings(
            schemaServiceSettingsBuilder
                .createSchemaSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    SchemaServiceStubSettings schemaServiceSettings = schemaServiceSettingsBuilder.build();
  }
}
