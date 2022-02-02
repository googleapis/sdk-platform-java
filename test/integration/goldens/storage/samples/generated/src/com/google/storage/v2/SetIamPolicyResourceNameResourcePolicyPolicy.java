package com.google.storage.v2.samples;

import com.google.api.resourcenames.ResourceName;
import com.google.iam.v1.Policy;
import com.google.storage.v2.CryptoKeyName;
import com.google.storage.v2.StorageClient;

public class SetIamPolicyResourceNameResourcePolicyPolicy {

  public static void main(String[] args) throws Exception {
    setIamPolicyResourceNameResourcePolicyPolicy();
  }

  public static void setIamPolicyResourceNameResourcePolicyPolicy() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ResourceName resource =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]");
      Policy policy = Policy.newBuilder().build();
      Policy response = storageClient.setIamPolicy(resource, policy);
    }
  }
}
