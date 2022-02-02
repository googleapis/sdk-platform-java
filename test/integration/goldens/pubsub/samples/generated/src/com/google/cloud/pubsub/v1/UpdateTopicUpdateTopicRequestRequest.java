package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.protobuf.FieldMask;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.UpdateTopicRequest;

public class UpdateTopicUpdateTopicRequestRequest {

  public static void main(String[] args) throws Exception {
    updateTopicUpdateTopicRequestRequest();
  }

  public static void updateTopicUpdateTopicRequestRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      UpdateTopicRequest request =
          UpdateTopicRequest.newBuilder()
              .setTopic(Topic.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      Topic response = topicAdminClient.updateTopic(request);
    }
  }
}
