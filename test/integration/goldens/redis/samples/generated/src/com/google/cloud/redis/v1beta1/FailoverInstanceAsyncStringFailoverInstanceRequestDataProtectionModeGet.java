package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.FailoverInstanceRequest;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.InstanceName;

public class FailoverInstanceAsyncStringFailoverInstanceRequestDataProtectionModeGet {

  public static void main(String[] args) throws Exception {
    failoverInstanceAsyncStringFailoverInstanceRequestDataProtectionModeGet();
  }

  public static void failoverInstanceAsyncStringFailoverInstanceRequestDataProtectionModeGet()
      throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      String name = InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString();
      FailoverInstanceRequest.DataProtectionMode dataProtectionMode =
          FailoverInstanceRequest.DataProtectionMode.forNumber(0);
      Instance response = cloudRedisClient.failoverInstanceAsync(name, dataProtectionMode).get();
    }
  }
}
