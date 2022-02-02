package com.google.storage.v2.samples;

import com.google.api.resourcenames.ResourceName;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.storage.v2.CryptoKeyName;
import com.google.storage.v2.StorageClient;
import java.util.ArrayList;
import java.util.List;

public class TestIamPermissionsResourceNameResourceListStringPermissions {

  public static void main(String[] args) throws Exception {
    testIamPermissionsResourceNameResourceListStringPermissions();
  }

  public static void testIamPermissionsResourceNameResourceListStringPermissions()
      throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ResourceName resource =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]");
      List<String> permissions = new ArrayList<>();
      TestIamPermissionsResponse response = storageClient.testIamPermissions(resource, permissions);
    }
  }
}
