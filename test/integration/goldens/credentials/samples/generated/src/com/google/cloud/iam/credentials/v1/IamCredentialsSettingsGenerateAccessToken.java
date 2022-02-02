package com.google.cloud.iam.credentials.v1.samples;

import com.google.cloud.iam.credentials.v1.IamCredentialsSettings;
import java.time.Duration;

public class IamCredentialsSettingsGenerateAccessToken {

  public static void main(String[] args) throws Exception {
    iamCredentialsSettingsGenerateAccessToken();
  }

  public static void iamCredentialsSettingsGenerateAccessToken() throws Exception {
    IamCredentialsSettings.Builder iamCredentialsSettingsBuilder =
        IamCredentialsSettings.newBuilder();
    iamCredentialsSettingsBuilder
        .generateAccessTokenSettings()
        .setRetrySettings(
            iamCredentialsSettingsBuilder
                .generateAccessTokenSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    IamCredentialsSettings iamCredentialsSettings = iamCredentialsSettingsBuilder.build();
  }
}
