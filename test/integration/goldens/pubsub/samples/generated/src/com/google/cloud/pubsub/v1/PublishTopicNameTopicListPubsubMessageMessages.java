package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.PublishResponse;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import java.util.ArrayList;
import java.util.List;

public class PublishTopicNameTopicListPubsubMessageMessages {

  public static void main(String[] args) throws Exception {
    publishTopicNameTopicListPubsubMessageMessages();
  }

  public static void publishTopicNameTopicListPubsubMessageMessages() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      TopicName topic = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]");
      List<PubsubMessage> messages = new ArrayList<>();
      PublishResponse response = topicAdminClient.publish(topic, messages);
    }
  }
}
