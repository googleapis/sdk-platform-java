package com.google.cloud.iam.credentials.v1.samples;

import com.google.cloud.iam.credentials.v1.GenerateAccessTokenResponse;
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import com.google.protobuf.Duration;
import java.util.ArrayList;
import java.util.List;

public
class GenerateAccessTokenServiceAccountNameNameListStringDelegatesListStringScopeDurationLifetime {

  public static void main(String[] args) throws Exception {
    generateAccessTokenServiceAccountNameNameListStringDelegatesListStringScopeDurationLifetime();
  }

  public static void
      generateAccessTokenServiceAccountNameNameListStringDelegatesListStringScopeDurationLifetime()
          throws Exception {
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      ServiceAccountName name = ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]");
      List<String> delegates = new ArrayList<>();
      List<String> scope = new ArrayList<>();
      Duration lifetime = Duration.newBuilder().build();
      GenerateAccessTokenResponse response =
          iamCredentialsClient.generateAccessToken(name, delegates, scope, lifetime);
    }
  }
}
