package com.google.cloud.iam.credentials.v1.samples;

import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.IamCredentialsSettings;
import com.google.cloud.iam.credentials.v1.myEndpoint;

public class CreateIamCredentialsSettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createIamCredentialsSettingsSetEndpoint();
  }

  public static void createIamCredentialsSettingsSetEndpoint() throws Exception {
    IamCredentialsSettings iamCredentialsSettings =
        IamCredentialsSettings.newBuilder().setEndpoint(myEndpoint).build();
    IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create(iamCredentialsSettings);
  }
}
