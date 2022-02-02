package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Schema;

public class CreateSchemaStringParentSchemaSchemaStringSchemaId {

  public static void main(String[] args) throws Exception {
    createSchemaStringParentSchemaSchemaStringSchemaId();
  }

  public static void createSchemaStringParentSchemaSchemaStringSchemaId() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      Schema schema = Schema.newBuilder().build();
      String schemaId = "schemaId-697673060";
      Schema response = schemaServiceClient.createSchema(parent, schema, schemaId);
    }
  }
}
