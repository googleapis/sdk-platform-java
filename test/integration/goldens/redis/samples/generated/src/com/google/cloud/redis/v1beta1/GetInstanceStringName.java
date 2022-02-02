package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.InstanceName;

public class GetInstanceStringName {

  public static void main(String[] args) throws Exception {
    getInstanceStringName();
  }

  public static void getInstanceStringName() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      String name = InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString();
      Instance response = cloudRedisClient.getInstance(name);
    }
  }
}
