package com.google.iam.v1.samples;

import com.google.iam.v1.IAMPolicyClient;
import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;

public class SetIamPolicySetIamPolicyRequestRequest {

  public static void main(String[] args) throws Exception {
    setIamPolicySetIamPolicyRequestRequest();
  }

  public static void setIamPolicySetIamPolicyRequestRequest() throws Exception {
    try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
      SetIamPolicyRequest request =
          SetIamPolicyRequest.newBuilder()
              .setResource("SetIamPolicyRequest1223629066".toString())
              .setPolicy(Policy.newBuilder().build())
              .build();
      Policy response = iAMPolicyClient.setIamPolicy(request);
    }
  }
}
