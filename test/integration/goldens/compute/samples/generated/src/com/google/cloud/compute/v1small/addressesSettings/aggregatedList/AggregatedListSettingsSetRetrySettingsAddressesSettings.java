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
package com.google.cloud.compute.v1small.samples;

// [START compute_v1small_generated_addressessettings_aggregatedlist_settingssetretrysettingsaddressessettings]
import com.google.cloud.compute.v1small.AddressesSettings;
import java.time.Duration;

public class AggregatedListSettingsSetRetrySettingsAddressesSettings {

  public static void main(String[] args) throws Exception {
    aggregatedListSettingsSetRetrySettingsAddressesSettings();
  }

  public static void aggregatedListSettingsSetRetrySettingsAddressesSettings() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    AddressesSettings.Builder addressesSettingsBuilder = AddressesSettings.newBuilder();
    addressesSettingsBuilder
        .aggregatedListSettings()
        .setRetrySettings(
            addressesSettingsBuilder
                .aggregatedListSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    AddressesSettings addressesSettings = addressesSettingsBuilder.build();
  }
}
// [END compute_v1small_generated_addressessettings_aggregatedlist_settingssetretrysettingsaddressessettings]