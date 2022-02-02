package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.SchemaName;

public class DeleteSchemaStringName {

  public static void main(String[] args) throws Exception {
    deleteSchemaStringName();
  }

  public static void deleteSchemaStringName() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      String name = SchemaName.of("[PROJECT]", "[SCHEMA]").toString();
      schemaServiceClient.deleteSchema(name);
    }
  }
}
