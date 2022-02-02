package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.iam.v1.GetIamPolicyRequest;
import com.google.iam.v1.GetPolicyOptions;
import com.google.iam.v1.Policy;
import com.google.pubsub.v1.ProjectName;

public class GetIamPolicyCallableFutureCallGetIamPolicyRequest {

  public static void main(String[] args) throws Exception {
    getIamPolicyCallableFutureCallGetIamPolicyRequest();
  }

  public static void getIamPolicyCallableFutureCallGetIamPolicyRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      GetIamPolicyRequest request =
          GetIamPolicyRequest.newBuilder()
              .setResource(ProjectName.of("[PROJECT]").toString())
              .setOptions(GetPolicyOptions.newBuilder().build())
              .build();
      ApiFuture<Policy> future = topicAdminClient.getIamPolicyCallable().futureCall(request);
      // Do something.
      Policy response = future.get();
    }
  }
}
