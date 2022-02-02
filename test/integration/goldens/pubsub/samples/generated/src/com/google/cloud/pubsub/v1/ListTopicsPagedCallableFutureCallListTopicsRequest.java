package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ListTopicsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Topic;

public class ListTopicsPagedCallableFutureCallListTopicsRequest {

  public static void main(String[] args) throws Exception {
    listTopicsPagedCallableFutureCallListTopicsRequest();
  }

  public static void listTopicsPagedCallableFutureCallListTopicsRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      ListTopicsRequest request =
          ListTopicsRequest.newBuilder()
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      ApiFuture<Topic> future = topicAdminClient.listTopicsPagedCallable().futureCall(request);
      // Do something.
      for (Topic element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
