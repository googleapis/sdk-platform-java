package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.DeleteSchemaRequest;
import com.google.pubsub.v1.SchemaName;

public class DeleteSchemaDeleteSchemaRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteSchemaDeleteSchemaRequestRequest();
  }

  public static void deleteSchemaDeleteSchemaRequestRequest() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      DeleteSchemaRequest request =
          DeleteSchemaRequest.newBuilder()
              .setName(SchemaName.of("[PROJECT]", "[SCHEMA]").toString())
              .build();
      schemaServiceClient.deleteSchema(request);
    }
  }
}
