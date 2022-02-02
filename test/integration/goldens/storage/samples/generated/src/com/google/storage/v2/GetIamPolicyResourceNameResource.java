package com.google.storage.v2.samples;

import com.google.api.resourcenames.ResourceName;
import com.google.iam.v1.Policy;
import com.google.storage.v2.CryptoKeyName;
import com.google.storage.v2.StorageClient;

public class GetIamPolicyResourceNameResource {

  public static void main(String[] args) throws Exception {
    getIamPolicyResourceNameResource();
  }

  public static void getIamPolicyResourceNameResource() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ResourceName resource =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]");
      Policy response = storageClient.getIamPolicy(resource);
    }
  }
}
