package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Schema;
import com.google.pubsub.v1.ValidateSchemaResponse;

public class ValidateSchemaStringParentSchemaSchema {

  public static void main(String[] args) throws Exception {
    validateSchemaStringParentSchemaSchema();
  }

  public static void validateSchemaStringParentSchemaSchema() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      Schema schema = Schema.newBuilder().build();
      ValidateSchemaResponse response = schemaServiceClient.validateSchema(parent, schema);
    }
  }
}
