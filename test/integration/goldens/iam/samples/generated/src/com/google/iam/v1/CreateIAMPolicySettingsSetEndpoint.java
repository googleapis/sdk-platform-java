package com.google.iam.v1.samples;

import com.google.iam.v1.IAMPolicyClient;
import com.google.iam.v1.IAMPolicySettings;
import com.google.iam.v1.myEndpoint;

public class CreateIAMPolicySettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createIAMPolicySettingsSetEndpoint();
  }

  public static void createIAMPolicySettingsSetEndpoint() throws Exception {
    IAMPolicySettings iAMPolicySettings =
        IAMPolicySettings.newBuilder().setEndpoint(myEndpoint).build();
    IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create(iAMPolicySettings);
  }
}
