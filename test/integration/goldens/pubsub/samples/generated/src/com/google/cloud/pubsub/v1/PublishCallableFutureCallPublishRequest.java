package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.PublishRequest;
import com.google.pubsub.v1.PublishResponse;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import java.util.ArrayList;

public class PublishCallableFutureCallPublishRequest {

  public static void main(String[] args) throws Exception {
    publishCallableFutureCallPublishRequest();
  }

  public static void publishCallableFutureCallPublishRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      PublishRequest request =
          PublishRequest.newBuilder()
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .addAllMessages(new ArrayList<PubsubMessage>())
              .build();
      ApiFuture<PublishResponse> future = topicAdminClient.publishCallable().futureCall(request);
      // Do something.
      PublishResponse response = future.get();
    }
  }
}
