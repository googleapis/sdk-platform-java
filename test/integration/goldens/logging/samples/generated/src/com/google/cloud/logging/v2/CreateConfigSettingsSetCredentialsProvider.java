package com.google.cloud.logging.v2.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.cloud.logging.v2.ConfigSettings;
import com.google.cloud.logging.v2.myCredentials;

public class CreateConfigSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createConfigSettingsSetCredentialsProvider();
  }

  public static void createConfigSettingsSetCredentialsProvider() throws Exception {
    ConfigSettings configSettings =
        ConfigSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    ConfigClient configClient = ConfigClient.create(configSettings);
  }
}
