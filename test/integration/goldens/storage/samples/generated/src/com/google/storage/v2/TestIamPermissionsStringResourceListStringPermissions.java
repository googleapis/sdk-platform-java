package com.google.storage.v2.samples;

import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.storage.v2.CryptoKeyName;
import com.google.storage.v2.StorageClient;
import java.util.ArrayList;
import java.util.List;

public class TestIamPermissionsStringResourceListStringPermissions {

  public static void main(String[] args) throws Exception {
    testIamPermissionsStringResourceListStringPermissions();
  }

  public static void testIamPermissionsStringResourceListStringPermissions() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String resource =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]").toString();
      List<String> permissions = new ArrayList<>();
      TestIamPermissionsResponse response = storageClient.testIamPermissions(resource, permissions);
    }
  }
}
