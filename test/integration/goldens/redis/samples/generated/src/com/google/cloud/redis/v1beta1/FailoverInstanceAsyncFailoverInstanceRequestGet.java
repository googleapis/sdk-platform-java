package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.FailoverInstanceRequest;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.InstanceName;

public class FailoverInstanceAsyncFailoverInstanceRequestGet {

  public static void main(String[] args) throws Exception {
    failoverInstanceAsyncFailoverInstanceRequestGet();
  }

  public static void failoverInstanceAsyncFailoverInstanceRequestGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      FailoverInstanceRequest request =
          FailoverInstanceRequest.newBuilder()
              .setName(InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString())
              .build();
      Instance response = cloudRedisClient.failoverInstanceAsync(request).get();
    }
  }
}
