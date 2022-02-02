package com.google.cloud.redis.v1beta1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.ExportInstanceRequest;
import com.google.cloud.redis.v1beta1.OutputConfig;
import com.google.longrunning.Operation;

public class ExportInstanceCallableFutureCallExportInstanceRequest {

  public static void main(String[] args) throws Exception {
    exportInstanceCallableFutureCallExportInstanceRequest();
  }

  public static void exportInstanceCallableFutureCallExportInstanceRequest() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      ExportInstanceRequest request =
          ExportInstanceRequest.newBuilder()
              .setName("name3373707")
              .setOutputConfig(OutputConfig.newBuilder().build())
              .build();
      ApiFuture<Operation> future = cloudRedisClient.exportInstanceCallable().futureCall(request);
      // Do something.
      Operation response = future.get();
    }
  }
}
