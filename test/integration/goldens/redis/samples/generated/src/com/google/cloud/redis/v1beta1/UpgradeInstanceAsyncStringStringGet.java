package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.InstanceName;

public class UpgradeInstanceAsyncStringStringGet {

  public static void main(String[] args) throws Exception {
    upgradeInstanceAsyncStringStringGet();
  }

  public static void upgradeInstanceAsyncStringStringGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      String name = InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString();
      String redisVersion = "redisVersion-1972584739";
      Instance response = cloudRedisClient.upgradeInstanceAsync(name, redisVersion).get();
    }
  }
}
