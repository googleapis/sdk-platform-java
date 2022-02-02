package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.protobuf.FieldMask;

public class UpdateInstanceAsyncFieldMaskInstanceGet {

  public static void main(String[] args) throws Exception {
    updateInstanceAsyncFieldMaskInstanceGet();
  }

  public static void updateInstanceAsyncFieldMaskInstanceGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      FieldMask updateMask = FieldMask.newBuilder().build();
      Instance instance = Instance.newBuilder().build();
      Instance response = cloudRedisClient.updateInstanceAsync(updateMask, instance).get();
    }
  }
}
