package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.iam.v1.TestIamPermissionsRequest;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.pubsub.v1.ProjectName;
import java.util.ArrayList;

public class TestIamPermissionsCallableFutureCallTestIamPermissionsRequest {

  public static void main(String[] args) throws Exception {
    testIamPermissionsCallableFutureCallTestIamPermissionsRequest();
  }

  public static void testIamPermissionsCallableFutureCallTestIamPermissionsRequest()
      throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      TestIamPermissionsRequest request =
          TestIamPermissionsRequest.newBuilder()
              .setResource(ProjectName.of("[PROJECT]").toString())
              .addAllPermissions(new ArrayList<String>())
              .build();
      ApiFuture<TestIamPermissionsResponse> future =
          topicAdminClient.testIamPermissionsCallable().futureCall(request);
      // Do something.
      TestIamPermissionsResponse response = future.get();
    }
  }
}
