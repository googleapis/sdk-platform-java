package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.DeleteInstanceRequest;
import com.google.cloud.redis.v1beta1.InstanceName;
import com.google.protobuf.Empty;

public class DeleteInstanceAsyncDeleteInstanceRequestGet {

  public static void main(String[] args) throws Exception {
    deleteInstanceAsyncDeleteInstanceRequestGet();
  }

  public static void deleteInstanceAsyncDeleteInstanceRequestGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      DeleteInstanceRequest request =
          DeleteInstanceRequest.newBuilder()
              .setName(InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString())
              .build();
      cloudRedisClient.deleteInstanceAsync(request).get();
    }
  }
}
