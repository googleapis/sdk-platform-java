package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;
import com.google.pubsub.v1.ProjectName;

public class SetIamPolicySetIamPolicyRequestRequest {

  public static void main(String[] args) throws Exception {
    setIamPolicySetIamPolicyRequestRequest();
  }

  public static void setIamPolicySetIamPolicyRequestRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      SetIamPolicyRequest request =
          SetIamPolicyRequest.newBuilder()
              .setResource(ProjectName.of("[PROJECT]").toString())
              .setPolicy(Policy.newBuilder().build())
              .build();
      Policy response = topicAdminClient.setIamPolicy(request);
    }
  }
}
