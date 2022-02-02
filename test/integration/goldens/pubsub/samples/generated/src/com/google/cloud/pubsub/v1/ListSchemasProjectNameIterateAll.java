package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Schema;

public class ListSchemasProjectNameIterateAll {

  public static void main(String[] args) throws Exception {
    listSchemasProjectNameIterateAll();
  }

  public static void listSchemasProjectNameIterateAll() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      for (Schema element : schemaServiceClient.listSchemas(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
