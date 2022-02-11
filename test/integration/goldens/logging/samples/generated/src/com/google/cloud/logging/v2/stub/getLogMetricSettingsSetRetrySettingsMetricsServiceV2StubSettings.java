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
package com.google.cloud.logging.v2.stub.samples;

// [START 1.0_10_generated_metricsservicev2stubsettings_getlogmetric_settingssetretrysettingsmetricsservicev2stubsettings]
import com.google.cloud.logging.v2.stub.MetricsServiceV2StubSettings;
import java.time.Duration;

public class GetLogMetricSettingsSetRetrySettingsMetricsServiceV2StubSettings {

  public static void main(String[] args) throws Exception {
    getLogMetricSettingsSetRetrySettingsMetricsServiceV2StubSettings();
  }

  public static void getLogMetricSettingsSetRetrySettingsMetricsServiceV2StubSettings()
      throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    MetricsServiceV2StubSettings.Builder metricsSettingsBuilder =
        MetricsServiceV2StubSettings.newBuilder();
    metricsSettingsBuilder
        .getLogMetricSettings()
        .setRetrySettings(
            metricsSettingsBuilder
                .getLogMetricSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    MetricsServiceV2StubSettings metricsSettings = metricsSettingsBuilder.build();
  }
}
// [END 1.0_10_generated_metricsservicev2stubsettings_getlogmetric_settingssetretrysettingsmetricsservicev2stubsettings]