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
package com.google.cloud.compute.v1small.stub.samples;

// [START 1.0_10_generated_regionoperationsstubsettings_get_settingssetretrysettingsregionoperationsstubsettings]
import com.google.cloud.compute.v1small.stub.RegionOperationsStubSettings;
import java.time.Duration;

public class GetSettingsSetRetrySettingsRegionOperationsStubSettings {

  public static void main(String[] args) throws Exception {
    getSettingsSetRetrySettingsRegionOperationsStubSettings();
  }

  public static void getSettingsSetRetrySettingsRegionOperationsStubSettings() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    RegionOperationsStubSettings.Builder regionOperationsSettingsBuilder =
        RegionOperationsStubSettings.newBuilder();
    regionOperationsSettingsBuilder
        .getSettings()
        .setRetrySettings(
            regionOperationsSettingsBuilder
                .getSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    RegionOperationsStubSettings regionOperationsSettings = regionOperationsSettingsBuilder.build();
  }
}
// [END 1.0_10_generated_regionoperationsstubsettings_get_settingssetretrysettingsregionoperationsstubsettings]