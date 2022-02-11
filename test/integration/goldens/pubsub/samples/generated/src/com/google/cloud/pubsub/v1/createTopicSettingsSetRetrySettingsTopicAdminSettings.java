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
package com.google.cloud.pubsub.v1.samples;

// [START 1.0_10_generated_topicadminsettings_createtopic_settingssetretrysettingstopicadminsettings]
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import java.time.Duration;

public class CreateTopicSettingsSetRetrySettingsTopicAdminSettings {

  public static void main(String[] args) throws Exception {
    createTopicSettingsSetRetrySettingsTopicAdminSettings();
  }

  public static void createTopicSettingsSetRetrySettingsTopicAdminSettings() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    TopicAdminSettings.Builder topicAdminSettingsBuilder = TopicAdminSettings.newBuilder();
    topicAdminSettingsBuilder
        .createTopicSettings()
        .setRetrySettings(
            topicAdminSettingsBuilder
                .createTopicSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    TopicAdminSettings topicAdminSettings = topicAdminSettingsBuilder.build();
  }
}
// [END 1.0_10_generated_topicadminsettings_createtopic_settingssetretrysettingstopicadminsettings]