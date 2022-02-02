package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.iam.v1.GetIamPolicyRequest;
import com.google.iam.v1.GetPolicyOptions;
import com.google.iam.v1.Policy;
import com.google.storage.v2.CryptoKeyName;
import com.google.storage.v2.StorageClient;

public class GetIamPolicyCallableFutureCallGetIamPolicyRequest {

  public static void main(String[] args) throws Exception {
    getIamPolicyCallableFutureCallGetIamPolicyRequest();
  }

  public static void getIamPolicyCallableFutureCallGetIamPolicyRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      GetIamPolicyRequest request =
          GetIamPolicyRequest.newBuilder()
              .setResource(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setOptions(GetPolicyOptions.newBuilder().build())
              .build();
      ApiFuture<Policy> future = storageClient.getIamPolicyCallable().futureCall(request);
      // Do something.
      Policy response = future.get();
    }
  }
}
