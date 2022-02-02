package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Schema;

public class CreateSchemaProjectNameParentSchemaSchemaStringSchemaId {

  public static void main(String[] args) throws Exception {
    createSchemaProjectNameParentSchemaSchemaStringSchemaId();
  }

  public static void createSchemaProjectNameParentSchemaSchemaStringSchemaId() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      Schema schema = Schema.newBuilder().build();
      String schemaId = "schemaId-697673060";
      Schema response = schemaServiceClient.createSchema(parent, schema, schemaId);
    }
  }
}
