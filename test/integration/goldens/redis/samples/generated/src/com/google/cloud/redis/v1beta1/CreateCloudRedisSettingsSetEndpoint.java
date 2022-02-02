package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.CloudRedisSettings;
import com.google.cloud.redis.v1beta1.myEndpoint;

public class CreateCloudRedisSettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createCloudRedisSettingsSetEndpoint();
  }

  public static void createCloudRedisSettingsSetEndpoint() throws Exception {
    CloudRedisSettings cloudRedisSettings =
        CloudRedisSettings.newBuilder().setEndpoint(myEndpoint).build();
    CloudRedisClient cloudRedisClient = CloudRedisClient.create(cloudRedisSettings);
  }
}
