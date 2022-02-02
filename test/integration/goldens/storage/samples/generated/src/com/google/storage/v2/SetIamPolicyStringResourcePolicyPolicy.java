package com.google.storage.v2.samples;

import com.google.iam.v1.Policy;
import com.google.storage.v2.CryptoKeyName;
import com.google.storage.v2.StorageClient;

public class SetIamPolicyStringResourcePolicyPolicy {

  public static void main(String[] args) throws Exception {
    setIamPolicyStringResourcePolicyPolicy();
  }

  public static void setIamPolicyStringResourcePolicyPolicy() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String resource =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]").toString();
      Policy policy = Policy.newBuilder().build();
      Policy response = storageClient.setIamPolicy(resource, policy);
    }
  }
}
