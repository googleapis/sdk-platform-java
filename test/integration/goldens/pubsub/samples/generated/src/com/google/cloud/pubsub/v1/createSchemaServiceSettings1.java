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

// [START pubsub_v1_generated_schemaserviceclient_create_schemaservicesettings1]
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.cloud.pubsub.v1.SchemaServiceSettings;
import com.google.cloud.pubsub.v1.myCredentials;

public class CreateSchemaServiceSettings1 {

  public static void main(String[] args) throws Exception {
    createSchemaServiceSettings1();
  }

  public static void createSchemaServiceSettings1() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    SchemaServiceSettings schemaServiceSettings =
        SchemaServiceSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    SchemaServiceClient schemaServiceClient = SchemaServiceClient.create(schemaServiceSettings);
  }
}
// [END pubsub_v1_generated_schemaserviceclient_create_schemaservicesettings1]