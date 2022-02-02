package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Topic;

public class ListTopicsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listTopicsStringIterateAll();
  }

  public static void listTopicsStringIterateAll() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      String project = ProjectName.of("[PROJECT]").toString();
      for (Topic element : topicAdminClient.listTopics(project).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
