package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.InputConfig;
import com.google.cloud.redis.v1beta1.Instance;

public class ImportInstanceAsyncStringInputConfigGet {

  public static void main(String[] args) throws Exception {
    importInstanceAsyncStringInputConfigGet();
  }

  public static void importInstanceAsyncStringInputConfigGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      String name = "name3373707";
      InputConfig inputConfig = InputConfig.newBuilder().build();
      Instance response = cloudRedisClient.importInstanceAsync(name, inputConfig).get();
    }
  }
}
