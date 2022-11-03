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

package com.google.cloud.logging.v2.stub.samples;

// [START logging_v2_generated_configservicev2stubsettings_getbucket_sync]
import com.google.cloud.logging.v2.stub.ConfigServiceV2StubSettings;
import java.time.Duration;

public class SyncGetBucket {

  public static void main(String[] args) throws Exception {
    syncGetBucket();
  }

  public static void syncGetBucket() throws Exception {
    // This snippet has been automatically generated and should be regarded as a code template only.
    // It will require modifications to work:
    // - It may require correct/in-range values for request initialization.
    // - It may require specifying regional endpoints when creating the service client as shown in
    // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
    ConfigServiceV2StubSettings.Builder configSettingsBuilder =
        ConfigServiceV2StubSettings.newBuilder();
    configSettingsBuilder
        .getBucketSettings()
        .setRetrySettings(
            configSettingsBuilder
                .getBucketSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    ConfigServiceV2StubSettings configSettings = configSettingsBuilder.build();
  }
}
// [END logging_v2_generated_configservicev2stubsettings_getbucket_sync]
