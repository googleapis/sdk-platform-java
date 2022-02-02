package com.google.cloud.iam.credentials.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import com.google.cloud.iam.credentials.v1.SignBlobRequest;
import com.google.cloud.iam.credentials.v1.SignBlobResponse;
import com.google.protobuf.ByteString;
import java.util.ArrayList;

public class SignBlobCallableFutureCallSignBlobRequest {

  public static void main(String[] args) throws Exception {
    signBlobCallableFutureCallSignBlobRequest();
  }

  public static void signBlobCallableFutureCallSignBlobRequest() throws Exception {
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      SignBlobRequest request =
          SignBlobRequest.newBuilder()
              .setName(ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]").toString())
              .addAllDelegates(new ArrayList<String>())
              .setPayload(ByteString.EMPTY)
              .build();
      ApiFuture<SignBlobResponse> future =
          iamCredentialsClient.signBlobCallable().futureCall(request);
      // Do something.
      SignBlobResponse response = future.get();
    }
  }
}
