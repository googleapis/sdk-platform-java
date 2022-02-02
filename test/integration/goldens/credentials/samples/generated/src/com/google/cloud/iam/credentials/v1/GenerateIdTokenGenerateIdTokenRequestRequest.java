package com.google.cloud.iam.credentials.v1.samples;

import com.google.cloud.iam.credentials.v1.GenerateIdTokenRequest;
import com.google.cloud.iam.credentials.v1.GenerateIdTokenResponse;
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import java.util.ArrayList;

public class GenerateIdTokenGenerateIdTokenRequestRequest {

  public static void main(String[] args) throws Exception {
    generateIdTokenGenerateIdTokenRequestRequest();
  }

  public static void generateIdTokenGenerateIdTokenRequestRequest() throws Exception {
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      GenerateIdTokenRequest request =
          GenerateIdTokenRequest.newBuilder()
              .setName(ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]").toString())
              .addAllDelegates(new ArrayList<String>())
              .setAudience("audience975628804")
              .setIncludeEmail(true)
              .build();
      GenerateIdTokenResponse response = iamCredentialsClient.generateIdToken(request);
    }
  }
}
