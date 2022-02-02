package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Schema;
import com.google.pubsub.v1.ValidateSchemaRequest;
import com.google.pubsub.v1.ValidateSchemaResponse;

public class ValidateSchemaValidateSchemaRequestRequest {

  public static void main(String[] args) throws Exception {
    validateSchemaValidateSchemaRequestRequest();
  }

  public static void validateSchemaValidateSchemaRequestRequest() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      ValidateSchemaRequest request =
          ValidateSchemaRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setSchema(Schema.newBuilder().build())
              .build();
      ValidateSchemaResponse response = schemaServiceClient.validateSchema(request);
    }
  }
}
