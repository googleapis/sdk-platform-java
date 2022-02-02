package com.google.cloud.redis.v1beta1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.DeleteInstanceRequest;
import com.google.cloud.redis.v1beta1.InstanceName;
import com.google.longrunning.Operation;

public class DeleteInstanceCallableFutureCallDeleteInstanceRequest {

  public static void main(String[] args) throws Exception {
    deleteInstanceCallableFutureCallDeleteInstanceRequest();
  }

  public static void deleteInstanceCallableFutureCallDeleteInstanceRequest() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      DeleteInstanceRequest request =
          DeleteInstanceRequest.newBuilder()
              .setName(InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString())
              .build();
      ApiFuture<Operation> future = cloudRedisClient.deleteInstanceCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
