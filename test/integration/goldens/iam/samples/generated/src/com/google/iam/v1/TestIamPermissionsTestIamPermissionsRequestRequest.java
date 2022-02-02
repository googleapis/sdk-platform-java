package com.google.iam.v1.samples;

import com.google.iam.v1.IAMPolicyClient;
import com.google.iam.v1.TestIamPermissionsRequest;
import com.google.iam.v1.TestIamPermissionsResponse;
import java.util.ArrayList;

public class TestIamPermissionsTestIamPermissionsRequestRequest {

  public static void main(String[] args) throws Exception {
    testIamPermissionsTestIamPermissionsRequestRequest();
  }

  public static void testIamPermissionsTestIamPermissionsRequestRequest() throws Exception {
    try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
      TestIamPermissionsRequest request =
          TestIamPermissionsRequest.newBuilder()
              .setResource("TestIamPermissionsRequest942398222".toString())
              .addAllPermissions(new ArrayList<String>())
              .build();
      TestIamPermissionsResponse response = iAMPolicyClient.testIamPermissions(request);
    }
  }
}
