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
// [END REGION TAG]