package com.google.storage.v2.samples;

import com.google.iam.v1.TestIamPermissionsRequest;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.storage.v2.CryptoKeyName;
import com.google.storage.v2.StorageClient;
import java.util.ArrayList;

public class TestIamPermissionsTestIamPermissionsRequestRequest {

  public static void main(String[] args) throws Exception {
    testIamPermissionsTestIamPermissionsRequestRequest();
  }

  public static void testIamPermissionsTestIamPermissionsRequestRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      TestIamPermissionsRequest request =
          TestIamPermissionsRequest.newBuilder()
              .setResource(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .addAllPermissions(new ArrayList<String>())
              .build();
      TestIamPermissionsResponse response = storageClient.testIamPermissions(request);
    }
  }
}
