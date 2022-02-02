package com.google.cloud.redis.v1beta1.samples;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.ExportInstanceRequest;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.OutputConfig;
import com.google.protobuf.Any;

public class ExportInstanceOperationCallablefutureCallExportInstanceRequest {

  public static void main(String[] args) throws Exception {
    exportInstanceOperationCallablefutureCallExportInstanceRequest();
  }

  public static void exportInstanceOperationCallablefutureCallExportInstanceRequest()
      throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      ExportInstanceRequest request =
          ExportInstanceRequest.newBuilder()
              .setName("name3373707")
              .setOutputConfig(OutputConfig.newBuilder().build())
              .build();
      OperationFuture<Instance, Any> future =
          cloudRedisClient.exportInstanceOperationCallable().futureCall(request);
      // Do something.
      Instance response = future.get();
    }
  }
}
