package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.FailoverInstanceRequest;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.InstanceName;

public class FailoverInstanceAsyncInstanceNameFailoverInstanceRequestDataProtectionModeGet {

  public static void main(String[] args) throws Exception {
    failoverInstanceAsyncInstanceNameFailoverInstanceRequestDataProtectionModeGet();
  }

  public static void failoverInstanceAsyncInstanceNameFailoverInstanceRequestDataProtectionModeGet()
      throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      InstanceName name = InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]");
      FailoverInstanceRequest.DataProtectionMode dataProtectionMode =
          FailoverInstanceRequest.DataProtectionMode.forNumber(0);
      Instance response = cloudRedisClient.failoverInstanceAsync(name, dataProtectionMode).get();
    }
  }
}
