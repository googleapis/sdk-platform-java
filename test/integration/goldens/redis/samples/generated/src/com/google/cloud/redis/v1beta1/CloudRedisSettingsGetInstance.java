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
package com.google.cloud.redis.v1beta1.samples;

// [START REGION TAG]
import com.google.cloud.redis.v1beta1.CloudRedisSettings;
import java.time.Duration;

public class CloudRedisSettingsGetInstance {

  public static void main(String[] args) throws Exception {
    cloudRedisSettingsGetInstance();
  }

  public static void cloudRedisSettingsGetInstance() throws Exception {
    CloudRedisSettings.Builder cloudRedisSettingsBuilder = CloudRedisSettings.newBuilder();
    cloudRedisSettingsBuilder
        .getInstanceSettings()
        .setRetrySettings(
            cloudRedisSettingsBuilder
                .getInstanceSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    CloudRedisSettings cloudRedisSettings = cloudRedisSettingsBuilder.build();
  }
}
// [END REGION TAG]