package com.google.iam.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.iam.v1.GetIamPolicyRequest;
import com.google.iam.v1.GetPolicyOptions;
import com.google.iam.v1.IAMPolicyClient;
import com.google.iam.v1.Policy;

public class GetIamPolicyCallableFutureCallGetIamPolicyRequest {

  public static void main(String[] args) throws Exception {
    getIamPolicyCallableFutureCallGetIamPolicyRequest();
  }

  public static void getIamPolicyCallableFutureCallGetIamPolicyRequest() throws Exception {
    try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
      GetIamPolicyRequest request =
          GetIamPolicyRequest.newBuilder()
              .setResource("GetIamPolicyRequest-1527610370".toString())
              .setOptions(GetPolicyOptions.newBuilder().build())
              .build();
      ApiFuture<Policy> future = iAMPolicyClient.getIamPolicyCallable().futureCall(request);
      // Do something.
      Policy response = future.get();
    }
  }
}
