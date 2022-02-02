package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.InstanceName;

public class UpgradeInstanceAsyncInstanceNameStringGet {

  public static void main(String[] args) throws Exception {
    upgradeInstanceAsyncInstanceNameStringGet();
  }

  public static void upgradeInstanceAsyncInstanceNameStringGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      InstanceName name = InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]");
      String redisVersion = "redisVersion-1972584739";
      Instance response = cloudRedisClient.upgradeInstanceAsync(name, redisVersion).get();
    }
  }
}
