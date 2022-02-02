package com.google.cloud.redis.v1beta1.samples;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.DeleteInstanceRequest;
import com.google.cloud.redis.v1beta1.InstanceName;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

public class DeleteInstanceOperationCallablefutureCallDeleteInstanceRequest {

  public static void main(String[] args) throws Exception {
    deleteInstanceOperationCallablefutureCallDeleteInstanceRequest();
  }

  public static void deleteInstanceOperationCallablefutureCallDeleteInstanceRequest()
      throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      DeleteInstanceRequest request =
          DeleteInstanceRequest.newBuilder()
              .setName(InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString())
              .build();
      OperationFuture<Empty, Any> future =
          cloudRedisClient.deleteInstanceOperationCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
