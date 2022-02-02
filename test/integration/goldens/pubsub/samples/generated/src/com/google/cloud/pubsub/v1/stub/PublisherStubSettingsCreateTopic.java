package com.google.cloud.pubsub.v1.stub.samples;

import com.google.cloud.pubsub.v1.stub.PublisherStubSettings;
import java.time.Duration;

public class PublisherStubSettingsCreateTopic {

  public static void main(String[] args) throws Exception {
    publisherStubSettingsCreateTopic();
  }

  public static void publisherStubSettingsCreateTopic() throws Exception {
    PublisherStubSettings.Builder topicAdminSettingsBuilder = PublisherStubSettings.newBuilder();
    topicAdminSettingsBuilder
        .createTopicSettings()
        .setRetrySettings(
            topicAdminSettingsBuilder
                .createTopicSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    PublisherStubSettings topicAdminSettings = topicAdminSettingsBuilder.build();
  }
}
