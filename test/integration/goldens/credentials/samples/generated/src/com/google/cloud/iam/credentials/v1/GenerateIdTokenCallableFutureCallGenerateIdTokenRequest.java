package com.google.cloud.iam.credentials.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.iam.credentials.v1.GenerateIdTokenRequest;
import com.google.cloud.iam.credentials.v1.GenerateIdTokenResponse;
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import java.util.ArrayList;

public class GenerateIdTokenCallableFutureCallGenerateIdTokenRequest {

  public static void main(String[] args) throws Exception {
    generateIdTokenCallableFutureCallGenerateIdTokenRequest();
  }

  public static void generateIdTokenCallableFutureCallGenerateIdTokenRequest() throws Exception {
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      GenerateIdTokenRequest request =
          GenerateIdTokenRequest.newBuilder()
              .setName(ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]").toString())
              .addAllDelegates(new ArrayList<String>())
              .setAudience("audience975628804")
              .setIncludeEmail(true)
              .build();
      ApiFuture<GenerateIdTokenResponse> future =
          iamCredentialsClient.generateIdTokenCallable().futureCall(request);
      // Do something.
      GenerateIdTokenResponse response = future.get();
    }
  }
}
