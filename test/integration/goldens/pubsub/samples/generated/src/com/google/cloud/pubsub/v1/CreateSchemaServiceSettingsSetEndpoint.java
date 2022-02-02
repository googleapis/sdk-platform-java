package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.cloud.pubsub.v1.SchemaServiceSettings;
import com.google.cloud.pubsub.v1.myEndpoint;

public class CreateSchemaServiceSettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createSchemaServiceSettingsSetEndpoint();
  }

  public static void createSchemaServiceSettingsSetEndpoint() throws Exception {
    SchemaServiceSettings schemaServiceSettings =
        SchemaServiceSettings.newBuilder().setEndpoint(myEndpoint).build();
    SchemaServiceClient schemaServiceClient = SchemaServiceClient.create(schemaServiceSettings);
  }
}
