package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.Schema;
import com.google.pubsub.v1.SchemaName;

public class GetSchemaSchemaNameName {

  public static void main(String[] args) throws Exception {
    getSchemaSchemaNameName();
  }

  public static void getSchemaSchemaNameName() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      SchemaName name = SchemaName.of("[PROJECT]", "[SCHEMA]");
      Schema response = schemaServiceClient.getSchema(name);
    }
  }
}
