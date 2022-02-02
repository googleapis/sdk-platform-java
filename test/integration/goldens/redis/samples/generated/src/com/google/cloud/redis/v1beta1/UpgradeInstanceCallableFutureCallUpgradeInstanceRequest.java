package com.google.cloud.redis.v1beta1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.InstanceName;
import com.google.cloud.redis.v1beta1.UpgradeInstanceRequest;
import com.google.longrunning.Operation;

public class UpgradeInstanceCallableFutureCallUpgradeInstanceRequest {

  public static void main(String[] args) throws Exception {
    upgradeInstanceCallableFutureCallUpgradeInstanceRequest();
  }

  public static void upgradeInstanceCallableFutureCallUpgradeInstanceRequest() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      UpgradeInstanceRequest request =
          UpgradeInstanceRequest.newBuilder()
              .setName(InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString())
              .setRedisVersion("redisVersion-1972584739")
              .build();
      ApiFuture<Operation> future = cloudRedisClient.upgradeInstanceCallable().futureCall(request);
      // Do something.
      Operation response = future.get();
    }
  }
}
