package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.ExportInstanceRequest;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.OutputConfig;

public class ExportInstanceAsyncExportInstanceRequestGet {

  public static void main(String[] args) throws Exception {
    exportInstanceAsyncExportInstanceRequestGet();
  }

  public static void exportInstanceAsyncExportInstanceRequestGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      ExportInstanceRequest request =
          ExportInstanceRequest.newBuilder()
              .setName("name3373707")
              .setOutputConfig(OutputConfig.newBuilder().build())
              .build();
      Instance response = cloudRedisClient.exportInstanceAsync(request).get();
    }
  }
}
