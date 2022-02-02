package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.protobuf.Duration;
import com.google.pubsub.v1.MessageStoragePolicy;
import com.google.pubsub.v1.SchemaSettings;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;
import java.util.HashMap;

public class CreateTopicCallableFutureCallTopic {

  public static void main(String[] args) throws Exception {
    createTopicCallableFutureCallTopic();
  }

  public static void createTopicCallableFutureCallTopic() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      Topic request =
          Topic.newBuilder()
              .setName(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .putAllLabels(new HashMap<String, String>())
              .setMessageStoragePolicy(MessageStoragePolicy.newBuilder().build())
              .setKmsKeyName("kmsKeyName412586233")
              .setSchemaSettings(SchemaSettings.newBuilder().build())
              .setSatisfiesPzs(true)
              .setMessageRetentionDuration(Duration.newBuilder().build())
              .build();
      ApiFuture<Topic> future = topicAdminClient.createTopicCallable().futureCall(request);
      // Do something.
      Topic response = future.get();
    }
  }
}
