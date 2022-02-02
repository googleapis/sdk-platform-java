package com.google.cloud.logging.v2.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.logging.v2.MetricsClient;
import com.google.cloud.logging.v2.MetricsSettings;
import com.google.cloud.logging.v2.myCredentials;

public class CreateMetricsSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createMetricsSettingsSetCredentialsProvider();
  }

  public static void createMetricsSettingsSetCredentialsProvider() throws Exception {
    MetricsSettings metricsSettings =
        MetricsSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    MetricsClient metricsClient = MetricsClient.create(metricsSettings);
  }
}
