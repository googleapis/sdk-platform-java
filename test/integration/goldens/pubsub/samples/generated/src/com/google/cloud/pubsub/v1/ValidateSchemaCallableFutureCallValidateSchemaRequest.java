package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Schema;
import com.google.pubsub.v1.ValidateSchemaRequest;
import com.google.pubsub.v1.ValidateSchemaResponse;

public class ValidateSchemaCallableFutureCallValidateSchemaRequest {

  public static void main(String[] args) throws Exception {
    validateSchemaCallableFutureCallValidateSchemaRequest();
  }

  public static void validateSchemaCallableFutureCallValidateSchemaRequest() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      ValidateSchemaRequest request =
          ValidateSchemaRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setSchema(Schema.newBuilder().build())
              .build();
      ApiFuture<ValidateSchemaResponse> future =
          schemaServiceClient.validateSchemaCallable().futureCall(request);
      // Do something.
      ValidateSchemaResponse response = future.get();
    }
  }
}
