package com.google.iam.v1.samples;

import com.google.iam.v1.GetIamPolicyRequest;
import com.google.iam.v1.GetPolicyOptions;
import com.google.iam.v1.IAMPolicyClient;
import com.google.iam.v1.Policy;

public class GetIamPolicyGetIamPolicyRequestRequest {

  public static void main(String[] args) throws Exception {
    getIamPolicyGetIamPolicyRequestRequest();
  }

  public static void getIamPolicyGetIamPolicyRequestRequest() throws Exception {
    try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
      GetIamPolicyRequest request =
          GetIamPolicyRequest.newBuilder()
              .setResource("GetIamPolicyRequest-1527610370".toString())
              .setOptions(GetPolicyOptions.newBuilder().build())
              .build();
      Policy response = iAMPolicyClient.getIamPolicy(request);
    }
  }
}
