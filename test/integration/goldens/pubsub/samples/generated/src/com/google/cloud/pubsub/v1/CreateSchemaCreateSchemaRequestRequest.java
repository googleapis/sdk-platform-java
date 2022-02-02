package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.CreateSchemaRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Schema;

public class CreateSchemaCreateSchemaRequestRequest {

  public static void main(String[] args) throws Exception {
    createSchemaCreateSchemaRequestRequest();
  }

  public static void createSchemaCreateSchemaRequestRequest() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      CreateSchemaRequest request =
          CreateSchemaRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setSchema(Schema.newBuilder().build())
              .setSchemaId("schemaId-697673060")
              .build();
      Schema response = schemaServiceClient.createSchema(request);
    }
  }
}
