package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.UpdateInstanceRequest;
import com.google.protobuf.FieldMask;

public class UpdateInstanceAsyncUpdateInstanceRequestGet {

  public static void main(String[] args) throws Exception {
    updateInstanceAsyncUpdateInstanceRequestGet();
  }

  public static void updateInstanceAsyncUpdateInstanceRequestGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      UpdateInstanceRequest request =
          UpdateInstanceRequest.newBuilder()
              .setUpdateMask(FieldMask.newBuilder().build())
              .setInstance(Instance.newBuilder().build())
              .build();
      Instance response = cloudRedisClient.updateInstanceAsync(request).get();
    }
  }
}
