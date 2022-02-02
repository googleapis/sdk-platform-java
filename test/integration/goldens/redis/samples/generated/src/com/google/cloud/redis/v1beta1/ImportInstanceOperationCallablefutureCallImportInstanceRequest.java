package com.google.cloud.redis.v1beta1.samples;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.ImportInstanceRequest;
import com.google.cloud.redis.v1beta1.InputConfig;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.protobuf.Any;

public class ImportInstanceOperationCallablefutureCallImportInstanceRequest {

  public static void main(String[] args) throws Exception {
    importInstanceOperationCallablefutureCallImportInstanceRequest();
  }

  public static void importInstanceOperationCallablefutureCallImportInstanceRequest()
      throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      ImportInstanceRequest request =
          ImportInstanceRequest.newBuilder()
              .setName("name3373707")
              .setInputConfig(InputConfig.newBuilder().build())
              .build();
      OperationFuture<Instance, Any> future =
          cloudRedisClient.importInstanceOperationCallable().futureCall(request);
      // Do something.
      Instance response = future.get();
    }
  }
}
