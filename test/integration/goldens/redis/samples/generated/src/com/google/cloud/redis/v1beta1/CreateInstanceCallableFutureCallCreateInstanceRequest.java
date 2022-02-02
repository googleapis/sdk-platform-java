package com.google.cloud.redis.v1beta1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.CreateInstanceRequest;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.LocationName;
import com.google.longrunning.Operation;

public class CreateInstanceCallableFutureCallCreateInstanceRequest {

  public static void main(String[] args) throws Exception {
    createInstanceCallableFutureCallCreateInstanceRequest();
  }

  public static void createInstanceCallableFutureCallCreateInstanceRequest() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      CreateInstanceRequest request =
          CreateInstanceRequest.newBuilder()
              .setParent(LocationName.of("[PROJECT]", "[LOCATION]").toString())
              .setInstanceId("instanceId902024336")
              .setInstance(Instance.newBuilder().build())
              .build();
      ApiFuture<Operation> future = cloudRedisClient.createInstanceCallable().futureCall(request);
      // Do something.
      Operation response = future.get();
    }
  }
}
