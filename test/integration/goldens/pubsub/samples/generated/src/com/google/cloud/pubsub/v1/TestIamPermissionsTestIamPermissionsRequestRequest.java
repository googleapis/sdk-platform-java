package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.iam.v1.TestIamPermissionsRequest;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.pubsub.v1.ProjectName;
import java.util.ArrayList;

public class TestIamPermissionsTestIamPermissionsRequestRequest {

  public static void main(String[] args) throws Exception {
    testIamPermissionsTestIamPermissionsRequestRequest();
  }

  public static void testIamPermissionsTestIamPermissionsRequestRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      TestIamPermissionsRequest request =
          TestIamPermissionsRequest.newBuilder()
              .setResource(ProjectName.of("[PROJECT]").toString())
              .addAllPermissions(new ArrayList<String>())
              .build();
      TestIamPermissionsResponse response = topicAdminClient.testIamPermissions(request);
    }
  }
}
