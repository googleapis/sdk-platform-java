package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.InstanceName;

public class GetInstanceInstanceNameName {

  public static void main(String[] args) throws Exception {
    getInstanceInstanceNameName();
  }

  public static void getInstanceInstanceNameName() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      InstanceName name = InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]");
      Instance response = cloudRedisClient.getInstance(name);
    }
  }
}
