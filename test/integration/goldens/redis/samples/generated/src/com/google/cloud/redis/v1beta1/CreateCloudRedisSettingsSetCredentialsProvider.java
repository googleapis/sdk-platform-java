package com.google.cloud.redis.v1beta1.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.CloudRedisSettings;
import com.google.cloud.redis.v1beta1.myCredentials;

public class CreateCloudRedisSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createCloudRedisSettingsSetCredentialsProvider();
  }

  public static void createCloudRedisSettingsSetCredentialsProvider() throws Exception {
    CloudRedisSettings cloudRedisSettings =
        CloudRedisSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    CloudRedisClient cloudRedisClient = CloudRedisClient.create(cloudRedisSettings);
  }
}
