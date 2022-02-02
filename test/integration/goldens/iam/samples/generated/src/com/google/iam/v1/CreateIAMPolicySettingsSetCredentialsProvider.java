package com.google.iam.v1.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.iam.v1.IAMPolicyClient;
import com.google.iam.v1.IAMPolicySettings;
import com.google.iam.v1.myCredentials;

public class CreateIAMPolicySettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createIAMPolicySettingsSetCredentialsProvider();
  }

  public static void createIAMPolicySettingsSetCredentialsProvider() throws Exception {
    IAMPolicySettings iAMPolicySettings =
        IAMPolicySettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create(iAMPolicySettings);
  }
}
