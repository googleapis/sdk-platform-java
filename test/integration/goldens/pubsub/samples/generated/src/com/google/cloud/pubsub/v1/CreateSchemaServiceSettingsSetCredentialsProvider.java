package com.google.cloud.pubsub.v1.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.cloud.pubsub.v1.SchemaServiceSettings;
import com.google.cloud.pubsub.v1.myCredentials;

public class CreateSchemaServiceSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createSchemaServiceSettingsSetCredentialsProvider();
  }

  public static void createSchemaServiceSettingsSetCredentialsProvider() throws Exception {
    SchemaServiceSettings schemaServiceSettings =
        SchemaServiceSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    SchemaServiceClient schemaServiceClient = SchemaServiceClient.create(schemaServiceSettings);
  }
}
