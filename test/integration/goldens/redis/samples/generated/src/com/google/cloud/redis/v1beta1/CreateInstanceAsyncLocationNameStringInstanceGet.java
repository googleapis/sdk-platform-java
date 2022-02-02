package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.LocationName;

public class CreateInstanceAsyncLocationNameStringInstanceGet {

  public static void main(String[] args) throws Exception {
    createInstanceAsyncLocationNameStringInstanceGet();
  }

  public static void createInstanceAsyncLocationNameStringInstanceGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      LocationName parent = LocationName.of("[PROJECT]", "[LOCATION]");
      String instanceId = "instanceId902024336";
      Instance instance = Instance.newBuilder().build();
      Instance response = cloudRedisClient.createInstanceAsync(parent, instanceId, instance).get();
    }
  }
}
