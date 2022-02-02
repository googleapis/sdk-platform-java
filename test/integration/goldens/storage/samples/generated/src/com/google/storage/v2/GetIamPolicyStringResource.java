package com.google.storage.v2.samples;

import com.google.iam.v1.Policy;
import com.google.storage.v2.CryptoKeyName;
import com.google.storage.v2.StorageClient;

public class GetIamPolicyStringResource {

  public static void main(String[] args) throws Exception {
    getIamPolicyStringResource();
  }

  public static void getIamPolicyStringResource() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String resource =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]").toString();
      Policy response = storageClient.getIamPolicy(resource);
    }
  }
}
