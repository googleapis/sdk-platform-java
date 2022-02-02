package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.ListSchemasRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Schema;
import com.google.pubsub.v1.SchemaView;

public class ListSchemasListSchemasRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listSchemasListSchemasRequestIterateAll();
  }

  public static void listSchemasListSchemasRequestIterateAll() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      ListSchemasRequest request =
          ListSchemasRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setView(SchemaView.forNumber(0))
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      for (Schema element : schemaServiceClient.listSchemas(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
