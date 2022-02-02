package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.LocationName;

public class CreateInstanceAsyncStringStringInstanceGet {

  public static void main(String[] args) throws Exception {
    createInstanceAsyncStringStringInstanceGet();
  }

  public static void createInstanceAsyncStringStringInstanceGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      String parent = LocationName.of("[PROJECT]", "[LOCATION]").toString();
      String instanceId = "instanceId902024336";
      Instance instance = Instance.newBuilder().build();
      Instance response = cloudRedisClient.createInstanceAsync(parent, instanceId, instance).get();
    }
  }
}
