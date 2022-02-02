package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.iam.v1.TestIamPermissionsRequest;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.storage.v2.CryptoKeyName;
import com.google.storage.v2.StorageClient;
import java.util.ArrayList;

public class TestIamPermissionsCallableFutureCallTestIamPermissionsRequest {

  public static void main(String[] args) throws Exception {
    testIamPermissionsCallableFutureCallTestIamPermissionsRequest();
  }

  public static void testIamPermissionsCallableFutureCallTestIamPermissionsRequest()
      throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      TestIamPermissionsRequest request =
          TestIamPermissionsRequest.newBuilder()
              .setResource(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .addAllPermissions(new ArrayList<String>())
              .build();
      ApiFuture<TestIamPermissionsResponse> future =
          storageClient.testIamPermissionsCallable().futureCall(request);
      // Do something.
      TestIamPermissionsResponse response = future.get();
    }
  }
}
