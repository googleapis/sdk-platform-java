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

// [START REGION TAG]
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ListTopicsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Topic;

public class ListTopicsListTopicsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listTopicsListTopicsRequestIterateAll();
  }

  public static void listTopicsListTopicsRequestIterateAll() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      ListTopicsRequest request =
          ListTopicsRequest.newBuilder()
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      for (Topic element : topicAdminClient.listTopics(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
// [END REGION TAG]