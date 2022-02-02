package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.ImportInstanceRequest;
import com.google.cloud.redis.v1beta1.InputConfig;
import com.google.cloud.redis.v1beta1.Instance;

public class ImportInstanceAsyncImportInstanceRequestGet {

  public static void main(String[] args) throws Exception {
    importInstanceAsyncImportInstanceRequestGet();
  }

  public static void importInstanceAsyncImportInstanceRequestGet() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      ImportInstanceRequest request =
          ImportInstanceRequest.newBuilder()
              .setName("name3373707")
              .setInputConfig(InputConfig.newBuilder().build())
              .build();
      Instance response = cloudRedisClient.importInstanceAsync(request).get();
    }
  }
}
