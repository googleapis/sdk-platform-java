package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.iam.v1.GetIamPolicyRequest;
import com.google.iam.v1.GetPolicyOptions;
import com.google.iam.v1.Policy;

public class GetIamPolicyGetIamPolicyRequestRequest {

  public static void main(String[] args) throws Exception {
    getIamPolicyGetIamPolicyRequestRequest();
  }

  public static void getIamPolicyGetIamPolicyRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      GetIamPolicyRequest request =
          GetIamPolicyRequest.newBuilder()
              .setResource(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setOptions(GetPolicyOptions.newBuilder().build())
              .build();
      Policy response = keyManagementServiceClient.getIamPolicy(request);
    }
  }
}
