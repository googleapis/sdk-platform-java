package com.google.cloud.redis.v1beta1.samples;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.FailoverInstanceRequest;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.InstanceName;
import com.google.protobuf.Any;

public class FailoverInstanceOperationCallablefutureCallFailoverInstanceRequest {

  public static void main(String[] args) throws Exception {
    failoverInstanceOperationCallablefutureCallFailoverInstanceRequest();
  }

  public static void failoverInstanceOperationCallablefutureCallFailoverInstanceRequest()
      throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      FailoverInstanceRequest request =
          FailoverInstanceRequest.newBuilder()
              .setName(InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString())
              .build();
      OperationFuture<Instance, Any> future =
          cloudRedisClient.failoverInstanceOperationCallable().futureCall(request);
      // Do something.
      Instance response = future.get();
    }
  }
}
