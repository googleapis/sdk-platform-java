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
package com.google.cloud.bigtable.data.v2.stub.samples;

// [START bigtable_v2_generated_bigtablestubsettings_mutaterow_settingssetretrysettingsbigtablestubsettings]
import com.google.cloud.bigtable.data.v2.stub.BigtableStubSettings;
import java.time.Duration;

public class MutateRowSettingsSetRetrySettingsBigtableStubSettings {

  public static void main(String[] args) throws Exception {
    mutateRowSettingsSetRetrySettingsBigtableStubSettings();
  }

  public static void mutateRowSettingsSetRetrySettingsBigtableStubSettings() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    BigtableStubSettings.Builder baseBigtableDataSettingsBuilder =
        BigtableStubSettings.newBuilder();
    baseBigtableDataSettingsBuilder
        .mutateRowSettings()
        .setRetrySettings(
            baseBigtableDataSettingsBuilder
                .mutateRowSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    BigtableStubSettings baseBigtableDataSettings = baseBigtableDataSettingsBuilder.build();
  }
}
// [END bigtable_v2_generated_bigtablestubsettings_mutaterow_settingssetretrysettingsbigtablestubsettings]