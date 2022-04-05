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

package com.google.cloud.compute.v1small.samples;

// [START compute_v1small_generated_regionoperationssettings_get_sync]
import com.google.cloud.compute.v1small.RegionOperationsSettings;
import java.time.Duration;

public class SyncGet {

  public static void main(String[] args) throws Exception {
    syncGet();
  }

  public static void syncGet() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    RegionOperationsSettings.Builder regionOperationsSettingsBuilder =
        RegionOperationsSettings.newBuilder();
    regionOperationsSettingsBuilder
        .getSettings()
        .setRetrySettings(
            regionOperationsSettingsBuilder
                .getSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    RegionOperationsSettings regionOperationsSettings = regionOperationsSettingsBuilder.build();
  }
}
// [END compute_v1small_generated_regionoperationssettings_get_sync]
