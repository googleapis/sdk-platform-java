package com.google.cloud.iam.credentials.v1.samples;

import com.google.cloud.iam.credentials.v1.GenerateAccessTokenRequest;
import com.google.cloud.iam.credentials.v1.GenerateAccessTokenResponse;
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import com.google.protobuf.Duration;
import java.util.ArrayList;

public class GenerateAccessTokenGenerateAccessTokenRequestRequest {

  public static void main(String[] args) throws Exception {
    generateAccessTokenGenerateAccessTokenRequestRequest();
  }

  public static void generateAccessTokenGenerateAccessTokenRequestRequest() throws Exception {
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      GenerateAccessTokenRequest request =
          GenerateAccessTokenRequest.newBuilder()
              .setName(ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]").toString())
              .addAllDelegates(new ArrayList<String>())
              .addAllScope(new ArrayList<String>())
              .setLifetime(Duration.newBuilder().build())
              .build();
      GenerateAccessTokenResponse response = iamCredentialsClient.generateAccessToken(request);
    }
  }
}
