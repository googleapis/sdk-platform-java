/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.pubsub.v1.samples;

// [START REGION TAG]
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
// [END REGION TAG]