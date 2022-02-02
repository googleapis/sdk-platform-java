package com.google.cloud.redis.v1beta1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.UpdateInstanceRequest;
import com.google.longrunning.Operation;
import com.google.protobuf.FieldMask;

public class UpdateInstanceCallableFutureCallUpdateInstanceRequest {

  public static void main(String[] args) throws Exception {
    updateInstanceCallableFutureCallUpdateInstanceRequest();
  }

  public static void updateInstanceCallableFutureCallUpdateInstanceRequest() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      UpdateInstanceRequest request =
          UpdateInstanceRequest.newBuilder()
              .setUpdateMask(FieldMask.newBuilder().build())
              .setInstance(Instance.newBuilder().build())
              .build();
      ApiFuture<Operation> future = cloudRedisClient.updateInstanceCallable().futureCall(request);
      // Do something.
      Operation response = future.get();
    }
  }
}
