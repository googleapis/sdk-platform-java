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
package com.google.cloud.redis.v1beta1.stub.samples;

// [START v1beta1_redis_generated_cloudredisstubsettings_getinstance_settingssetretrysettingscloudredisstubsettings]
import com.google.cloud.redis.v1beta1.stub.CloudRedisStubSettings;
import java.time.Duration;

public class GetInstanceSettingsSetRetrySettingsCloudRedisStubSettings {

  public static void main(String[] args) throws Exception {
    getInstanceSettingsSetRetrySettingsCloudRedisStubSettings();
  }

  public static void getInstanceSettingsSetRetrySettingsCloudRedisStubSettings() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    CloudRedisStubSettings.Builder cloudRedisSettingsBuilder = CloudRedisStubSettings.newBuilder();
    cloudRedisSettingsBuilder
        .getInstanceSettings()
        .setRetrySettings(
            cloudRedisSettingsBuilder
                .getInstanceSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    CloudRedisStubSettings cloudRedisSettings = cloudRedisSettingsBuilder.build();
  }
}
// [END v1beta1_redis_generated_cloudredisstubsettings_getinstance_settingssetretrysettingscloudredisstubsettings]