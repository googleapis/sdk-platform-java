package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;
import com.google.storage.v2.CryptoKeyName;
import com.google.storage.v2.StorageClient;

public class SetIamPolicyCallableFutureCallSetIamPolicyRequest {

  public static void main(String[] args) throws Exception {
    setIamPolicyCallableFutureCallSetIamPolicyRequest();
  }

  public static void setIamPolicyCallableFutureCallSetIamPolicyRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      SetIamPolicyRequest request =
          SetIamPolicyRequest.newBuilder()
              .setResource(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setPolicy(Policy.newBuilder().build())
              .build();
      ApiFuture<Policy> future = storageClient.setIamPolicyCallable().futureCall(request);
      // Do something.
      Policy response = future.get();
    }
  }
}
