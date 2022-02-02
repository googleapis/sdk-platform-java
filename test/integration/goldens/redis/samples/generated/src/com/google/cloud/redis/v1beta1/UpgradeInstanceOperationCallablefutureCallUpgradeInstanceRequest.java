package com.google.cloud.redis.v1beta1.samples;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.InstanceName;
import com.google.cloud.redis.v1beta1.UpgradeInstanceRequest;
import com.google.protobuf.Any;

public class UpgradeInstanceOperationCallablefutureCallUpgradeInstanceRequest {

  public static void main(String[] args) throws Exception {
    upgradeInstanceOperationCallablefutureCallUpgradeInstanceRequest();
  }

  public static void upgradeInstanceOperationCallablefutureCallUpgradeInstanceRequest()
      throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      UpgradeInstanceRequest request =
          UpgradeInstanceRequest.newBuilder()
              .setName(InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString())
              .setRedisVersion("redisVersion-1972584739")
              .build();
      OperationFuture<Instance, Any> future =
          cloudRedisClient.upgradeInstanceOperationCallable().futureCall(request);
      // Do something.
      Instance response = future.get();
    }
  }
}
