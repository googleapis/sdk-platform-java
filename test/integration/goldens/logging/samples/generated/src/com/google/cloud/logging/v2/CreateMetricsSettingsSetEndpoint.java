package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.MetricsClient;
import com.google.cloud.logging.v2.MetricsSettings;
import com.google.cloud.logging.v2.myEndpoint;

public class CreateMetricsSettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createMetricsSettingsSetEndpoint();
  }

  public static void createMetricsSettingsSetEndpoint() throws Exception {
    MetricsSettings metricsSettings = MetricsSettings.newBuilder().setEndpoint(myEndpoint).build();
    MetricsClient metricsClient = MetricsClient.create(metricsSettings);
  }
}
