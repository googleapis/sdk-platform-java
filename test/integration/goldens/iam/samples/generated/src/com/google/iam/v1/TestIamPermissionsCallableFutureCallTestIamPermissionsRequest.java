package com.google.iam.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.iam.v1.IAMPolicyClient;
import com.google.iam.v1.TestIamPermissionsRequest;
import com.google.iam.v1.TestIamPermissionsResponse;
import java.util.ArrayList;

public class TestIamPermissionsCallableFutureCallTestIamPermissionsRequest {

  public static void main(String[] args) throws Exception {
    testIamPermissionsCallableFutureCallTestIamPermissionsRequest();
  }

  public static void testIamPermissionsCallableFutureCallTestIamPermissionsRequest()
      throws Exception {
    try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
      TestIamPermissionsRequest request =
          TestIamPermissionsRequest.newBuilder()
              .setResource("TestIamPermissionsRequest942398222".toString())
              .addAllPermissions(new ArrayList<String>())
              .build();
      ApiFuture<TestIamPermissionsResponse> future =
          iAMPolicyClient.testIamPermissionsCallable().futureCall(request);
      // Do something.
      TestIamPermissionsResponse response = future.get();
    }
  }
}
