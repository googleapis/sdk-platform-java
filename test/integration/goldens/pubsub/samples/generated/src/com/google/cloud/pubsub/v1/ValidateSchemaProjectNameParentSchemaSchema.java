package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Schema;
import com.google.pubsub.v1.ValidateSchemaResponse;

public class ValidateSchemaProjectNameParentSchemaSchema {

  public static void main(String[] args) throws Exception {
    validateSchemaProjectNameParentSchemaSchema();
  }

  public static void validateSchemaProjectNameParentSchemaSchema() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      Schema schema = Schema.newBuilder().build();
      ValidateSchemaResponse response = schemaServiceClient.validateSchema(parent, schema);
    }
  }
}
