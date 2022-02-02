package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Schema;

public class ListSchemasStringIterateAll {

  public static void main(String[] args) throws Exception {
    listSchemasStringIterateAll();
  }

  public static void listSchemasStringIterateAll() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      for (Schema element : schemaServiceClient.listSchemas(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
