package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.Schema;
import com.google.pubsub.v1.SchemaName;

public class GetSchemaStringName {

  public static void main(String[] args) throws Exception {
    getSchemaStringName();
  }

  public static void getSchemaStringName() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      String name = SchemaName.of("[PROJECT]", "[SCHEMA]").toString();
      Schema response = schemaServiceClient.getSchema(name);
    }
  }
}
