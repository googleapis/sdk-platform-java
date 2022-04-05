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

package com.google.cloud.pubsub.v1.stub.samples;

// [START pubsub_v1_generated_schemaservicestubsettings_createschema_sync]
import com.google.cloud.pubsub.v1.stub.SchemaServiceStubSettings;
import java.time.Duration;

public class SyncCreateSchema {

  public static void main(String[] args) throws Exception {
    syncCreateSchema();
  }

  public static void syncCreateSchema() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    SchemaServiceStubSettings.Builder schemaServiceSettingsBuilder =
        SchemaServiceStubSettings.newBuilder();
    schemaServiceSettingsBuilder
        .createSchemaSettings()
        .setRetrySettings(
            schemaServiceSettingsBuilder
                .createSchemaSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    SchemaServiceStubSettings schemaServiceSettings = schemaServiceSettingsBuilder.build();
  }
}
// [END pubsub_v1_generated_schemaservicestubsettings_createschema_sync]
