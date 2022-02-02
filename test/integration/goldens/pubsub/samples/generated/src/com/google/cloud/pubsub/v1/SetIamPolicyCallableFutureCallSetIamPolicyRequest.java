package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;
import com.google.pubsub.v1.ProjectName;

public class SetIamPolicyCallableFutureCallSetIamPolicyRequest {

  public static void main(String[] args) throws Exception {
    setIamPolicyCallableFutureCallSetIamPolicyRequest();
  }

  public static void setIamPolicyCallableFutureCallSetIamPolicyRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      SetIamPolicyRequest request =
          SetIamPolicyRequest.newBuilder()
              .setResource(ProjectName.of("[PROJECT]").toString())
              .setPolicy(Policy.newBuilder().build())
              .build();
      ApiFuture<Policy> future = topicAdminClient.setIamPolicyCallable().futureCall(request);
      // Do something.
      Policy response = future.get();
    }
  }
}
