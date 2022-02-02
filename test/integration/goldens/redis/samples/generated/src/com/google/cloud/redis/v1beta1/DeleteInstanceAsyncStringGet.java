package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.InstanceName;
import com.google.protobuf.Empty;

public class DeleteInstanceAsyncStringGet {

  public static void main(String[] args) throws Exception {
    deleteInstanceAsyncStringGet();
  }

  public static void deleteInstanceAsyncStringGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      String name = InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString();
      cloudRedisClient.deleteInstanceAsync(name).get();
    }
  }
}
