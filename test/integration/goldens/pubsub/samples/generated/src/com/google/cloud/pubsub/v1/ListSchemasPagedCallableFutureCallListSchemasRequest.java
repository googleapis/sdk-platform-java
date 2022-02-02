package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.ListSchemasRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Schema;
import com.google.pubsub.v1.SchemaView;

public class ListSchemasPagedCallableFutureCallListSchemasRequest {

  public static void main(String[] args) throws Exception {
    listSchemasPagedCallableFutureCallListSchemasRequest();
  }

  public static void listSchemasPagedCallableFutureCallListSchemasRequest() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      ListSchemasRequest request =
          ListSchemasRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setView(SchemaView.forNumber(0))
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      ApiFuture<Schema> future = schemaServiceClient.listSchemasPagedCallable().futureCall(request);
      // Do something.
      for (Schema element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
