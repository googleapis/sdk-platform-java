package com.google.iam.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.iam.v1.IAMPolicyClient;
import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;

public class SetIamPolicyCallableFutureCallSetIamPolicyRequest {

  public static void main(String[] args) throws Exception {
    setIamPolicyCallableFutureCallSetIamPolicyRequest();
  }

  public static void setIamPolicyCallableFutureCallSetIamPolicyRequest() throws Exception {
    try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
      SetIamPolicyRequest request =
          SetIamPolicyRequest.newBuilder()
              .setResource("SetIamPolicyRequest1223629066".toString())
              .setPolicy(Policy.newBuilder().build())
              .build();
      ApiFuture<Policy> future = iAMPolicyClient.setIamPolicyCallable().futureCall(request);
      // Do something.
      Policy response = future.get();
    }
  }
}
