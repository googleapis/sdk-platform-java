package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.InstanceName;
import com.google.protobuf.Empty;

public class DeleteInstanceAsyncInstanceNameGet {

  public static void main(String[] args) throws Exception {
    deleteInstanceAsyncInstanceNameGet();
  }

  public static void deleteInstanceAsyncInstanceNameGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      InstanceName name = InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]");
      cloudRedisClient.deleteInstanceAsync(name).get();
    }
  }
}
