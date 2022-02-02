package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.protobuf.FieldMask;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.UpdateTopicRequest;

public class UpdateTopicCallableFutureCallUpdateTopicRequest {

  public static void main(String[] args) throws Exception {
    updateTopicCallableFutureCallUpdateTopicRequest();
  }

  public static void updateTopicCallableFutureCallUpdateTopicRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      UpdateTopicRequest request =
          UpdateTopicRequest.newBuilder()
              .setTopic(Topic.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<Topic> future = topicAdminClient.updateTopicCallable().futureCall(request);
      // Do something.
      Topic response = future.get();
    }
  }
}
