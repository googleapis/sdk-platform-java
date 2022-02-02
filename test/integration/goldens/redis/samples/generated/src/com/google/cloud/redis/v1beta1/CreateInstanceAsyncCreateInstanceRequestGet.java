package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.CreateInstanceRequest;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.LocationName;

public class CreateInstanceAsyncCreateInstanceRequestGet {

  public static void main(String[] args) throws Exception {
    createInstanceAsyncCreateInstanceRequestGet();
  }

  public static void createInstanceAsyncCreateInstanceRequestGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      CreateInstanceRequest request =
          CreateInstanceRequest.newBuilder()
              .setParent(LocationName.of("[PROJECT]", "[LOCATION]").toString())
              .setInstanceId("instanceId902024336")
              .setInstance(Instance.newBuilder().build())
              .build();
      Instance response = cloudRedisClient.createInstanceAsync(request).get();
    }
  }
}
