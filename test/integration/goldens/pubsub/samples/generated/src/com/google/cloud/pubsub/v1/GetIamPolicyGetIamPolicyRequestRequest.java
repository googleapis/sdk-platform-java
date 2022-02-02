package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.iam.v1.GetIamPolicyRequest;
import com.google.iam.v1.GetPolicyOptions;
import com.google.iam.v1.Policy;
import com.google.pubsub.v1.ProjectName;

public class GetIamPolicyGetIamPolicyRequestRequest {

  public static void main(String[] args) throws Exception {
    getIamPolicyGetIamPolicyRequestRequest();
  }

  public static void getIamPolicyGetIamPolicyRequestRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      GetIamPolicyRequest request =
          GetIamPolicyRequest.newBuilder()
              .setResource(ProjectName.of("[PROJECT]").toString())
              .setOptions(GetPolicyOptions.newBuilder().build())
              .build();
      Policy response = topicAdminClient.getIamPolicy(request);
    }
  }
}
