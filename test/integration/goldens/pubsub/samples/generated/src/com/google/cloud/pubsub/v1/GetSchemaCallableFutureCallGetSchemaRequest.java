package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.GetSchemaRequest;
import com.google.pubsub.v1.Schema;
import com.google.pubsub.v1.SchemaName;
import com.google.pubsub.v1.SchemaView;

public class GetSchemaCallableFutureCallGetSchemaRequest {

  public static void main(String[] args) throws Exception {
    getSchemaCallableFutureCallGetSchemaRequest();
  }

  public static void getSchemaCallableFutureCallGetSchemaRequest() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      GetSchemaRequest request =
          GetSchemaRequest.newBuilder()
              .setName(SchemaName.of("[PROJECT]", "[SCHEMA]").toString())
              .setView(SchemaView.forNumber(0))
              .build();
      ApiFuture<Schema> future = schemaServiceClient.getSchemaCallable().futureCall(request);
      // Do something.
      Schema response = future.get();
    }
  }
}
