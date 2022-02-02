package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.GetInstanceRequest;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.InstanceName;

public class GetInstanceGetInstanceRequestRequest {

  public static void main(String[] args) throws Exception {
    getInstanceGetInstanceRequestRequest();
  }

  public static void getInstanceGetInstanceRequestRequest() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      GetInstanceRequest request =
          GetInstanceRequest.newBuilder()
              .setName(InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString())
              .build();
      Instance response = cloudRedisClient.getInstance(request);
    }
  }
}
