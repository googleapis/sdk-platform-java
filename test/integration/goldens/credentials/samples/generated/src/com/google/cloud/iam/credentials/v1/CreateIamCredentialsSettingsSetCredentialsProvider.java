package com.google.cloud.iam.credentials.v1.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.IamCredentialsSettings;
import com.google.cloud.iam.credentials.v1.myCredentials;

public class CreateIamCredentialsSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createIamCredentialsSettingsSetCredentialsProvider();
  }

  public static void createIamCredentialsSettingsSetCredentialsProvider() throws Exception {
    IamCredentialsSettings iamCredentialsSettings =
        IamCredentialsSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create(iamCredentialsSettings);
  }
}
