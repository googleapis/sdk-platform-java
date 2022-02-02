package com.google.cloud.iam.credentials.v1.samples;

import com.google.cloud.iam.credentials.v1.GenerateAccessTokenResponse;
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import com.google.protobuf.Duration;
import java.util.ArrayList;
import java.util.List;

public class GenerateAccessTokenStringNameListStringDelegatesListStringScopeDurationLifetime {

  public static void main(String[] args) throws Exception {
    generateAccessTokenStringNameListStringDelegatesListStringScopeDurationLifetime();
  }

  public static void
      generateAccessTokenStringNameListStringDelegatesListStringScopeDurationLifetime()
          throws Exception {
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      String name = ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]").toString();
      List<String> delegates = new ArrayList<>();
      List<String> scope = new ArrayList<>();
      Duration lifetime = Duration.newBuilder().build();
      GenerateAccessTokenResponse response =
          iamCredentialsClient.generateAccessToken(name, delegates, scope, lifetime);
    }
  }
}
