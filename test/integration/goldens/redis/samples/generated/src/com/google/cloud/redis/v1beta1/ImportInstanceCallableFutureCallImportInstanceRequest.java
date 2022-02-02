package com.google.cloud.redis.v1beta1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.ImportInstanceRequest;
import com.google.cloud.redis.v1beta1.InputConfig;
import com.google.longrunning.Operation;

public class ImportInstanceCallableFutureCallImportInstanceRequest {

  public static void main(String[] args) throws Exception {
    importInstanceCallableFutureCallImportInstanceRequest();
  }

  public static void importInstanceCallableFutureCallImportInstanceRequest() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      ImportInstanceRequest request =
          ImportInstanceRequest.newBuilder()
              .setName("name3373707")
              .setInputConfig(InputConfig.newBuilder().build())
              .build();
      ApiFuture<Operation> future = cloudRedisClient.importInstanceCallable().futureCall(request);
      // Do something.
      Operation response = future.get();
    }
  }
}
