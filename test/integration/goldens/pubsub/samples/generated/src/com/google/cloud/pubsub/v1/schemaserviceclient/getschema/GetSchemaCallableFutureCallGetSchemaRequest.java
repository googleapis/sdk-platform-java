/*
 * Copyright 2022 Google LLC
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

// [START pubsub_v1_generated_schemaserviceclient_getschema_callablefuturecallgetschemarequest_sync]
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
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
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
// [END pubsub_v1_generated_schemaserviceclient_getschema_callablefuturecallgetschemarequest_sync]
