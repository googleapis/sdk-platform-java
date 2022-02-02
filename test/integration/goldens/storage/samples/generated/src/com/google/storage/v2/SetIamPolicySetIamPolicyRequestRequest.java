package com.google.storage.v2.samples;

import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;
import com.google.storage.v2.CryptoKeyName;
import com.google.storage.v2.StorageClient;

public class SetIamPolicySetIamPolicyRequestRequest {

  public static void main(String[] args) throws Exception {
    setIamPolicySetIamPolicyRequestRequest();
  }

  public static void setIamPolicySetIamPolicyRequestRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      SetIamPolicyRequest request =
          SetIamPolicyRequest.newBuilder()
              .setResource(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setPolicy(Policy.newBuilder().build())
              .build();
      Policy response = storageClient.setIamPolicy(request);
    }
  }
}
