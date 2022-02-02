package com.google.cloud.redis.v1beta1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.FailoverInstanceRequest;
import com.google.cloud.redis.v1beta1.InstanceName;
import com.google.longrunning.Operation;

public class FailoverInstanceCallableFutureCallFailoverInstanceRequest {

  public static void main(String[] args) throws Exception {
    failoverInstanceCallableFutureCallFailoverInstanceRequest();
  }

  public static void failoverInstanceCallableFutureCallFailoverInstanceRequest() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      FailoverInstanceRequest request =
          FailoverInstanceRequest.newBuilder()
              .setName(InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString())
              .build();
      ApiFuture<Operation> future = cloudRedisClient.failoverInstanceCallable().futureCall(request);
      // Do something.
      Operation response = future.get();
    }
  }
}
