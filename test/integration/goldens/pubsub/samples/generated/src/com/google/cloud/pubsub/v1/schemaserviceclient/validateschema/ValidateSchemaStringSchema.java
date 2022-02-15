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

// [START pubsub_v1_generated_schemaserviceclient_validateschema_stringschema]
import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Schema;
import com.google.pubsub.v1.ValidateSchemaResponse;

public class ValidateSchemaStringSchema {

  public static void main(String[] args) throws Exception {
    validateSchemaStringSchema();
  }

  public static void validateSchemaStringSchema() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      Schema schema = Schema.newBuilder().build();
      ValidateSchemaResponse response = schemaServiceClient.validateSchema(parent, schema);
    }
  }
}
// [END pubsub_v1_generated_schemaserviceclient_validateschema_stringschema]