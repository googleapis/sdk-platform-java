package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.DeleteTopicRequest;
import com.google.pubsub.v1.TopicName;

public class DeleteTopicCallableFutureCallDeleteTopicRequest {

  public static void main(String[] args) throws Exception {
    deleteTopicCallableFutureCallDeleteTopicRequest();
  }

  public static void deleteTopicCallableFutureCallDeleteTopicRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      DeleteTopicRequest request =
          DeleteTopicRequest.newBuilder()
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .build();
      ApiFuture<Empty> future = topicAdminClient.deleteTopicCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
