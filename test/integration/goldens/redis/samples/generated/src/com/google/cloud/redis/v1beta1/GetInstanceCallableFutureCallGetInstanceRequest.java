package com.google.cloud.redis.v1beta1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.GetInstanceRequest;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.InstanceName;

public class GetInstanceCallableFutureCallGetInstanceRequest {

  public static void main(String[] args) throws Exception {
    getInstanceCallableFutureCallGetInstanceRequest();
  }

  public static void getInstanceCallableFutureCallGetInstanceRequest() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      GetInstanceRequest request =
          GetInstanceRequest.newBuilder()
              .setName(InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString())
              .build();
      ApiFuture<Instance> future = cloudRedisClient.getInstanceCallable().futureCall(request);
      // Do something.
      Instance response = future.get();
    }
  }
}
