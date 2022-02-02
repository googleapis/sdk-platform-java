package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.cloud.pubsub.v1.myEndpoint;

public class CreateTopicAdminSettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createTopicAdminSettingsSetEndpoint();
  }

  public static void createTopicAdminSettingsSetEndpoint() throws Exception {
    TopicAdminSettings topicAdminSettings =
        TopicAdminSettings.newBuilder().setEndpoint(myEndpoint).build();
    TopicAdminClient topicAdminClient = TopicAdminClient.create(topicAdminSettings);
  }
}
