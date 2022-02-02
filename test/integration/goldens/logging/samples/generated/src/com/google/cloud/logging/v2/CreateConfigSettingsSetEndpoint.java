package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.cloud.logging.v2.ConfigSettings;
import com.google.cloud.logging.v2.myEndpoint;

public class CreateConfigSettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createConfigSettingsSetEndpoint();
  }

  public static void createConfigSettingsSetEndpoint() throws Exception {
    ConfigSettings configSettings = ConfigSettings.newBuilder().setEndpoint(myEndpoint).build();
    ConfigClient configClient = ConfigClient.create(configSettings);
  }
}
