package com.google.cloud.iam.credentials.v1.stub.samples;

import com.google.cloud.iam.credentials.v1.stub.IamCredentialsStubSettings;
import java.time.Duration;

public class IamCredentialsStubSettingsGenerateAccessToken {

  public static void main(String[] args) throws Exception {
    iamCredentialsStubSettingsGenerateAccessToken();
  }

  public static void iamCredentialsStubSettingsGenerateAccessToken() throws Exception {
    IamCredentialsStubSettings.Builder iamCredentialsSettingsBuilder =
        IamCredentialsStubSettings.newBuilder();
    iamCredentialsSettingsBuilder
        .generateAccessTokenSettings()
        .setRetrySettings(
            iamCredentialsSettingsBuilder
                .generateAccessTokenSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    IamCredentialsStubSettings iamCredentialsSettings = iamCredentialsSettingsBuilder.build();
  }
}
