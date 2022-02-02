package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.InstanceName;
import com.google.cloud.redis.v1beta1.UpgradeInstanceRequest;

public class UpgradeInstanceAsyncUpgradeInstanceRequestGet {

  public static void main(String[] args) throws Exception {
    upgradeInstanceAsyncUpgradeInstanceRequestGet();
  }

  public static void upgradeInstanceAsyncUpgradeInstanceRequestGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      UpgradeInstanceRequest request =
          UpgradeInstanceRequest.newBuilder()
              .setName(InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString())
              .setRedisVersion("redisVersion-1972584739")
              .build();
      Instance response = cloudRedisClient.upgradeInstanceAsync(request).get();
    }
  }
}
