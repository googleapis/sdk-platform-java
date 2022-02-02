package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.PublishResponse;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import java.util.ArrayList;
import java.util.List;

public class PublishStringTopicListPubsubMessageMessages {

  public static void main(String[] args) throws Exception {
    publishStringTopicListPubsubMessageMessages();
  }

  public static void publishStringTopicListPubsubMessageMessages() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      String topic = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString();
      List<PubsubMessage> messages = new ArrayList<>();
      PublishResponse response = topicAdminClient.publish(topic, messages);
    }
  }
}
