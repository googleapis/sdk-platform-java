package com.google.cloud.pubsub.v1.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.cloud.pubsub.v1.myCredentials;

public class CreateTopicAdminSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createTopicAdminSettingsSetCredentialsProvider();
  }

  public static void createTopicAdminSettingsSetCredentialsProvider() throws Exception {
    TopicAdminSettings topicAdminSettings =
        TopicAdminSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    TopicAdminClient topicAdminClient = TopicAdminClient.create(topicAdminSettings);
  }
}
