package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminSettings;
import java.time.Duration;

public class TopicAdminSettingsCreateTopic {

  public static void main(String[] args) throws Exception {
    topicAdminSettingsCreateTopic();
  }

  public static void topicAdminSettingsCreateTopic() throws Exception {
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
