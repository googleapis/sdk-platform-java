/*
 * Copyright 2023 Google LLC
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

package com.google.cloud.apigeeconnect.v1.stub.samples;

// [START apigeeconnect_v1_generated_TetherStubSettings_Egress_sync]
import com.google.cloud.apigeeconnect.v1.stub.TetherStubSettings;
import java.time.Duration;

public class SyncEgress {

  public static void main(String[] args) throws Exception {
    syncEgress();
  }

  public static void syncEgress() throws Exception {
    // This snippet has been automatically generated and should be regarded as a code template only.
    // It will require modifications to work:
    // - It may require correct/in-range values for request initialization.
    // - It may require specifying regional endpoints when creating the service client as shown in
    // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
    TetherStubSettings.Builder tetherSettingsBuilder = TetherStubSettings.newBuilder();
    tetherSettingsBuilder
        .egressSettings()
        .setRetrySettings(
            tetherSettingsBuilder
                .egressSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    TetherStubSettings tetherSettings = tetherSettingsBuilder.build();
  }
}
// [END apigeeconnect_v1_generated_TetherStubSettings_Egress_sync]
