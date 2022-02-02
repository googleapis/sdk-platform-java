package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.OutputConfig;

public class ExportInstanceAsyncStringOutputConfigGet {

  public static void main(String[] args) throws Exception {
    exportInstanceAsyncStringOutputConfigGet();
  }

  public static void exportInstanceAsyncStringOutputConfigGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      String name = "name3373707";
      OutputConfig outputConfig = OutputConfig.newBuilder().build();
      Instance response = cloudRedisClient.exportInstanceAsync(name, outputConfig).get();
    }
  }
}
