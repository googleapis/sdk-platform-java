package com.google.cloud.iam.credentials.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import com.google.cloud.iam.credentials.v1.SignJwtRequest;
import com.google.cloud.iam.credentials.v1.SignJwtResponse;
import java.util.ArrayList;

public class SignJwtCallableFutureCallSignJwtRequest {

  public static void main(String[] args) throws Exception {
    signJwtCallableFutureCallSignJwtRequest();
  }

  public static void signJwtCallableFutureCallSignJwtRequest() throws Exception {
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      SignJwtRequest request =
          SignJwtRequest.newBuilder()
              .setName(ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]").toString())
              .addAllDelegates(new ArrayList<String>())
              .setPayload("payload-786701938")
              .build();
      ApiFuture<SignJwtResponse> future =
          iamCredentialsClient.signJwtCallable().futureCall(request);
      // Do something.
      SignJwtResponse response = future.get();
    }
  }
}
