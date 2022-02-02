package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.SchemaName;

public class DeleteSchemaSchemaNameName {

  public static void main(String[] args) throws Exception {
    deleteSchemaSchemaNameName();
  }

  public static void deleteSchemaSchemaNameName() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      SchemaName name = SchemaName.of("[PROJECT]", "[SCHEMA]");
      schemaServiceClient.deleteSchema(name);
    }
  }
}
