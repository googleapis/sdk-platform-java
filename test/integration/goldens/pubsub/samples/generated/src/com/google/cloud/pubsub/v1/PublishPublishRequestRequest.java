package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.PublishRequest;
import com.google.pubsub.v1.PublishResponse;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import java.util.ArrayList;

public class PublishPublishRequestRequest {

  public static void main(String[] args) throws Exception {
    publishPublishRequestRequest();
  }

  public static void publishPublishRequestRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      PublishRequest request =
          PublishRequest.newBuilder()
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .addAllMessages(new ArrayList<PubsubMessage>())
              .build();
      PublishResponse response = topicAdminClient.publish(request);
    }
  }
}
