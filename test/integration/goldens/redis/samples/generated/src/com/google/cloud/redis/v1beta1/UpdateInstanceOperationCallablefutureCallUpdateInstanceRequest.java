package com.google.cloud.redis.v1beta1.samples;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.UpdateInstanceRequest;
import com.google.protobuf.Any;
import com.google.protobuf.FieldMask;

public class UpdateInstanceOperationCallablefutureCallUpdateInstanceRequest {

  public static void main(String[] args) throws Exception {
    updateInstanceOperationCallablefutureCallUpdateInstanceRequest();
  }

  public static void updateInstanceOperationCallablefutureCallUpdateInstanceRequest()
      throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      UpdateInstanceRequest request =
          UpdateInstanceRequest.newBuilder()
              .setUpdateMask(FieldMask.newBuilder().build())
              .setInstance(Instance.newBuilder().build())
              .build();
      OperationFuture<Instance, Any> future =
          cloudRedisClient.updateInstanceOperationCallable().futureCall(request);
      // Do something.
      Instance response = future.get();
    }
  }
}
