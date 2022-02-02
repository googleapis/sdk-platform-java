package com.google.cloud.iam.credentials.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.iam.credentials.v1.GenerateAccessTokenRequest;
import com.google.cloud.iam.credentials.v1.GenerateAccessTokenResponse;
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import com.google.protobuf.Duration;
import java.util.ArrayList;

public class GenerateAccessTokenCallableFutureCallGenerateAccessTokenRequest {

  public static void main(String[] args) throws Exception {
    generateAccessTokenCallableFutureCallGenerateAccessTokenRequest();
  }

  public static void generateAccessTokenCallableFutureCallGenerateAccessTokenRequest()
      throws Exception {
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      GenerateAccessTokenRequest request =
          GenerateAccessTokenRequest.newBuilder()
              .setName(ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]").toString())
              .addAllDelegates(new ArrayList<String>())
              .addAllScope(new ArrayList<String>())
              .setLifetime(Duration.newBuilder().build())
              .build();
      ApiFuture<GenerateAccessTokenResponse> future =
          iamCredentialsClient.generateAccessTokenCallable().futureCall(request);
      // Do something.
      GenerateAccessTokenResponse response = future.get();
    }
  }
}
