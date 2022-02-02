package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.DeleteSchemaRequest;
import com.google.pubsub.v1.SchemaName;

public class DeleteSchemaCallableFutureCallDeleteSchemaRequest {

  public static void main(String[] args) throws Exception {
    deleteSchemaCallableFutureCallDeleteSchemaRequest();
  }

  public static void deleteSchemaCallableFutureCallDeleteSchemaRequest() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      DeleteSchemaRequest request =
          DeleteSchemaRequest.newBuilder()
              .setName(SchemaName.of("[PROJECT]", "[SCHEMA]").toString())
              .build();
      ApiFuture<Empty> future = schemaServiceClient.deleteSchemaCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
