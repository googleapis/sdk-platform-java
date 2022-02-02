package com.google.cloud.pubsub.v1.samples;

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
