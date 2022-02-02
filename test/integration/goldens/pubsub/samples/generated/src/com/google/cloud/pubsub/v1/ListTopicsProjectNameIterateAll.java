package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Topic;

public class ListTopicsProjectNameIterateAll {

  public static void main(String[] args) throws Exception {
    listTopicsProjectNameIterateAll();
  }

  public static void listTopicsProjectNameIterateAll() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      ProjectName project = ProjectName.of("[PROJECT]");
      for (Topic element : topicAdminClient.listTopics(project).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
